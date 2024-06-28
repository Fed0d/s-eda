package com.example.s_eda_app.entity

class Dish(val id: Int,
           val title:String?,
           val photoLink: String?,
           val  time: String?,
           val additionTime: String?,
           val recipe: String?,
           val calories:Float?,
           val p:Float?,
           val f:Float?,
           val c:Float?,
           val ingredients:List<Ingredient> =  mutableListOf<Ingredient>()) {
}