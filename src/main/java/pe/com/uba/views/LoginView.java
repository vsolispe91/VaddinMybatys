package pe.com.uba.views;


import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

//import com.vaadin.flow.server.Strea



@Route(value = "login")
@PageTitle("LOGIN")
public class LoginView extends BasicLayout implements BeforeEnterObserver{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	LoginForm login = new LoginForm();	
	
	 
	
	public LoginView() {
		
		String id_session=VaadinSession.getCurrent().getSession().getId();
	    
	    System.out.println("ID SESSION LOGIN:"+id_session);
	    
	    // 
	    
	    buttonlogout.setEnabled(false);		
	    login.setAction("login");
		content.add(login);
		
		//VaadinSession.getCurrent().getSession().setMaxInactiveInterval (10);

	}
	
	
	
	@Override
	public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
		// inform the user about an authentication error
		if(beforeEnterEvent.getLocation() 
        .getQueryParameters()
        .getParameters()
        .containsKey("error")) {
            login.setError(true);
        }
	}

}


//component.addLoginListener(e -> {
//boolean isAuthenticated = false;
//String usuario=e.getUsername();
//String clave=e.getPassword();	    
//
//
//
//		    
//if(usuario.equalsIgnoreCase("Victor")&&clave.equalsIgnoreCase("reinita")) {
//	isAuthenticated=true;		    	
//}else {
//	isAuthenticated=false;
//
//}		    
//if (isAuthenticated) {		    	
//	UI.getCurrent().navigate("layout");		    	
//} else {
//    component.setError(true);
//}
//});
