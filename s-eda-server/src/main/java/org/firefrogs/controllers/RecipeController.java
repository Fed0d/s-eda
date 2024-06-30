package org.firefrogs.controllers;

import lombok.AllArgsConstructor;
import org.firefrogs.DTO.IngredientDTO;
import org.firefrogs.services.impl.RecipeServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/recipes")
public class RecipeController {
    private final RecipeServiceImpl recipeService;

    @GetMapping("/ingredients/{recipeId}")
    public ResponseEntity<List<IngredientDTO>>getIngredientsWithWeightByRecipeId(@PathVariable("recipeId") Long recipeId) {
        List<IngredientDTO> ingredients = recipeService.findIngredientsByRecipeId(recipeId);

        return ResponseEntity.ok(ingredients);
    }
}
