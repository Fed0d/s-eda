package org.firefrogs.services.impl;

import lombok.AllArgsConstructor;
import org.firefrogs.entities.Ingredient;
import org.firefrogs.entities.IngredientWithWeight;
import org.firefrogs.entities.Recipe;
import org.firefrogs.entities.RecipeIngredient;
import org.firefrogs.repositories.RecipeIngredientRepository;
import org.firefrogs.repositories.RecipeRepository;
import org.firefrogs.services.RecipeService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class RecipeServiceImpl implements RecipeService {
    private final RecipeRepository repository;
    private final RecipeIngredientRepository recipeIngredientRepository;

    @Override
    public List<Recipe> findAllRecipes() {
        List<Recipe> recipes = repository.findAll();

        for (Recipe recipe : recipes)
            recipe.setIngredientsWithWeight(findIngredientsWithWeightByRecipeId(recipe.getId()));

        return recipes;
    }

    @Override
    public Recipe findRecipeById(Long id) {
        Recipe recipe = repository.findById(id).orElse(null);

        if (recipe == null)
            throw new RuntimeException("Recipe not found");

        recipe.setIngredientsWithWeight(findIngredientsWithWeightByRecipeId(recipe.getId()));
        return recipe;
    }

    @Override
    public List<IngredientWithWeight> findIngredientsWithWeightByRecipeId(Long recipeId) {
        return recipeIngredientRepository.findByRecipeId(recipeId).stream()
                .map(recipeIngredient -> new IngredientWithWeight(recipeIngredient.getIngredient(), recipeIngredient.getWeight()))
                .collect(Collectors.toList());
    }
}
