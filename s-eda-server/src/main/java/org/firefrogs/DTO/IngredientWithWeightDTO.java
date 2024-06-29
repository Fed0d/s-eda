package org.firefrogs.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.firefrogs.entities.Ingredient;

@AllArgsConstructor
@Getter
@Setter
public class IngredientWithWeightDTO {
    private Ingredient ingredient;
    private Integer weight;
}
