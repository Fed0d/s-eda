# Список сущностей, свойств и связей
## Сущности
* user - сущность пользователя
  * user_id - идентификатор пользователя (long, PK)
  * nickname - никнейм пользователя (string, not null, unique)
  * password - пароль пользователя (string, not null)
  * role - роль пользователя (long, not null) (*Foreign key* на **role.id**)
---
* role - сущность роли
  * role_id - идентификатор роли (long, PK)
  * name - название роли (string, not null, unique)
---
* user_parameters - сущность параметров пользователя
  * user_parameters_id - идентификатор параметров пользователя (long, PK)
  * user_id - идентификатор пользователя (long, not null) (*Foreign key* на **user.user_id**)
  * activity - активность пользователя (long, not null) (*Foreign key* на **activity.id**)
  * birth_date - дата рождения пользователя (date, not null)
  * weight - вес пользователя (double, not null)
  * height - рост пользователя (double, not null)
---
* activity - сущность активности
  * activity_id - идентификатор активности (long, PK)
  * name - название активности (string, not null, unique)
---
* user_information - сущность информации о пользователе
  * user_information_id - идентификатор информации о пользователе (long, PK)
  * user_id - идентификатор пользователя (long, not null) (*Foreign key* на **user.user_id**)
  * first_name - имя пользователя (string)
  * last_name - фамилия пользователя (string)
  * email - электронная почта пользователя (string)
  * phone - телефон пользователя (string)
---
* ingredients - сущность ингредиентов
  * ingredient_id - идентификатор ингредиента (long, PK)
  * name - название ингредиента (string, not null, unique)
  * calories - калорийность ингредиента (double, not null)
  * proteins - белки ингредиента (double, not null)
  * fats - жиры ингредиента (double, not null)
  * carbohydrates - углеводы ингредиента (double, not null)
  * meat - мясной ингредиент (boolean, not null)
  * fish - рыбный ингредиент (boolean, not null)
  * milk - молочный ингредиент (boolean, not null)
  * gluten - глютеновый ингредиент (boolean, not null)
  * seafood - морепродукт (boolean, not null)
  * mushrooms - грибной ингредиент (boolean, not null)
  * halal - халяльный ингредиент (boolean, not null)
  * vegan - веганский ингредиент (boolean, not null)
---
* dishes_types - сущность типов блюд
  * dish_type_id - идентификатор типа блюда (long, PK)
  * name - название типа блюда (string, not null, unique)
---
* recipes - сущность рецептов
  * recipe_id - идентификатор рецепта (long, PK)
  * name - название рецепта (string, not null, unique)
  * description - описание рецепта (string, not null)
  * main_time - время приготовления (long, not null)
  * additional_time - дополнительное время приготовления (long, not null)
  * photo_link - ссылка на фото рецепта (string)
  * dish_type_id - идентификатор типа блюда (long, not null) (*Foreign key* на **dishes_types.dish_type_id**)
---
* recipes_ingredients - сущность ингредиентов рецептов
  * recipe_id - идентификатор рецепта (long, not null) (*Foreign key* на **recipes.recipe_id**)
  * ingredient_id - идентификатор ингредиента (long, not null) (*Foreign key* на **ingredients.ingredient_id**)
  * weight - вес ингредиента (double, not null)
