package pe.com.uba.iservice;


import java.io.UnsupportedEncodingException;
import java.util.List;

import pe.com.uba.model.Persona;


public interface IPersonaService {
        
	public List<Persona> buscarpersonas();
	
	public int borrarpersona(Persona persona) throws UnsupportedEncodingException, InterruptedException, Exception;
	
	public int actualizarpersona(Persona persona) throws UnsupportedEncodingException, InterruptedException, Exception;
	
	public int registrapersona(Persona persona) throws UnsupportedEncodingException, InterruptedException, Exception;
	
}
