package edu.agh.jabeda.server.domain.exception;

public class JabedaException extends RuntimeException {
    private final ErrorCode errorCode;

    public JabedaException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return this.errorCode;
    }

}
