package org.firefrogs.services;

import lombok.AllArgsConstructor;
import org.firefrogs.dto.IngredientResponse;
import org.firefrogs.dto.RecipeResponse;
import org.firefrogs.entities.Recipe;
import org.firefrogs.repositories.RecipeIngredientRepository;
import org.firefrogs.repositories.RecipeRepository;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;

@Service
@AllArgsConstructor
public class RecipeService {
    private final RecipeRepository recipeRepository;
    private final RecipeIngredientRepository recipeIngredientRepository;

    public List<IngredientResponse> findIngredientsByRecipeId(Long recipeId) {
        return recipeIngredientRepository.findByRecipeId(recipeId).orElseThrow()
                .stream()
                .map(recipeIngredient -> IngredientResponse.builder()
                        .ingredient(recipeIngredient.getIngredient())
                        .weight(recipeIngredient.getWeight())
                        .build())
                .toList();
    }

    public List<RecipeResponse> findRecipesByDishTypeId(Long dishTypeId) {
        List<Recipe> recipes = recipeRepository.findByDishTypeId(dishTypeId).orElseThrow();
        recipes.forEach(recipe -> recipe.setIngredients(findIngredientsByRecipeId(recipe.getId())));

        return recipes
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private RecipeResponse mapToResponse(Recipe recipe) {
        Integer totalWeight = 0;
        Double totalCalories = 0.0;
        Double totalProteins = 0.0;
        Double totalFats = 0.0;
        Double totalCarbohydrates = 0.0;

        for (IngredientResponse ingredient : recipe.getIngredients()) {
            totalWeight += ingredient.getWeight();
            totalCalories += ingredient.getIngredient().getCalories();
            totalProteins += ingredient.getIngredient().getProteins();
            totalFats += ingredient.getIngredient().getFats();
            totalCarbohydrates += ingredient.getIngredient().getCarbohydrates();
        }

        DecimalFormat df = new DecimalFormat("#.#", new DecimalFormatSymbols(Locale.US));

        totalCalories = Double.valueOf(df.format(totalCalories / totalWeight * 100));
        totalProteins = Double.valueOf(df.format(totalProteins / totalWeight * 100));
        totalFats = Double.valueOf(df.format(totalFats / totalWeight * 100));
        totalCarbohydrates = Double.valueOf(df.format(totalCarbohydrates / totalWeight * 100));

        return RecipeResponse.builder()
                .id(recipe.getId())
                .name(recipe.getName())
                .description(recipe.getDescription())
                .cookingTime(recipe.getCookingTime())
                .additionalTime(recipe.getAdditionalTime())
                .photoLink(recipe.getPhotoLink())
                .calories(totalCalories)
                .proteins(totalProteins)
                .fats(totalFats)
                .carbohydrates(totalCarbohydrates)
                .build();
    }
}
