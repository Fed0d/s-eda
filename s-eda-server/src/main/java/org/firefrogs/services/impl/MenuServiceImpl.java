package org.firefrogs.services.impl;

import lombok.AllArgsConstructor;
import org.firefrogs.DTO.MenuDTO;
import org.firefrogs.DTO.RecipeDTO;
import org.firefrogs.repositories.DishTypeRepository;
import org.firefrogs.services.MenuService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@AllArgsConstructor
@Service
public class MenuServiceImpl implements MenuService {
    RecipeServiceImpl recipeService;
    DishTypeRepository dishTypeRepository;

    @Override
    public MenuDTO getMenu() {
        List<RecipeDTO> breakfast = recipeService.findRecipesByDishTypeId(dishTypeRepository.findByName("maindish").getId());
        List<RecipeDTO> lunch = recipeService.findRecipesByDishTypeId(dishTypeRepository.findByName("soup").getId());
        List<RecipeDTO> dinner = recipeService.findRecipesByDishTypeId(dishTypeRepository.findByName("maindish").getId());

        breakfast = getRandomSublist(breakfast, 3);
        lunch = getRandomSublist(lunch, 3);
        dinner = getRandomSublist(dinner, 3);

        return new MenuDTO(breakfast, lunch, dinner);
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
