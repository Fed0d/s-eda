package org.firefrogs.services;

import org.firefrogs.entities.IngredientWithWeight;
import org.firefrogs.entities.Recipe;
import org.firefrogs.entities.RecipeIngredient;

import java.util.List;

public interface RecipeService {
    List<Recipe> findAllRecipes();
    Recipe findRecipeById(Long id);
    List<IngredientWithWeight> findIngredientsWithWeightByRecipeId(Long recipeId);
}
