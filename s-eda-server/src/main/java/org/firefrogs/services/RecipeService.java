package org.firefrogs.services;

import org.firefrogs.DTO.IngredientWithWeightDTO;
import org.firefrogs.DTO.RecipeDTO;

import java.util.List;

public interface RecipeService {
    List<IngredientWithWeightDTO> findIngredientsWithWeightByRecipeId(Long recipeId);
    List<RecipeDTO> findRecipesByDishTypeId(Long dishTypeId);
}
