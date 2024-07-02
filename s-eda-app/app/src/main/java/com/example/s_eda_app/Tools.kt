package com.example.s_eda_app

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import com.example.s_eda_app.entity.Dish
import com.example.s_eda_app.entity.Ingredient
import org.json.JSONArray
import java.io.UnsupportedEncodingException




class Tools() {
    fun fixEncoding(response: String): String? {
        var res:String
        try {
            val u = response.toString().toByteArray(
                charset(
                    "ISO-8859-1"
                )
            )
            res=String(u, charset("UTF-8"))
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
            return null
        }
        return res
    }
    fun fillTheMenu(menu: JSONArray) : MutableList<Dish>{
        val ans= mutableListOf<Dish>()
        var i =0
        while (!menu.isNull(i)){
            val dish = Dish(
                menu.getJSONObject(i).getInt("id"),
                menu.getJSONObject(i).getString("name"),
                menu.getJSONObject(i).getString("photoLink"),
                menu.getJSONObject(i).getString("cookingTime"),
                menu.getJSONObject(i).getString("additionalTime").drop(1),
                menu.getJSONObject(i).getString("description"),
                menu.getJSONObject(i).getString("calories").toFloat(),
                menu.getJSONObject(i).getString("proteins").toFloat(),
                menu.getJSONObject(i).getString("fats").toFloat(),
                menu.getJSONObject(i).getString("carbohydrates").toFloat())
            i+=1
            ans+= mutableListOf(dish)
        }
        return ans

    }
    fun getBitmapForId(id:Int, context: Context): Bitmap?{
        try {
            val fileName = if (id == -1) {
                context.getDir("Images", Context.MODE_PRIVATE).path + "/" + "default_dish.png"
            } else {
                context.getDir("Images", Context.MODE_PRIVATE).path + "/" + id.toString() + ".jpeg"
            }
            val bitmap: Bitmap = BitmapFactory.decodeFile(fileName)
            Log.i("Seiggailion", "Image loaded.")
            return bitmap
        } catch (e: Exception) {
            Log.i("Seiggailion", "Failed to load image.")
            return null
        }

    }
    fun deleteImage(id:Int, context: Context){
        try {
            val fileName = context.getDir("Images", Context.MODE_PRIVATE).path + "/" + id.toString() + ".jpeg"
            context.deleteFile(fileName)
            Log.i("Seiggailion", "Image loaded.")
        } catch (e: Exception) {
            Log.i("Seiggailion", "Failed to load image.")
        }
    }
    fun fillTheIngrs(ingrs: JSONArray): MutableList<Ingredient> {
        val ans = mutableListOf<Ingredient>()
        var i = 0
        while (!ingrs.isNull(i)) {
            val now=ingrs.getJSONObject(i).getJSONObject("ingredient")
            val weight =ingrs.getJSONObject(i).getString("weight").toFloat()
            val ingr = Ingredient(
                now.getInt("id"),
                now.getString("name"),
                weight,
                now.getString("calories").toFloat(),
                now.getString("proteins").toFloat(),
                now.getString("fats").toFloat(),
                now.getString("carbohydrates").toFloat()
            )
            i += 1
            ans += mutableListOf(ingr)
        }
        return ans

    }
}