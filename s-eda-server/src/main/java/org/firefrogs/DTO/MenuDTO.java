package org.firefrogs.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class MenuDTO {
    private List<RecipeDTO> breakfast;
    private List<RecipeDTO> lunch;
    private List<RecipeDTO> dinner;
}
