package org.firefrogs.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.firefrogs.entities.Ingredient;

@Getter
@AllArgsConstructor
public class IngredientDTO {
    private Ingredient ingredient;
    private Integer weight;
}
