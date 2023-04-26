package edu.agh.jabeda.server.domain.exception;

public class RoleNotFoundException extends JabedaException {

    public RoleNotFoundException(String name) {
        super(String.format("Role with name=<%s> not found!", name), ErrorCode.NOT_FOUND);
    }
}
