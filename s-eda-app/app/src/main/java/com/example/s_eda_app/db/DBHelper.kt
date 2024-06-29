package com.example.s_eda_app.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.s_eda_app.entity.Dish
import com.example.s_eda_app.entity.Ingredient
class DBHelper(private val context: Context, private val factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, "disease", factory, 1){
    private var isFilled=false
    override fun onCreate(db: SQLiteDatabase?) {
        val query = "CREATE TABLE dishes (id INT PRIMARY KEY, title TEXT, photoLink TEXT, time TEXT, addition_time TEXT, recipe TEXT, calories REAL, p REAL, f REAL, c REAL, numer INT, timestamp DATETIME DEFAULT CURRENT_TIMESTAMP )"
        val query1= "CREATE TABLE ingredients (id INT PRIMARY KEY, name TEXT, weight REAL, calories REAL, p REAL, f REAL, c REAL, FOREIGN KEY(dish_id) REFERENCES dishes(id))"
        db!!.execSQL(query)
        db.execSQL(query1)
    }
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS dishes")
        db.execSQL("DROP TABLE IF EXISTS ingredients ")
        onCreate(db)

    }
    private fun fillDatabase(){

    }

    private fun addDish(dish: Dish, numer :Int = 0){
        val values = ContentValues()
        values.put("id", dish.id)
        values.put("title", dish.title)
        values.put("photoLink", dish.photoLink)
        values.put("time", dish.time)
        values.put("addition_time", dish.additionTime)
        values.put("recipe", dish.recipe)
        values.put("calories", dish.calories)
        values.put("p", dish.p)
        values.put("f", dish.f)
        values.put("c", dish.c)
        values.put("numer", numer)
        val db = this.writableDatabase
        db.insert("dishes", null, values)
        db.close()
    }
    public fun addAllDishes(dishes: List<Dish>){
        var t=0
        dishes.forEach {
            addDish(it, t)
            t++
        }
    }
    private fun addIngredient(ingredient: Ingredient, dishId :Int = 0){
        val values = ContentValues()
        values.put("id", ingredient.id)
        values.put("name", ingredient.name)
        values.put("calories", ingredient.calories)
        values.put("p", ingredient.p)
        values.put("f", ingredient.f)
        values.put("c", ingredient.c)
        values.put("dish_id", dishId)
        val db = this.writableDatabase
        db.insert("ingredients", null, values)
        db.close()
    }
    public fun addAllIngredient(ingredients: List<Ingredient>, dishId :Int = 0){
        ingredients.forEach {
            addIngredient(it, dishId)
        }
    }
    public fun getDishesForToday(): MutableList<Dish> {
        val db = this.readableDatabase
        val ans = mutableListOf<Dish>()
        val result = db.rawQuery("SELECT * from dishes where id in (select id from dishes order by timestamp desc limit 3) ", null)
        if( result.moveToFirst()){
            val r=result.columnNames
             ans+= mutableListOf( Dish(result.getInt(result.getColumnIndexOrThrow(r[0])),
                result.getString(result.getColumnIndexOrThrow(r[1])),
                result.getString(result.getColumnIndexOrThrow(r[2])),
                result.getString(result.getColumnIndexOrThrow(r[3])),
                result.getString(result.getColumnIndexOrThrow(r[4])),
                result.getString(result.getColumnIndexOrThrow(r[5])),
                result.getFloat(result.getColumnIndexOrThrow(r[6])),
                result.getFloat(result.getColumnIndexOrThrow(r[7])),
                result.getFloat(result.getColumnIndexOrThrow(r[8])),
                result.getFloat(result.getColumnIndexOrThrow(r[9]))))
        }
        db.close()
        return ans
    }
    public fun getIngredientsOfDish(id: Int): List<Ingredient>{
        val db = this.readableDatabase
        val ans = mutableListOf<Ingredient>()
        val result = db.rawQuery("SELECT * from dishes where id in (select id from dishes order by timestamp desc limit 3) ", null)
        if( result.moveToFirst()){
            val r=result.columnNames
            ans+= mutableListOf( Ingredient(
                result.getInt(result.getColumnIndexOrThrow(r[0])),
                result.getString(result.getColumnIndexOrThrow(r[1])),
                result.getFloat(result.getColumnIndexOrThrow(r[2])),
                result.getFloat(result.getColumnIndexOrThrow(r[3])),
                result.getFloat(result.getColumnIndexOrThrow(r[4])),
                result.getFloat(result.getColumnIndexOrThrow(r[5])),
                result.getFloat(result.getColumnIndexOrThrow(r[6]))))
        }
        //FIXME добавить сбор остальных рецептов и ингридиентов
        db.close()
        return ans
    }


}