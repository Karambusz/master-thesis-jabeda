package edu.agh.jabeda.server.domain.exception;

public class CategoryNotFoundException extends JabedaNotFoundException {

    public CategoryNotFoundException(String name) {
        super(String.format("Category with name=<%s> not found!", name), JabedaErrorCode.CATEGORY_NOT_FOUND);
    }
}
