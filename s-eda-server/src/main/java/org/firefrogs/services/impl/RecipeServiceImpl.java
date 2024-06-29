package org.firefrogs.services.impl;

import lombok.AllArgsConstructor;
import org.firefrogs.DTO.IngredientWithWeightDTO;
import org.firefrogs.DTO.RecipeDTO;
import org.firefrogs.entities.Recipe;
import org.firefrogs.repositories.RecipeIngredientRepository;
import org.firefrogs.repositories.RecipeRepository;
import org.firefrogs.services.RecipeService;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class RecipeServiceImpl implements RecipeService {
    private final RecipeRepository recipeRepository;
    private final RecipeIngredientRepository recipeIngredientRepository;

    @Override
    public List<IngredientWithWeightDTO> findIngredientsWithWeightByRecipeId(Long recipeId) {
        return recipeIngredientRepository.findByRecipeId(recipeId)
                .stream()
                .map(recipeIngredient -> new IngredientWithWeightDTO(recipeIngredient.getIngredient(), recipeIngredient.getWeight()))
                .collect(Collectors.toList());
    }

    @Override
    public List<RecipeDTO> findRecipesByDishTypeId(Long dishTypeId) {
        List<Recipe> recipes = recipeRepository.findByDishTypeId(dishTypeId);
        recipes.forEach(recipe -> recipe.setIngredientsWithWeight(findIngredientsWithWeightByRecipeId(recipe.getId())));
        List<RecipeDTO> recipesDTO = recipes
                .stream()
                .map(this::mapToDTO)
                .toList();

        return recipesDTO;
    }

    private RecipeDTO mapToDTO(Recipe recipe) {
        Integer totalWeight = 0;
        Double totalCalories = 0.0;
        Double totalProteins = 0.0;
        Double totalFats = 0.0;
        Double totalCarbohydrates = 0.0;

        for (IngredientWithWeightDTO ingredientWithWeight : recipe.getIngredientsWithWeight()) {
            totalWeight += ingredientWithWeight.getWeight();
            totalCalories += ingredientWithWeight.getIngredient().getCalories();
            totalProteins += ingredientWithWeight.getIngredient().getProteins();
            totalFats += ingredientWithWeight.getIngredient().getFats();
            totalCarbohydrates += ingredientWithWeight.getIngredient().getCarbohydrates();
        }

        DecimalFormat df = new DecimalFormat("#.#", new DecimalFormatSymbols(Locale.US));

        totalCalories = Double.valueOf(df.format(totalCalories / totalWeight * 100));
        totalProteins = Double.valueOf(df.format(totalProteins / totalWeight * 100));
        totalFats = Double.valueOf(df.format(totalFats / totalWeight * 100));
        totalCarbohydrates = Double.valueOf(df.format(totalCarbohydrates / totalWeight * 100));

        return new RecipeDTO(recipe.getId(), recipe.getName(), recipe.getDescription(), recipe.getCookingTime(),
                recipe.getAdditionalTime(), recipe.getPhotoLink(), totalCalories, totalProteins, totalFats, totalCarbohydrates);
    }
}
