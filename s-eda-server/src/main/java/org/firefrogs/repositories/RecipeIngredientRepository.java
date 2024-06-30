package org.firefrogs.repositories;

import org.firefrogs.entities.RecipeIngredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RecipeIngredientRepository extends JpaRepository<RecipeIngredient, Long> {
    Optional<List<RecipeIngredient>> findByRecipeId(Long recipeId);
}
