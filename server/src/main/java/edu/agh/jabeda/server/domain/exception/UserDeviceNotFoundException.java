package edu.agh.jabeda.server.domain.exception;

public class UserDeviceNotFoundException extends JabedaNotFoundException {

    public UserDeviceNotFoundException(String id) {
        super(String.format("User device with id=<%s> not found!", id),
                JabedaErrorCode.DEVICE_NOT_FOUND);
    }

    public UserDeviceNotFoundException() {
        super("User device not found!", JabedaErrorCode.DEVICE_NOT_FOUND);
    }
}

