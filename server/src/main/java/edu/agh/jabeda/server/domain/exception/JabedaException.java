package edu.agh.jabeda.server.domain.exception;

public class JabedaException extends RuntimeException {
    private final JabedaErrorCode errorCode;

    public JabedaException(String message, JabedaErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public JabedaErrorCode getErrorCode() {
        return this.errorCode;
    }

}
