package fr.skyblock.exceptions;

/**
 * Modélise l'exception pour des paramètres invalides
 */
public class ValParamException extends RuntimeException{

    private final String message;

    /*
     * Obtient une instance de ValParamException
     */
    public ValParamException() {
        message = "";
    }

    /**
     * Obtient une instance de ValParamException.
     *
     * @param mess message associée à l'exception
     */
    public ValParamException(final String mess) {
        message = mess;
    }

    /**
     * Obtient le message et la Trace de l'exception.
     *
     * @return la chaine détaillée + la trace.
     */
    @Override
    public String getMessage() {
        return message + " --> " + super.getMessage();
    }

}
