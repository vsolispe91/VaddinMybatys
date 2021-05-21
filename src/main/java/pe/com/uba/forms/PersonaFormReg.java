package pe.com.uba.forms;


import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.Shortcuts;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.Binder.Binding;
import com.vaadin.flow.data.binder.BinderValidationStatus;
import com.vaadin.flow.data.binder.BindingValidationStatus;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.data.validator.StringLengthValidator;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.function.SerializablePredicate;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Location;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.QueryParameters;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

import pe.com.uba.iservice.IPersonaService;
import pe.com.uba.model.Persona;
import pe.com.uba.serviceimpl.BusinessServiceUtility;
import pe.com.uba.views.BasicLayout;

@Route(value = "personaformreg")
@PageTitle("REGISTRO PERSONA")
public class PersonaFormReg  extends BasicLayout{
	
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private IPersonaService ipersonaservice=BusinessServiceUtility.getService("PersonaService");
	
	Persona persona_queryParam;
	
	int id_persona_editar;
	
	TextField Nombre;
	TextField apellidos;
	
	Dialog dialog;
	
	TextField email;

	public PersonaFormReg() {
	
		
		String id_session=VaadinSession.getCurrent().getSession().getId();
	    
	    System.out.println("ID SESSION REGISTRO PERSONA:"+id_session);
		
		dialog = new Dialog();
		dialog.add(new Text("¿Esta seguro de Registrar este elemento?."));
		dialog.setCloseOnEsc(false);
		dialog.setCloseOnOutsideClick(false);
		Span message = new Span();	
		
		H1 title = new H1();
		
		title.add(new Text("Formulario Registro de Persona"));
		title.getStyle().set("color", "orange");
		
		title.getStyle().set("font-size", "20px");
		
		FormLayout layoutWithBinder = new FormLayout();
		Binder<Persona> binder = new Binder<>();

		// The object that will be edited
	    Persona contactBeingEdited = new Persona();

		// Create the fields
		Nombre = new TextField();
		Nombre.setValueChangeMode(ValueChangeMode.EAGER);
		apellidos = new TextField();
		apellidos.setValueChangeMode(ValueChangeMode.EAGER);
		
		email= new TextField();
		email.setValueChangeMode(ValueChangeMode.EAGER);
		Label infoLabel = new Label();
		Button save = new Button("Registrar");
		Button reset = new Button("Cancelar");
		Button volver = new Button(new Icon(VaadinIcon.ARROW_BACKWARD));

		layoutWithBinder.addFormItem(Nombre, "Nombre");
		layoutWithBinder.addFormItem(email, "E-mail");
		layoutWithBinder.addFormItem(apellidos, "Apellido");

		// Button bar
		HorizontalLayout actions = new HorizontalLayout();
		volver.setEnabled(false);
		actions.add(save, reset,volver);
		

		// Both Nombre and email cannot be empty
		SerializablePredicate<String> NombreEmailPredicate = value -> !Nombre
		        .getValue().trim().isEmpty()
		        || !apellidos.getValue().trim().isEmpty() || !email.getValue().trim().isEmpty();

		// E-mail and Nombre have specific validators
		Binding<Persona, String> emailBinding = binder.forField(email)
		        .withNullRepresentation("")
		        .withValidator(NombreEmailPredicate,"Especifica Email")
		        .withValidator(new EmailValidator("Correo Electronico Incorrecto"))
		        .bind(Persona::getEmail, Persona::setEmail);

		Binding<Persona, String> NombreBinding = binder.forField(Nombre)
		        .withValidator(NombreEmailPredicate,
		                "Especifica Nombre")
		        .bind(Persona::getNombres, Persona::setNombres);
		
		Binding<Persona, String> ApellidosBinding = binder.forField(apellidos)
		        .withValidator(NombreEmailPredicate,
		                "Especifica Apellidos")
		        .bind(Persona::getApellidos, Persona::setApellidos);


		// Trigger cross-field validation when the other field is changed
		Nombre.addValueChangeListener(event -> NombreBinding.validate());
		apellidos.addValueChangeListener(event -> ApellidosBinding.validate());
		email.addValueChangeListener(event -> emailBinding.validate());
		
		// Click listeners for the buttons
		save.addClickListener(event -> {
			if (binder.writeBeanIfValid(contactBeingEdited)) {						
		        try {
		        	dialog.open();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					  infoLabel.getStyle().set("color","Red");
				      infoLabel.setText("Se encontraron errores, consultar administrador");
					
				}
		       //UI.getCurrent().navigate("");
		    } else {
		        BinderValidationStatus<Persona> validate = binder.validate();
		        String errorText = validate.getFieldValidationStatuses()
		                .stream().filter(BindingValidationStatus::isError)
		                .map(BindingValidationStatus::getMessage)
		                .map(Optional::get).distinct()
		                .collect(Collectors.joining(", "));
		        infoLabel.getStyle().set("color","Red");
		        //infoLabel.setText("Se encontraron errores: " + errorText);
		    }
		});
		reset.addClickListener(event -> {
		    // clear fields by setting null
		    binder.readBean(null);
		    infoLabel.setText("");
		});
		
		
		
	     Button confirmButton = new Button("Confirm", event -> {
	        	
			    int resultado=0;
				try {					
					int registerrows=0;
					if((registerrows=ipersonaservice.registrapersona(contactBeingEdited))>=1) {
						
						infoLabel.getStyle().set("color","Green");
				        infoLabel.setText("Se Registro Persona: " + contactBeingEdited.getNombres()+contactBeingEdited.getApellidos()+contactBeingEdited.getEmail());
					}else {
						  infoLabel.getStyle().set("color","Red");
					      infoLabel.setText("Se Registro mas de un registro: ");
					}					
				}catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					infoLabel.getStyle().set("color", "red");				
					infoLabel.setText("Ocurrio un Error Exception"+resultado);
				}			
			    dialog.close();
			    save.setEnabled(false);
			    reset.setEnabled(false);
			    volver.setEnabled(true);
			    //		    
			});
			Button cancelButton = new Button("Cancel", event -> {
				infoLabel.getStyle().set("color", "Orange");
				infoLabel.setText("Registro Cancelado...");
			    dialog.close();			    
			});
			// Cancel action on ESC press
			Shortcuts.addShortcutListener(dialog, () -> {
				infoLabel.getStyle().set("color", "Orange");
				infoLabel.setText("Registro Cancelado...");			    
			    dialog.close();			    
			}, Key.ESCAPE);
	        
			volver.addClickListener(click->{
				UI.getCurrent().navigate("contactos");	
			});
			
	        dialog.add(new Div( confirmButton, cancelButton));
		
		
		content.add(title,layoutWithBinder, actions, infoLabel);
		
		
		
	}
	

}
