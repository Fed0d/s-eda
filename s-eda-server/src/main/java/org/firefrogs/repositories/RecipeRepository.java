package org.firefrogs.repositories;

import org.firefrogs.entities.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    Optional<List<Recipe>> findByDishTypeId(Long dishTypeId);
}
