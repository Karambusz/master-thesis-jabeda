package edu.agh.jabeda.server.domain.exception;

public class UserBannedException extends JabedaException {

    public UserBannedException(String id) {
        super(String.format("User with device id=<%s> is banned!", id), JabedaErrorCode.DEVICE_IS_BANNED);
    }
}