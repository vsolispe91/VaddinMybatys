package pe.com.uba.serviceimpl;

import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.vaadin.flow.server.VaadinServletService;

import pe.com.uba.iservice.IPersonaService;


public class BusinessServiceUtility {

	static Log logger = LogFactory.getLog(BusinessServiceUtility.class);
	
      
    /**
     * Obtiene un servicio de la capa de negocios 
     * @param name El nombre del servicio
     * @return El servicio
     */
	public static IPersonaService getService(String name) {
		ServletContext ctx = VaadinServletService            // com.vaadin.flow.server.VaadinServletService
		        .getCurrentServletRequest()     // Returns a javax.servlet.http.HttpServletRequest
		        .getServletContext()            // Returns a `javax.servlet.ServletContext`. 
		;		
		//ServletContext ctx = (ServletContext)VaadinService.getCurrent().getDesktop().getWebApp().getServletContext();
    	return (IPersonaService) WebApplicationContextUtils.getWebApplicationContext(ctx).getBean(name);
	}
	
}
