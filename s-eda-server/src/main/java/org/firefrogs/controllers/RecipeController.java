package org.firefrogs.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.firefrogs.dto.IngredientResponse;
import org.firefrogs.services.RecipeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/recipes")
@Tag(name = "Рецепты")
public class RecipeController {
    private final RecipeService recipeService;

    @Operation(summary = "Получение ингредиентов с их весом по id рецепта")
    @GetMapping("/ingredients/{recipeId}")
    public ResponseEntity<List<IngredientResponse>> getIngredientsWithWeightByRecipeId(@PathVariable("recipeId") Long recipeId) {
        List<IngredientResponse> ingredients = recipeService.findIngredientsByRecipeId(recipeId);

        return ResponseEntity.ok(ingredients);
    }
}
