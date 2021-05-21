package pe.com.uba.security;


import org.springframework.stereotype.Component;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinServiceInitListener;

import pe.com.uba.views.LoginView;

@Component 
public class ConfigureUIServiceInitListener implements VaadinServiceInitListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void serviceInit(ServiceInitEvent event) {
		System.out.println("serviceInit 1");
		event.getSource().addUIInitListener(uiEvent -> { 
			System.out.println("serviceInit");
			final UI ui = uiEvent.getUI();
			ui.addBeforeEnterListener(this::authenticateNavigation);
		});
		System.out.println("serviceInit 3 ");
	}

	private void authenticateNavigation(BeforeEnterEvent event) {
		System.out.println("authenticateNavigation 1");
		if (!LoginView.class.equals(event.getNavigationTarget())
		    && !SecurityUtils.isUserLoggedIn()) { 
			System.out.println("authenticateNavigation 2");
			event.rerouteTo(LoginView.class);
		}
		System.out.println("authenticateNavigation 3");
	}
}