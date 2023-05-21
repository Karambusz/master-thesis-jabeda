package edu.agh.jabeda.server.domain.exception;

public class JabedaNotFoundException extends JabedaException {
    public JabedaNotFoundException(String message, JabedaErrorCode errorCode) {
        super(message, errorCode);
    }
}
