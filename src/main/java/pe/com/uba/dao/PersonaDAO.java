package pe.com.uba.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import pe.com.uba.model.Persona;


@Repository
public class PersonaDAO  extends BaseDAO{
	
	
	public List<Persona> buscarpersonas() {
		return getSqlSession().selectList("buscarpersonas");
	}
	
	public int deletePersona(Persona persona) {	
		int deletrows=getSqlSession().delete("deletePersona",persona);
		return deletrows;
	}
	
	public int actualizarPersona(Persona persona) {	
		int updaterows=getSqlSession().update("updatePersona",persona);
		return updaterows;
	}
	
	public int registraPersona(Persona persona) {	
		int registerrows=getSqlSession().insert("registraPersona",persona);
		return registerrows;
	}

}
