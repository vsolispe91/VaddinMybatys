package pe.com.uba.utils;



import org.apache.commons.lang3.StringUtils;

import pe.com.uba.model.Persona;

public class PersonFilter {

	
	
	String apellidos="";

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String name) {
        this.apellidos = name;
    } 

    public boolean test(Persona person) {
        if (apellidos.length() > 0 && !StringUtils.containsIgnoreCase(String.valueOf(person.getApellidos()),apellidos)) {
            return false;
        }
        return true;
    }
}
