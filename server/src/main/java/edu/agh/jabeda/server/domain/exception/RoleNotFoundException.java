package edu.agh.jabeda.server.domain.exception;

public class RoleNotFoundException extends JabedaNotFoundException {

    public RoleNotFoundException(String name) {
        super(String.format("Role with name=<%s> not found!", name), JabedaErrorCode.ROLE_NOT_FOUND);
    }
}
