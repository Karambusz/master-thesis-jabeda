package edu.agh.jabeda.server.domain.exception;

public enum JabedaErrorCode {
    BAD_REQUEST("400"),
    CATEGORY_NOT_FOUND("Category.NotFound"),
    ROLE_NOT_FOUND("Role.NotFound"),
    SUBSCRIBER_NOT_FOUND("Subscriber.NotFound"),
    SUBSCRIBER_ALREADY_EXISTS("Subscriber.AlreadyExists");

    private final String errorCode;

    JabedaErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return this.errorCode;
    }
}
