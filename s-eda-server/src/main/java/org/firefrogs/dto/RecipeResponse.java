package org.firefrogs.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Ответ с рецептом")
public class RecipeResponse {
    @Schema(description = "Идентификатор рецепта", example = "1")
    private Long id;
    @Schema(description = "Название рецепта", example = "Борщ")
    private String name;
    @Schema(description = "Описание рецепта", example = "Борщ - это русский суп")
    private String description;
    @Schema(description = "Время приготовления", example = "1 час")
    private String cookingTime;
    @Schema(description = "Дополнительное время", example = "30 минут")
    private String additionalTime;
    @Schema(description = "Ссылка на фото", example = "https://example.com/photo.jpg")
    private String photoLink;
    @Schema(description = "Количество калорий", example = "1000.2")
    private Double calories;
    @Schema(description = "Количество белков", example = "50.1")
    private Double proteins;
    @Schema(description = "Количество жиров", example = "20.5")
    private Double fats;
    @Schema(description = "Количество углеводов", example = "150.3")
    private Double carbohydrates;
}
