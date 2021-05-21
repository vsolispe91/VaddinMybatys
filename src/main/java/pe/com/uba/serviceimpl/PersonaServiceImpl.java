package pe.com.uba.serviceimpl;



import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.com.uba.dao.PersonaDAO;
import pe.com.uba.iservice.IPersonaService;
import pe.com.uba.model.Persona;;


@Service("PersonaService")
@Transactional
public class PersonaServiceImpl implements IPersonaService{

	
	private static Log logger = LogFactory.getLog(PersonaServiceImpl.class);
	
	
	@Autowired
	private PersonaDAO personadao;
	

	@Override
	public List<Persona> buscarpersonas() {
		// TODO Auto-generated method stub
		logger.info("Buscamos personas");
		List<Persona> personas=personadao.buscarpersonas();
		logger.info("Encontramos personas: "+personas.size());	
		return personas;
	}


	@Override
	@Transactional(rollbackFor = Throwable.class)
	public int borrarpersona(Persona persona) throws UnsupportedEncodingException, InterruptedException, Exception{
		// TODO Auto-generated method stub
		logger.info("Borramos persona");
		int deleterows=personadao.deletePersona(persona);
		logger.info("Personas borradas ["+deleterows+"]");
		return deleterows;
	}


	@Override
	public int actualizarpersona(Persona persona) throws UnsupportedEncodingException, InterruptedException, Exception {
		// TODO Auto-generated method stub		
		logger.info("Actualizamos persona"+persona.getId()+persona.getNombres()+persona.getApellidos());
		int updaterows=personadao.actualizarPersona(persona);
		logger.info("Personas Actualizadas ["+updaterows+"]");
		return updaterows;
	}


	@Override
	public int registrapersona(Persona persona) throws UnsupportedEncodingException, InterruptedException, Exception {
		// TODO Auto-generated method stub
		logger.info("Registramos persona"+persona.getNombres()+persona.getApellidos());
		int registerrows=personadao.registraPersona(persona);
		logger.info("Registrado Personas ["+registerrows+"]");
		return registerrows;
	}
	
}
