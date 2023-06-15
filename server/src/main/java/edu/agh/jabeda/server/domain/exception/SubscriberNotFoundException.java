package edu.agh.jabeda.server.domain.exception;

public class SubscriberNotFoundException extends JabedaNotFoundException {

    public SubscriberNotFoundException(String email) {
        super(String.format("Subscriber with email=<%s> not found!", email),
                JabedaErrorCode.SUBSCRIBER_NOT_FOUND);
    }

    public SubscriberNotFoundException(Integer id) {
        super(String.format("Subscriber with id=<%d> not found!", id),
                JabedaErrorCode.SUBSCRIBER_NOT_FOUND);
    }
}
