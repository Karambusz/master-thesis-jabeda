package edu.agh.jabeda.server.domain;

import java.util.HashSet;
import java.util.Set;

public class Category {
    private int idCategory;

    private String categoryName;

    private Set<Subscriber> subscribers = new HashSet<>();

    private Set<Problem> problems = new HashSet<>();
}
