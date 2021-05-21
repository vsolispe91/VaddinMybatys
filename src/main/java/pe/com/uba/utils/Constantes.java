package pe.com.uba.utils;

public interface Constantes {

	/* SEPARADOR PARA TEXTO HASH */
	public static final String HASH_SEPARADOR = ",";
	
	/* MAXIMO DESFASE SEC */
	public static final String MAX_DESFASE_SEC = "MAX_DESFASE_SEGUNDOS";
	
	/* NOMBRE DE LAS OPERACIONES */
	public static final String OP_NAMES_ADM = "OPERACIONES.A";
	public static final String OP_NAMES_FIN = "OPERACIONES.F";
	public static final String OP_NAMES_TST = "OPERACIONES.T";
	
	/* Tipo de operaciones */
	public static final char TIPO_ADM= 'A';
	public static final char TIPO_FIN= 'F';
	public static final char TIPO_TST= 'T';
	
	/*
	 * Codigos de respuesta
	 */
	
	/**
	 * Operacion exitosa
	 */
	public static final String RC_OK = "0";
	
	/**
	 * Error de validacion de datos
	 */
	public static final String RC_VAL_DATA = "201";
	
	/**
	 * Error de seguridad SEC_HASH
	 */
	public static final String RC_VAL_SEC = "202";
	
	/**
	 * Error interno
	 */
	public static final String RC_ERR_INT = "203";
	
	/**
	 * Error de diferencia de tiempo UTC
	 */
	public static final String RC_DIF_TIM = "204";
	
	/**
	 * Error en nombre de operacion invocada
	 */
	public static final String RC_ERR_OPE = "205";
	
	/**
	 * Elemento no encontrado
	 */
	public static final String RC_NOT_FND = "206";
	
	/**
	 * Estado invalido
	 */
	public static final String RC_EST_INV = "207";

	/**
	 * Elemento duplicado
	 */
	public static final String RC_VAL_DUP = "208";
	
	/**
	 * Error del HSM UBA
	 */
	public static final String RC_HSM_ERR = "220";

	/*
	 * Datos obligatorios para el request
	 */
	public static final String KEY_ID_CONEXION = "ID_CONEXION"; // ASIGNADO POR UNIBANCA CHAR(10)
	public static final String KEY_INSTITUCION ="INSTITUCION"; // INTEGER
	public static final String KEY_TIEMPO_UTC ="TIEMPO_UTC"; // INTEGER
	public static final String KEY_SEC_HASH = "SEC_HASH"; // CHAR(32)
	public static final String KEY_ID_USUARIO ="ID_USUARIO"; // CHAR(15)
	public static final String KEY_SECUENCIA ="SECUENCIA"; // INTEGER (hasta 6 digitos)
	
	/*
	 *  Datos obligatorios para respuestas
	 */
	public static final String KEY_RC ="RC"; // INTEGER
	public static final String KEY_RC_DESC ="RC_DESC"; // CHAR(100)
	
}
