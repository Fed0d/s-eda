package org.firefrogs.services.impl;

import lombok.AllArgsConstructor;
import org.firefrogs.DTO.MenuDTO;
import org.firefrogs.DTO.RecipeDTO;
import org.firefrogs.entities.Recipe;
import org.firefrogs.repositories.DishTypeRepository;
import org.firefrogs.services.MenuService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Random;

@AllArgsConstructor
@Service
public class MenuServiceImpl implements MenuService {
    RecipeServiceImpl recipeServiceImpl;
    DishTypeRepository dishTypeRepository;

    @Override
    public MenuDTO getMenu() {
        List<RecipeDTO> breakfast = recipeServiceImpl.findRecipesByDishTypeId(dishTypeRepository.findByName("maindish").getId());
        List<RecipeDTO> lunch = recipeServiceImpl.findRecipesByDishTypeId(dishTypeRepository.findByName("soup").getId());
        List<RecipeDTO> dinner = recipeServiceImpl.findRecipesByDishTypeId(dishTypeRepository.findByName("maindish").getId());

        Random rand = new Random();

        List<RecipeDTO> randomBreakfast = rand.ints(0, breakfast.size())
                .distinct()
                .limit(3)
                .mapToObj(breakfast::get)
                .toList();

        List<RecipeDTO> randomLunch = rand.ints(0, lunch.size())
                .distinct()
                .limit(3)
                .mapToObj(lunch::get)
                .toList();

        List<RecipeDTO> randomDinner = rand.ints(0, dinner.size())
                .distinct()
                .limit(3)
                .mapToObj(dinner::get)
                .toList();

        return new MenuDTO(randomBreakfast, randomLunch, randomDinner);


    }
}
