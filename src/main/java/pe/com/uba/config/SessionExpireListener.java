package pe.com.uba.config;

import com.vaadin.flow.server.VaadinSession;
import java.util.Enumeration;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class SessionExpireListener implements HttpSessionListener {

	 @Override
	    public void sessionCreated(HttpSessionEvent hse) {
	        System.out.println("Session created");
	    }

	    @Override
	    public void sessionDestroyed(HttpSessionEvent hse) {
	        System.out.println("Session destroyed");
	        Enumeration e = hse.getSession().getAttributeNames();
	        while (e.hasMoreElements()) {
	            Object o = hse.getSession().getAttribute((String) e.nextElement());
	            if (o instanceof VaadinSession) {
	                VaadinSession vs = (VaadinSession) o;
	            }
	        }        
	    }
}
