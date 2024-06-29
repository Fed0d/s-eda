package org.firefrogs.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class IngredientWithWeight {
    private Ingredient ingredient;
    private Integer weight;
}
