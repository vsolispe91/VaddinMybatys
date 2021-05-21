package pe.com.uba.forms;


import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.vaadin.flow.component.ComponentUtil;
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
import pe.com.uba.views.MainView;

@Route(value = "personaform")
@PageTitle("FORMULARIO")
public class PersonaForm  extends BasicLayout{
	
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	Map<String, List<String>>  parametersMap;
	
	private IPersonaService ipersonaservice=BusinessServiceUtility.getService("PersonaService");
	
	
	Persona personarecibida;
	
	Persona persona_queryParam;
	
	int id_persona_editar;
	
	TextField Nombre;
	TextField apellidos;
	TextField email;
	
	Dialog dialog;

	Label infoLabel;
	
	
	public PersonaForm() {
	
		
		//personarecibida=new Persona();
		
		personarecibida=(Persona)ComponentUtil.getData(UI.getCurrent(), "pe.com.uba.model.Persona");
		
		String id_session=VaadinSession.getCurrent().getSession().getId();
	    
	    System.out.println("ID SESSION PERSONA FORM:"+id_session);
		
		dialog = new Dialog();
		dialog.add(new Text("¿Esta seguro de Editar este elemento?."));
		dialog.setCloseOnEsc(false);
		dialog.setCloseOnOutsideClick(false);
		Span message = new Span();	
		
		H1 title = new H1();
		
		title.add(new Text("Formulario Actualizacion de Persona"));
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
		
		
		
		
		email = new TextField();
		email.setValueChangeMode(ValueChangeMode.EAGER);
		
		infoLabel= new Label();
		Button save = new Button("Actualizar");
		Button reset = new Button("Cancelar");
		Button volver = new Button(new Icon(VaadinIcon.ARROW_BACKWARD));

		if(personarecibida!=null) {
			//System.out.println("Valores COmponet Util:"+":"+personarecibida.getApellidos()+":"+personarecibida.getNombres()+":"+personarecibida.getId());	
			Nombre.setValue((personarecibida.getNombres()!=null)?personarecibida.getNombres().trim():"");			
			apellidos.setValue((personarecibida.getApellidos()!=null)?personarecibida.getApellidos().trim():"");
			email.setValue((personarecibida.getEmail()!=null)?personarecibida.getEmail().trim():"");
			volver.setEnabled(false);			
		}else {
			save.setEnabled(false);
			reset.setEnabled(false);
			volver.setEnabled(true);	
		}
		
		layoutWithBinder.addFormItem(Nombre, "Nombre");
		layoutWithBinder.addFormItem(email, "E-mail");
		layoutWithBinder.addFormItem(apellidos, "Apellido");

		// Button bar
		HorizontalLayout actions = new HorizontalLayout();		
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

		// apellidos is a required field
		//Nombre.setRequiredIndicatorVisible(true);
		//apellidos.setRequiredIndicatorVisible(true);
		//email.setRequiredIndicatorVisible(true);
		//binder.forField(apellidos).withValidator(new StringLengthValidator("Especifica Apellido", 1, null)).bind(Persona::getApellidos, Persona::setApellidos);


		
		// Click listeners for the buttons
		save.addClickListener(event -> {
			if (binder.writeBeanIfValid(contactBeingEdited)) {						
		        try {
		    		if(personarecibida!=null) {
			        	contactBeingEdited.setId(personarecibida.getId()); //la llave o ID del objeto
			        	dialog.open();
		    		}
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
					int updaterows=0;
					if((updaterows=ipersonaservice.actualizarpersona(contactBeingEdited))>=1) {
						
						infoLabel.getStyle().set("color","Green");
				        infoLabel.setText("Se actualizo Persona: " + contactBeingEdited.getNombres()+contactBeingEdited.getApellidos()+contactBeingEdited.getEmail());
					}else {
						  infoLabel.getStyle().set("color","Red");
					      infoLabel.setText("Se actualizo mas de un registro: ");
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
				infoLabel.setText("Actualizacion Cancelada...");
			    dialog.close();			    
			});
			// Cancel action on ESC press
			Shortcuts.addShortcutListener(dialog, () -> {
				infoLabel.getStyle().set("color", "Orange");
				infoLabel.setText("Actualizacion Cancelada...");			    
			    dialog.close();			    
			}, Key.ESCAPE);
	        
			volver.addClickListener(click->{
				UI.getCurrent().navigate("contactos");	
			});
			
	        dialog.add(new Div( confirmButton, cancelButton));
		
		
		content.add(title,layoutWithBinder, actions, infoLabel);
		
		
		
	}

	/*@Override
	public void setParameter(BeforeEvent event, String parameter) {
		// TODO Auto-generated method stub
		persona_queryParam= new Persona();
		Location location = event.getLocation();
		
	    QueryParameters queryParameters = location.getQueryParameters();
	    parametersMap = queryParameters.getParameters();    
	    System.out.println("Valores:"+parametersMap.toString()+":"+parametersMap.get("ObjPersona").get(0)+":"+parametersMap.get("ObjPersona").get(1)+":"+parametersMap.get("ObjPersona").get(2)+":"+parameter);	    
	    persona_queryParam.setId(Integer.parseInt(parametersMap.get("ObjPersona").get(0)));
	    persona_queryParam.setNombres(parametersMap.get("ObjPersona").get(1).trim());
	    persona_queryParam.setApellidos(parametersMap.get("ObjPersona").get(2).trim());    

		Nombre.setValue(persona_queryParam.getNombres());
		apellidos.setValue(persona_queryParam.getApellidos());
	    
	}*/
	

}
