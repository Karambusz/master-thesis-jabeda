package edu.agh.jabeda.server.domain.exception;

public class CategoryNotFoundException extends JabedaException{

    public CategoryNotFoundException(String name) {
        super(String.format("Category with name=<%s> not found!", name), ErrorCode.NOT_FOUND);
    }
}
