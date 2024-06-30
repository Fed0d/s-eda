package org.firefrogs.services;

import org.firefrogs.DTO.IngredientDTO;
import org.firefrogs.DTO.RecipeDTO;

import java.util.List;

public interface RecipeService {
    List<IngredientDTO> findIngredientsByRecipeId(Long recipeId);
    List<RecipeDTO> findRecipesByDishTypeId(Long dishTypeId);
}
