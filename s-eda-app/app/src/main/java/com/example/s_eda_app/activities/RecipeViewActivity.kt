package com.example.s_eda_app.activities

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.example.s_eda_app.adapters.IngredientsAdapter
import com.example.s_eda_app.R
import com.example.s_eda_app.URLs
import com.example.s_eda_app.singleton.VolleySingleton
import com.example.s_eda_app.entity.Ingredient
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class RecipeViewActivity : AppCompatActivity() {
    internal lateinit var titleField: EditText
    internal lateinit var timeField: EditText
    internal lateinit var backButton: Button
    internal lateinit var calories: TextView
    internal lateinit var p: TextView
    internal lateinit var f: TextView
    internal lateinit var c: TextView
    internal lateinit var recipe: TextView
    internal lateinit var img: ImageView
    internal lateinit var ingredientsList: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_recipe_view)
        titleField = findViewById(R.id.card_title_big)
        timeField = findViewById(R.id.card_subhead_big)
        backButton = findViewById(R.id.back_button_card)
        calories = findViewById(R.id.calories_card_big)
        p = findViewById(R.id.p_card_big)
        f = findViewById(R.id.f_card_big)
        c = findViewById(R.id.c_card_big)
        recipe = findViewById(R.id.card_recipe_big)
        img = findViewById(R.id.card_header_image_big)
        ingredientsList= findViewById(R.id.card_ingredient_view)
        titleField.setText(this.intent.getStringExtra("title"))
        timeField.setText(
            StringBuilder(this.intent.getStringExtra("time")!!).append(
                this.intent.getStringExtra(
                    "additionTime"
                )
            ).toString()
        )
        calories.text = this.intent.getFloatExtra("calories", 0.0f).toString()
        p.text = this.intent.getFloatExtra("p", 0.0f).toString()
        f.text = this.intent.getFloatExtra("f", 0.0f).toString()
        c.text = this.intent.getFloatExtra("c", 0.0f).toString()
        recipe.text = this.intent.getStringExtra("recipe")
        val id = this.intent.getIntExtra("id", -1)


        try {
            val fileName = if (id == -1) {
                this.getDir("Images", Context.MODE_PRIVATE).path + "/" + "default_dish.png"
            } else {
                this.getDir("Images", Context.MODE_PRIVATE).path + "/" + id.toString() + ".jpeg"
            }
            val bitmap: Bitmap = BitmapFactory.decodeFile(fileName)
            img.setImageBitmap(bitmap)
            Log.i("Seiggailion", "Image loaded.")
        } catch (e: Exception) {
            Log.i("Seiggailion", "Failed to load image.")
        }
        backButton.setOnClickListener {
            finish() //FIXME будет ли оно возварщать к прошлому запросу?
        }
        if (id != -1) {
            val stringRequest = object : StringRequest(
                Request.Method.GET, URLs.URL_INGREDIENTS_OF_DISH + id.toString(),
                Response.Listener { response ->
                    try {
                        val obj = JSONObject(response)
                        if (!obj.getBoolean("error")) {
                            Toast.makeText(
                                applicationContext,
                                obj.getString("message"),
                                Toast.LENGTH_SHORT
                            ).show()
                            val ingrs = fillTheIngrs(obj.getJSONArray("ingredients"))
                            ingredientsList.adapter=
                                IngredientsAdapter(ingrs,this)//FIXME будет ли работать без перезапуска

                        } else {
                            Toast.makeText(
                                applicationContext,
                                obj.getString("message"),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { error ->
                    Toast.makeText(
                        applicationContext,
                        error.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }) {
            }
            VolleySingleton.getInstance(this).addToRequestQueue(stringRequest)
        }
    }
    fun fillTheIngrs(ingrs: JSONArray): MutableList<Ingredient> {
        val ans = mutableListOf<Ingredient>()
        var i = 0
        while (!ingrs.isNull(i)) {
            val ingr = Ingredient(
                ingrs.getJSONObject(i).getInt("id"),
                ingrs.getJSONObject(i).getString("name"),
                ingrs.getJSONObject(i).getString("weight").toFloat(),
                ingrs.getJSONObject(i).getString("calories").toFloat(),
                ingrs.getJSONObject(i).getString("p").toFloat(),
                ingrs.getJSONObject(i).getString("f").toFloat(),
                ingrs.getJSONObject(i).getString("c").toFloat()
            )
            i += 1
            ans += mutableListOf(ingr)
        }
        return ans

    }
}