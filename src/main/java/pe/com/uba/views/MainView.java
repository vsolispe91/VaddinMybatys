package pe.com.uba.views;


import java.util.List;

import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.Shortcuts;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

import pe.com.uba.iservice.IPersonaService;
import pe.com.uba.model.Persona;
import pe.com.uba.serviceimpl.BusinessServiceUtility;
import pe.com.uba.utils.PersonFilter;



@Route("contactos")
@PageTitle("CONTACTOS") 
public class MainView extends BasicLayout  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	Grid<Persona> grid;
	
	List<Persona> personList;
	
	ListDataProvider<Persona> dataProvider;
	
	ListDataProvider<Persona> dataProvider2;
	
	Persona selected_item;
	Persona selected_item_update;
	
	TextField filtroApellido=new TextField();
	
	
	PersonFilter filterObject = new PersonFilter();
	
	private IPersonaService ipersonaservice=BusinessServiceUtility.getService("PersonaService");
	
	HorizontalLayout actions = new HorizontalLayout();
	HorizontalLayout filters = new HorizontalLayout();
	
	Dialog dialog;
	
	Span message;
	
	H1 title;
	
	 Button confirmButton;
	 
	 Button cancelButton;
	 
	 Button save;
	 
	 H1 savedesc;
	
	public MainView() {
	    
		String id_session=VaadinSession.getCurrent().getSession().getId();	    
	    System.out.println("ID SESSION MAIN:"+id_session);
	    
	    
	    //FUNCIONALIDAD
	    
	    createtitulo();		
		createdialog();		
		createGridPersonas();        
		configdeletePersonas();			
		configNewElement();
		configureFilter(grid);
		
		//VISTA Y PRESENTACION
		
		//volver.setEnabled(false);
		actions.add(savedesc,save);
		actions.setAlignItems(Alignment.STRETCH);
		//actions.setAlignSelf(save,(Alignment.START));        
        dialog.add(new Div( confirmButton, cancelButton));        
        content.add(title,filters,actions,grid,message);	        
        content.setAlignSelf(FlexComponent.Alignment.END,actions);
        //content.setAlignSelf(save, Alignment.END);
			   
	}
	
	private void configNewElement() {
		save = new Button(new Icon(VaadinIcon.PLUS));
		savedesc = new H1("Nuevo Elemento");
		savedesc.getStyle().set("color", "orange");
	    savedesc.getStyle().set("font-size", "15px");
			    
		save.addClickListener(click->{
			UI.getCurrent().navigate("personaformreg");
		});
		
	}
	
	private void configdeletePersonas() {
		confirmButton = new Button("Confirm", event -> {
        	
		    int resultado=0;
			try {
				if((resultado=ipersonaservice.borrarpersona(selected_item))>=1) {
					dataProvider.getItems().remove(selected_item);
					dataProvider.refreshAll();
					//message.setText("Confirmed!");
					message.getStyle().set("color", "green");		    
				    message.setText("Se Elimino el elemento");
				}else {
					message.getStyle().set("color", "organge");		    
					message.setText("Se borro mas de un registro"+resultado);	
				}
			}catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				message.getStyle().set("color", "red");				
				message.setText("Ocurrio un Error Exception"+resultado);
			}			
		    dialog.close();
		});
		cancelButton = new Button("Cancel", event -> {
			message.getStyle().set("color", "Orange");
		    message.setText("Eliminacion Cancelada...");
		    dataProvider.refreshAll();
		    dialog.close();
		});
		// Cancel action on ESC press
		Shortcuts.addShortcutListener(dialog, () -> {
			message.getStyle().set("color", "Orange");
			message.setText("Eliminacion Cancelada...");
		    dataProvider.refreshAll();
		    dialog.close();
		}, Key.ESCAPE);
		
		
		
	}
	
	private void createGridPersonas() {
		

		personList=ipersonaservice.buscarpersonas(); 
		
		grid = new Grid<>();
		grid.setItems(personList);
		
		grid.addColumn(Persona::getId).setHeader("ID").setFlexGrow(0);

		grid.addColumn(Persona::getNombres).setHeader("Primer Nombre").setFooter("Total: " + personList.size() + " Personas");

		grid.addColumn(Persona::getApellidos).setHeader("Apellido");
		
		grid.addColumn(Persona::getEmail).setHeader("Email");
				
		grid.addComponentColumn(item -> createEditButton(grid, item)).setHeader("Actualizar").setFlexGrow(0);
		
		grid.addComponentColumn(item -> createRemoveButton(grid, item)).setHeader("Eliminar").setFlexGrow(0);
		

        grid.setSelectionMode(Grid.SelectionMode.NONE);		           
   	   
        grid.getStyle().set("border", "1px solid #ADD8E6");
        grid.getStyle().set("border-radius", "5px");
        //grid.getStyle().set("background-color", "#ADD8E6");
        grid.addThemeVariants(GridVariant.LUMO_COLUMN_BORDERS,GridVariant.MATERIAL_COLUMN_DIVIDERS,GridVariant.LUMO_COMPACT, GridVariant.LUMO_ROW_STRIPES);	
        
        
	}
	
	private void createtitulo() {
	    title= new H1();
		
		title.add(new Text("Reporte de Contactos"));
		title.getStyle().set("color", "orange");
		
		title.getStyle().set("font-size", "20px");
	}
	
	private void createdialog() {
		dialog = new Dialog();
		dialog.add(new Text("¿Esta seguro de Eliminar el elemento seleccionado?."));
		dialog.setCloseOnEsc(false);
		dialog.setCloseOnOutsideClick(false);
		message = new Span();	
	}
	
	@SuppressWarnings("unchecked")
	private void configureFilter(Grid<Persona> grid) {		
		dataProvider = (ListDataProvider<Persona>) grid.getDataProvider();		
		dataProvider.setFilter(person -> filterObject.test(person));
		filtroApellido.setPlaceholder("Buscar Apellido...");
		filtroApellido.setClearButtonVisible(true);
		filtroApellido.setValueChangeMode(ValueChangeMode.LAZY);	
		
		filters.add(filtroApellido);
		
		filters.setAlignItems(Alignment.START);
		
		filtroApellido.addValueChangeListener(e->{	
			System.out.println("Filtrando "+e.getValue());
		    filterObject.setApellidos(e.getValue());
		    dataProvider.refreshAll();		
		});
	}

	private Button createRemoveButton(Grid<Persona> grid, Persona item) {
	    @SuppressWarnings("unchecked")
	    Button button = new Button(new Icon(VaadinIcon.TRASH), clickEvent -> {
	    	dataProvider = (ListDataProvider<Persona>) grid.getDataProvider();
	        //ipersonaservice.borrarpersona(item);
	    	selected_item=item;
	        dialog.open();
	    });
	    return button;
	}  
	
	private Button createEditButton(Grid<Persona> grid, Persona item) {
	    @SuppressWarnings("unchecked")
	    Button button = new Button(new Icon(VaadinIcon.EDIT), clickEvent -> {
	    	//dataProvider = (ListDataProvider<Persona>) grid.getDataProvider();
	        //ipersonaservice.borrarpersona(item);
	    	selected_item_update=item;
	    	
//	    	Map<String, List<String>> myParams = new HashMap<>();
//	   	    myParams.put("ObjPersona", Arrays.asList(String.valueOf(item.getId()).trim(), item.getNombres().trim(), item.getApellidos().trim()));
	    	
//	    	//UI.getCurrent().navigate("personaform");
//	   	   QueryParameters queryParameters = new QueryParameters(myParams);
//	   	   
	   	   ComponentUtil.setData(UI.getCurrent(), "pe.com.uba.model.Persona", selected_item_update);
		      
		   UI.getCurrent().navigate("personaform");
	        //dialog.open();
	    });
	    return button;
	} 

}



