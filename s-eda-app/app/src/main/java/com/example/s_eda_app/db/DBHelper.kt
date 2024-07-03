package com.example.s_eda_app.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import com.example.s_eda_app.Constants
import com.example.s_eda_app.Tools
import com.example.s_eda_app.entity.Dish
import com.example.s_eda_app.entity.Ingredient
import com.example.s_eda_app.volley.Requests
import com.example.s_eda_app.volley.VolleySingleton
import org.json.JSONException
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.Date
import java.util.Locale


class DBHelper(private val context: Context, private val factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DB_NAME, factory, 1){
    private var isFilled=false
    private val tools=Tools()
    private val requests=Requests(context)
    override fun onCreate(db: SQLiteDatabase?) {
        val query =
            "CREATE TABLE $DISHES_TABLE_NAME ($DISHES_ID_COL  INT PRIMARY KEY, $DISHES_TITLE_COl TEXT, $DISHES_PHOTO_COl TEXT, $DISHES_TIME_COl TEXT, $DISHES_ADDITIONAL_TIME_COl TEXT, $DISHES_RECIPE_COl TEXT, $DISHES_CALORIES_COl REAL, $DISHES_P_COl REAL, $DISHES_F_COl REAL, $DISHES_C_COl REAL, $DISHES_NUMER_COl INT, $DISHES_DAY_COl INT )"
        val query1=
            "CREATE TABLE $INGREDIENTS_TABLE_NAME ($INGREDIENTS_ID_COL INT PRIMARY KEY, $INGREDIENTS_TITLE_COl TEXT, $INGREDIENTS_WEIGHT_COl REAL, $INGREDIENTS_CALORIES_COl REAL, $INGREDIENTS_P_COl REAL, $INGREDIENTS_F_COl REAL, $INGREDIENTS_C_COl REAL, $INGREDIENTS_DISH_ID_COl INT, FOREIGN KEY($INGREDIENTS_DISH_ID_COl) REFERENCES $DISHES_TABLE_NAME($DISHES_ID_COL))"
        db!!.execSQL(query)
        db.execSQL(query1)
    }
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS dishes")
        db.execSQL("DROP TABLE IF EXISTS ingredients ")
        onCreate(db)

    }

    private fun addDish(dish: Dish, numer :Int = 0){
        val values = ContentValues()
        values.put(DISHES_ID_COL, dish.id)
        values.put(DISHES_TITLE_COl, dish.title)
        values.put(DISHES_PHOTO_COl, dish.photoLink)
        values.put(DISHES_TIME_COl, dish.time)
        values.put(DISHES_ADDITIONAL_TIME_COl, dish.additionTime)
        values.put(DISHES_RECIPE_COl, dish.recipe)
        values.put(DISHES_CALORIES_COl, dish.calories)
        values.put(DISHES_P_COl, dish.p)
        values.put(DISHES_F_COl, dish.f)
        values.put(DISHES_C_COl, dish.c)
        values.put(DISHES_NUMER_COl, numer)
        val today = getDay()
        values.put(DISHES_DAY_COl, today)
        val db = this.writableDatabase
        try{
            db.insert(DISHES_TABLE_NAME, null, values)
        } catch (e: Exception){
            val id=dish.id
            db.rawQuery("UPDATE $DISHES_TABLE_NAME SET $DISHES_DAY_COl='$today' WHERE $DISHES_ID_COL='$id'",null).close()
        }
        db.close()
    }
    public fun addAllDishes(dishes: List<Dish>){
        var t=0
        dishes.forEach {
            addDish(it, t)
            t++
        }
    }
    fun setIngredients(dishId :Int){
        if (dishId != -1) {
            val onGetMenuResponse:(String)-> Unit={ response ->
                try {
                    val s=tools.fixEncoding(response)
                    val obj = JSONObject(s!!)
                    if (!obj.getBoolean("error")) {
                        Toast.makeText(
                            context,
                            obj.getString("message"),
                            Toast.LENGTH_SHORT
                        ).show()
                        val ingrs = tools.fillTheIngrs(obj.getJSONArray("ingredients"))
                        addAllIngredient(ingrs, dishId)

                    } else {
                        Toast.makeText(
                            context,
                            obj.getString("message"),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
            val stringRequest = requests.getIngredientsOfDish(dishId, onGetMenuResponse)
            VolleySingleton.getInstance(context).addToRequestQueue(stringRequest)
        }
    }
    public fun deleteDishes(){
        val db=this.writableDatabase//FIXME сделать так чтобю это функция очищала все таблицы
        val today = getDay()
        val result = db.rawQuery("SELECT $DISHES_ID_COL FROM $DISHES_TABLE_NAME WHERE $DISHES_DAY_COl <'$today'", null)
        val ids = mutableListOf<Int>()
        if(result.moveToFirst()){
            ids+=result.getInt(result.getColumnIndexOrThrow(DISHES_ID_COL))
        }
        while(result.moveToNext()){
            ids+=result.getInt(result.getColumnIndexOrThrow(DISHES_ID_COL))
        }
        for (id in ids) {
            db.rawQuery("DELETE FROM $INGREDIENTS_TABLE_NAME WHERE $INGREDIENTS_DISH_ID_COl='$id'", null).close()
            db.rawQuery("DELETE FROM $DISHES_TABLE_NAME WHERE $DISHES_ID_COL='$id'", null).close()

            tools.deleteImage(id, context)
        }

        result.close()
        db.close()
    }
    private fun addIngredient(ingredient: Ingredient, dishId :Int = 0){
        val values = ContentValues()
        values.put(INGREDIENTS_TITLE_COl, ingredient.name)
        values.put(INGREDIENTS_CALORIES_COl, ingredient.calories)
        values.put(INGREDIENTS_P_COl, ingredient.p)
        values.put(INGREDIENTS_F_COl, ingredient.f)
        values.put(INGREDIENTS_C_COl, ingredient.c)
        values.put(INGREDIENTS_WEIGHT_COl, ingredient.weight)
        values.put(INGREDIENTS_DISH_ID_COl, dishId)
        val db = this.writableDatabase
        db.insert(INGREDIENTS_TABLE_NAME, null, values)
        db.close()
    }
    public fun addAllIngredient(ingredients: List<Ingredient>, dishId :Int = 0){
        ingredients.forEach {
            addIngredient(it, dishId)
        }
    }
    public fun getDishesForToday(): List<Dish> {
        val db = this.readableDatabase
        val ans = mutableListOf<Dish>()
        val result = db.rawQuery("SELECT * from $DISHES_TABLE_NAME where $DISHES_ID_COL in (select $DISHES_ID_COL  from $DISHES_TABLE_NAME order by $DISHES_DAY_COl desc limit 3) order by $DISHES_NUMER_COl ", null)
        if( result.moveToFirst()){
            ans+= nextDish(result)
        }
        while(result.moveToNext()){
            ans+= nextDish(result)
        }
        result.close()
        db.close()
        return ans
    }
    private fun nextDish(result: Cursor): MutableList<Dish>{
    return mutableListOf( Dish(
        result.getInt(result.getColumnIndexOrThrow(DISHES_ID_COL)),
        result.getString(result.getColumnIndexOrThrow(DISHES_TITLE_COl)),
        result.getString(result.getColumnIndexOrThrow(DISHES_PHOTO_COl)),
        result.getString(result.getColumnIndexOrThrow(DISHES_TIME_COl)),
        result.getString(result.getColumnIndexOrThrow(DISHES_ADDITIONAL_TIME_COl)),
        result.getString(result.getColumnIndexOrThrow(DISHES_RECIPE_COl)),
        result.getFloat(result.getColumnIndexOrThrow(DISHES_CALORIES_COl)),
        result.getFloat(result.getColumnIndexOrThrow(DISHES_P_COl)),
        result.getFloat(result.getColumnIndexOrThrow(DISHES_F_COl)),
        result.getFloat(result.getColumnIndexOrThrow(DISHES_C_COl)),
        result.getInt(result.getColumnIndexOrThrow(DISHES_NUMER_COl)),
        result.getInt(result.getColumnIndexOrThrow(DISHES_DAY_COl))))
    }
    public fun getIngredientsOfDish(id: Int): List<Ingredient>{
        val db = this.readableDatabase
        val ans = mutableListOf<Ingredient>()
        val result = db.rawQuery("SELECT * from $INGREDIENTS_TABLE_NAME where $INGREDIENTS_DISH_ID_COl='$id' ", null)
        if( result.moveToFirst()){
            ans+= nextIngredient(result)
        }
        while(result.moveToNext()){
            ans+= nextIngredient(result)
        }
        result.close()
        db.close()
        return ans
    }
    private fun nextIngredient(result: Cursor):MutableList<Ingredient>{
        return mutableListOf( Ingredient(
            result.getInt(result.getColumnIndexOrThrow(INGREDIENTS_ID_COL)),
            result.getString(result.getColumnIndexOrThrow(INGREDIENTS_TITLE_COl)),
            result.getFloat(result.getColumnIndexOrThrow(INGREDIENTS_WEIGHT_COl)),
            result.getFloat(result.getColumnIndexOrThrow(INGREDIENTS_CALORIES_COl)),
            result.getFloat(result.getColumnIndexOrThrow(INGREDIENTS_P_COl)),
            result.getFloat(result.getColumnIndexOrThrow(INGREDIENTS_F_COl)),
            result.getFloat(result.getColumnIndexOrThrow(INGREDIENTS_C_COl))))
    }
    private fun getDay():Int{
        val now= LocalDateTime.now()
        return if(now.hour>=Constants.MENU_UPDATE_TIME){
            now.plusDays(1).dayOfMonth
        }else{
            now.dayOfMonth
        }
    }

    companion object{
        val DB_NAME="dishes_db"

        val DISHES_TABLE_NAME = "dishes"

        val DISHES_ID_COL = "id"
        val DISHES_TITLE_COl = "title"
        val DISHES_PHOTO_COl = "photoLink"
        val DISHES_TIME_COl = "time"
        val DISHES_ADDITIONAL_TIME_COl = "addition_time"
        val DISHES_RECIPE_COl = "recipe"
        val DISHES_CALORIES_COl = "calories"
        val DISHES_P_COl = "p"
        val DISHES_F_COl = "f"
        val DISHES_C_COl = "c"
        val DISHES_NUMER_COl = "numer"
        val DISHES_DAY_COl = "day"


        val INGREDIENTS_TABLE_NAME = "ingredients"

        val INGREDIENTS_ID_COL = "id"
        val INGREDIENTS_TITLE_COl = "name"
        val INGREDIENTS_WEIGHT_COl = "weight"
        val INGREDIENTS_CALORIES_COl = "calories"
        val INGREDIENTS_P_COl = "p"
        val INGREDIENTS_C_COl = "c"
        val INGREDIENTS_F_COl = "f"
        val INGREDIENTS_DISH_ID_COl = "dish_id"

    }

}