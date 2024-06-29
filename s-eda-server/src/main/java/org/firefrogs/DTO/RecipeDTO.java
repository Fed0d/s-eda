package org.firefrogs.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RecipeDTO {
    private Long id;
    private String name;
    private String description;
    private String cookingTime;
    private String additionalTime;
    private String photoLink;
    private Double calories;
    private Double proteins;
    private Double fats;
    private Double carbohydrates;
}
