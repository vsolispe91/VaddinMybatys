package pe.com.uba.config;

import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import pe.com.uba.iservice.IPersonaService;
import pe.com.uba.model.Persona;



@Configuration
public class Utils {

	private static Log logger = LogFactory.getLog(Utils.class);
	
	List<Persona> personList;
	
	@Autowired
	private IPersonaService serviciopersona;

	@PostConstruct
	public void inicializa() {

	
		
		personList = serviciopersona.buscarpersonas();
		
		
		
	}
	
}
