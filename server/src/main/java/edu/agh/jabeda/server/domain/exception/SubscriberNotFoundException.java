package edu.agh.jabeda.server.domain.exception;

public class SubscriberNotFoundException extends JabedaException{

    public SubscriberNotFoundException(String email) {
        super(String.format("Subscriber with email=<%s> not found!", email),
                JabedaErrorCode.SUBSCRIBER_NOT_FOUND);
    }
}
