package org.firefrogs.controllers;

import lombok.AllArgsConstructor;
import org.firefrogs.entities.IngredientWithWeight;
import org.firefrogs.entities.Recipe;
import org.firefrogs.services.impl.RecipeServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/recipes")
@AllArgsConstructor
public class RecipeController {
    private final RecipeServiceImpl service;

    @GetMapping
    public List<Recipe> getAllRecipes() {
        return service.findAllRecipes();
    }

    @GetMapping("/ingredients/{recipeId}")
    public List<IngredientWithWeight> getIngredientsWithWeightByRecipeId(@PathVariable("recipeId") Long recipeId) {
        return service.findIngredientsWithWeightByRecipeId(recipeId);
    }
}
