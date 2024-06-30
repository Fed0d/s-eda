package org.firefrogs.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Ответ с меню на день")
public class MenuResponse {
    @Schema(description = "Завтрак", example = "[{\"id\": 1, \"name\": \"Борщ\", " +
            "\"description\": \"Борщ - это русский суп\", \"cookingTime\": \"1 час\", " +
            "\"additionalTime\": \"30 минут\", \"photoLink\": \"https://example.com/photo.jpg\", " +
            "\"calories\": 1000.2, \"proteins\": 50.1, \"fats\": 20.5, \"carbohydrates\": 150.3}]")
    private List<RecipeResponse> breakfast;
    @Schema(description = "Обед", example = "[{\"id\": 1, \"name\": \"Борщ\", " +
            "\"description\": \"Борщ - это русский суп\", \"cookingTime\": \"1 час\", " +
            "\"additionalTime\": \"30 минут\", \"photoLink\": \"https://example.com/photo.jpg\", " +
            "\"calories\": 1000.2, \"proteins\": 50.1, \"fats\": 20.5, \"carbohydrates\": 150.3}]")
    private List<RecipeResponse> lunch;
    @Schema(description = "Ужин", example = "[{\"id\": 1, \"name\": \"Борщ\", " +
            "\"description\": \"Борщ - это русский суп\", \"cookingTime\": \"1 час\", " +
            "\"additionalTime\": \"30 минут\", \"photoLink\": \"https://example.com/photo.jpg\", " +
            "\"calories\": 1000.2, \"proteins\": 50.1, \"fats\": 20.5, \"carbohydrates\": 150.3}]")
    private List<RecipeResponse> dinner;
}
