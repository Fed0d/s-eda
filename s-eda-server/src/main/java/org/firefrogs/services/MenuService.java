package org.firefrogs.services;

import lombok.AllArgsConstructor;
import org.firefrogs.dto.MenuResponse;
import org.firefrogs.dto.RecipeResponse;
import org.firefrogs.repositories.DishTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@AllArgsConstructor
@Service
public class MenuService {
    RecipeService recipeService;
    DishTypeRepository dishTypeRepository;

    public MenuResponse getMenu() {
        List<RecipeResponse> breakfast = recipeService.findRecipesByDishTypeId(dishTypeRepository.findByName("maindish").getId());
        List<RecipeResponse> lunch = recipeService.findRecipesByDishTypeId(dishTypeRepository.findByName("soup").getId());
        List<RecipeResponse> dinner = recipeService.findRecipesByDishTypeId(dishTypeRepository.findByName("salad").getId());

        return MenuResponse.builder()
                .breakfast(getRandomSublist(breakfast, 3))
                .lunch(getRandomSublist(lunch, 3))
                .dinner(getRandomSublist(dinner, 3))
                .build();
    }

    private <T> List<T> getRandomSublist(List<T> list, int limit) {
        Random rand = new Random();
        return rand.ints(0, list.size())
                .distinct()
                .limit(limit)
                .mapToObj(list::get)
                .toList();
    }
}
