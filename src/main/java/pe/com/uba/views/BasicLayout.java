package pe.com.uba.views;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.dom.ThemeList;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.theme.lumo.Lumo;


//@Theme(value = Lumo.class, variant = Lumo.DARK)
@Route(value = "")
@PageTitle("BIENVENIDO")
public class BasicLayout extends VerticalLayout {

	
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	   HorizontalLayout header=new HorizontalLayout();	   
	   HorizontalLayout headersub= new HorizontalLayout();;
	   VerticalLayout navBar= new VerticalLayout();;
	   FormLayout subnavBar= new FormLayout();;
	   protected VerticalLayout content= new VerticalLayout();;
	   HorizontalLayout center= new HorizontalLayout();;
	   HorizontalLayout footer= new HorizontalLayout();;
	   
	   
	   Button button = new Button();	    
	   Button buttonlogout = new Button();
	   Button buttonlogin = new Button();
	   
	   Icon logo = new Icon(VaadinIcon.MENU);	    
	   Icon logo2 = new Icon(VaadinIcon.SIGN_IN);
	   Icon logo3 = new Icon(VaadinIcon.SIGN_OUT_ALT);
	   Icon logo4 = new Icon(VaadinIcon.BELL_O);
	   Icon logo5 = new Icon(VaadinIcon.MOON);
	   Button buttonactivity = new Button();
       Button buttondarkmode;
	   H1 title = new H1();
	   H1 title2 = new H1();
	   H1 title3 = new H1();
	   
	   
	   Button button1;
	   Button button2;
	   Button button3;	   
	   Button button4;
	   
	   ThemeList theme;
	   
	public BasicLayout() {
		   // Instantiate layouts
		
		String id_session=VaadinSession.getCurrent().getSession().getId();
		
	    System.out.println("ID SESSION BASIC LAYOUT FORM:"+id_session);
	    
	    theme= UI.getCurrent().getElement().getThemeList();
	    theme.add(Lumo.DARK);
	    
		logo.setSize("40px");
		logo2.setSize("40px");
		logo3.setSize("40px");
		logo4.setSize("40px");
		logo5.setSize("40px");
		    
		    
		button.setIcon(logo);
		button.setWidth("40px");  
		//button.setHeight("50px");
		buttonlogin.setIcon(logo2);
		buttonlogin.setWidth("40px");
		buttonlogout.setIcon(logo3);
		buttonlogout.setWidth("40px");
		buttonlogout.addClickListener(click->{
		   System.out.println("CERRAMOS SESION:"+id_session);
		   VaadinSession.getCurrent().getSession().invalidate();
		   //UI.getCurrent().navigate("login");
	    });     
		      
		buttonactivity.setIcon(logo4);
		buttonactivity.setWidth("40px");
		    
		buttondarkmode = new Button("",click-> {				
		if(theme.contains(Lumo.DARK)) {
		  	 theme.remove(Lumo.DARK);
		}else {
		    theme.add(Lumo.DARK);		    		 
		}}); 
		
		buttondarkmode.setIcon(logo5);
		buttondarkmode.setWidth("40px");
		    
		title.add(new Text("ENTRY MANAGMENT.V1"));				
		title.getStyle().set("font-size", "20px");
		title.getStyle().set("color", "orange");
		title.setHeight("30px");
		title.setWidth("90%");
		title3.getStyle().set("vertical-align", "top");	
				  	
				
				
		button1= new Button("Home",click-> {
			UI.getCurrent().navigate("");	
		});
		button1.setTabIndex(1);
		button1.getStyle().set("border-radius", "0px");
			
		//Button button2 = new Button("Contactos");
		button2= new Button("Contactos",click-> {
			UI.getCurrent().navigate("contactos");	
		});
		button2.setTabIndex(2);
		button2.getStyle().set("border-radius", "0px");
			
		button3= new Button("Reportes",click->{
		});
				
		button3.setTabIndex(3);
		button3.getStyle().set("border-radius", "0px");
				
		button4= new Button("Complementos");
		button4.setTabIndex(4);
		button4.getStyle().set("border-radius", "0px");
				
		title2.add(new Text("Todos los derechos Reservados \u00a9")); 
		title2.getStyle().set("color", "orange");
		title2.getStyle().set("font-size", "10px");
		    
		// Configure layouts
		setSizeFull();
		setPadding(false);
		setSpacing(false);
		header.setWidth("100%");
		header.setHeight("80px");
		//header.setPadding(true);
		center.setWidth("100%");
		navBar.setWidth("200px");
		subnavBar.setWidth(navBar.getWidth());
		content.setWidth("100%");
		footer.setWidth("100%");
		footer.setHeight("50px");
		footer.setPadding(true);
		footer.setAlignItems(Alignment.CENTER);
		footer.setFlexGrow(0);
		  
		header.getStyle().set("border-bottom", "1px solid #476EBE");
		navBar.getStyle().set("border-right", "1px solid #476EBE");
		footer.getStyle().set("border-top", "1px solid #476EBE");
		  		  		   
		headersub.add(buttondarkmode,buttonactivity,buttonlogin,buttonlogout);
		headersub.setAlignItems(Alignment.END);
		header.add(button,title,headersub);   
		   
		header.setAlignItems(Alignment.BASELINE);	
		header.setFlexGrow(0);
		header.getStyle().set("padding-left", "1px");
		header.getStyle().set("padding-right", "1px");
		 
		footer.add(title2);
		//subnavBar.add(title3);
		navBar.add(button1,button2,button3,button4);
		navBar.setAlignItems(Alignment.STRETCH);		   
		navBar.setFlexGrow(0);
		navBar.setMargin(false);
		navBar.getStyle().set("padding-left", "1px");
		navBar.getStyle().set("padding-right", "1px");
		//navBar.setMargin();
		// Compose layout
		center.add(navBar, content);
		center.setFlexGrow(1, navBar);
		add(header, center, footer);
		expand(center);
		 		   
		button.addClickListener(event->{if(navBar.isVisible()==true) {navBar.setVisible(false);}else{navBar.setVisible(true);}});   
		   
		   
	 }
}
