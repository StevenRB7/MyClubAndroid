package com.myclub.myapplication.utils;

public class Constantes {
    public static final String ID_PROYECTO = "6";

    public static final String ID_ESTADO_ACTIVO = "";

    public static final String ID_ROL_PERSONA_NATURAL = "1";
    public static final String BASE_URL_PERSONAS = "https://apiamingenieria.app/";
    public static final String BASE_MY_CLUB = "http://52.175.225.67:63202/";

    public static final String PREF_NAME = "StoreMyPrefs";
    public static final String PREF_ACTIVE_SESSION = "ActiveSessionUser";
    public static final String PREF_ACTIVE_SESSION_ROL = "ActiveSessionRol";

    public static final String PREF_ID_USUARIO = "StoreIdUser";
    public static final String PREF_ID_ROL = "StoreIdRol";


    public static String E_EMAIL_INVALID = "Correo no valido";
    public static String M_VALIDATE_DATA = "We are validating your data please wait.";
    public static String M_ERROR_VALIDATE_DATA = "An error occurred while validating your data, please try again.";
    public static String M_VALIDATE_CODE = "We are verifying your code, please wait a moment.";
    public static String M_E_VERIFY_CODE = "Invalid verification code, please enter a valid code.";

    public static final String ERROR_FORMULARIO_VACIO = "Campo requerido";
    public static final String ERROR_RETROFIT = "An internal error has occurred, please try again.";
    public static final String M_ERROR_BUY_PLAN = "An error occurred while purchasing the plan" + "\n" + "please try again.";
    public static final String M_SUCCES_BUY_PLAN = "Successfully purchased plan.";
    public static final String MESSAGE_ALERT_LOGIN = "We are validating your data" + "\n" + "Please wait a moment.";


    public static final int CODIGO_EXITOSO = 200;
    public static String ID_PERSONA = "0";
    public static final int CODIGO_ERROR = 500;



    /**
     * CODIGOS DE RESPUESTA UTILES
     */

    public static final int CodeSuccess = 200;
    public static final int CodeInvalidArgument = 400;
    public static final int CodeNoFoundElement = 404;
    public static final int CodeVerificationCodeInvalid = 405;
    public static final int CodeUnauthorizedAccess = 401;
    public static final int CodePasswordInvalid = 402;
    public static final int CodeAccessProhibited = 403;
    public static final int CodeElementAlreadyExists = 409;
    public static final int CodeServer = 500;


    /**
     * MENSAJES DE RESPUESTA
     */
    public static final String MessageItemNotFound = "Unregistered user, please register";
    public static final String MessageUnauthorizedAccess = "Unverified user, please verify";
    public static final String MessageElementAlreadyExists = "El usuario ingresado ya está registrado";
    public static final String MessageInvalidRequest = "Argumento invalido o petición incorrecta";
    public static final String MessagePasswordInvalid = "Contraseña incorrecta";
    public static final String MessageVerifyCodeInvalid = "Codigo de verificacion incorrecto";
    public static final String MessageErrorServer = "Error Interno";


}
