package edu.agh.jabeda.server.domain.exception;

public class SubscriberAlreadyExistsException extends JabedaException {

    public SubscriberAlreadyExistsException(String email) {
        super(String.format("Subscriber with email=<%s> already exists!", email),
                JabedaErrorCode.SUBSCRIBER_ALREADY_EXISTS);
    }
}
