package org.firefrogs.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.firefrogs.entities.Ingredient;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Ответ с ингредиентом и его весом в рецепте")
public class IngredientResponse {
    @Schema(description = "Ингредиент")
    private Ingredient ingredient;
    @Schema(description = "Вес ингредиента", example = "100")
    private Integer weight;
}
