package edu.agh.jabeda.server.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    private int idCategory;

    private String categoryName;

    private Set<Problem> problems = new HashSet<>();
}
