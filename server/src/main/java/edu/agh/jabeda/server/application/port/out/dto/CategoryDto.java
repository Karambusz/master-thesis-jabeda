package edu.agh.jabeda.server.application.port.out.dto;

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
public class CategoryDto {
    private int idCategory;

    private String categoryName;

    private Set<SubscriberDto> subscribers = new HashSet<>();

    private Set<ProblemDto> problems = new HashSet<>();
}
