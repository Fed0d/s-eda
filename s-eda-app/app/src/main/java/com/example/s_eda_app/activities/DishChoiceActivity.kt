package com.example.s_eda_app.activities

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.example.s_eda_app.adapters.DishesAdapter
import com.example.s_eda_app.R
import com.example.s_eda_app.URLs
import com.example.s_eda_app.db.DBHelper
import com.example.s_eda_app.singleton.VolleySingleton
import com.example.s_eda_app.entity.Dish
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class DishChoiceActivity : AppCompatActivity() {
    private lateinit var breakfastList: RecyclerView
    private lateinit var lunchList: RecyclerView
    private lateinit var dinnerList: RecyclerView
    private lateinit var breakfastAdapter: DishesAdapter
    private lateinit var lunchAdapter: DishesAdapter
    private lateinit var dinnerAdapter: DishesAdapter
    private lateinit var confirmButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_dish_choice)
        var breakfastDish:Dish?=null
        var dinnerDish:Dish?=null
        var lunchDish:Dish?=null
        breakfastList = findViewById(R.id.breakfast_view)
        lunchList= findViewById(R.id.lunch_view)
        dinnerList= findViewById(R.id.dinner_view)
        val breakfastMenu : MutableList<Dish>
        val dinnerMenu : MutableList<Dish>
        val lunchMenu: MutableList<Dish>
        if(this.intent.getStringExtra("breakfast") == null
            || this.intent.getStringExtra("dinner") == null
            || this.intent.getStringExtra("lunch") == null){
        val stringRequest = object : StringRequest(Request.Method.POST,
            URLs.URL_TODAY_MENU,
            Response.Listener { response ->
                try {
                    val obj = JSONObject(response)
                    if(!obj.getBoolean("error")) {
                        Toast.makeText(applicationContext, obj.getString("message"), Toast.LENGTH_SHORT).show()
                        val menuJson = obj.getJSONObject("menu")
                        val breakfastMenuRes = menuJson.getJSONArray("breakfast")
                        val dinnerMenuRes  = menuJson.getJSONArray("dinner")
                        val lunchMenuRes  = menuJson.getJSONArray("lunch")
                        val intent1 = Intent(this, DishChoiceActivity::class.java)//FIXME обязательно ли перезапускать активити

                        intent1.putExtra("breakfast" , breakfastMenuRes.toString())
                        intent1.putExtra("dinner" , dinnerMenuRes.toString())
                        intent1.putExtra("lunch" , lunchMenuRes.toString())

                        startActivity(intent1)
                    } else {
                        Toast.makeText(applicationContext, obj.getString("message"), Toast.LENGTH_SHORT).show()
                    }
                } catch(e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { error -> Toast.makeText(applicationContext, error.message, Toast.LENGTH_SHORT).show() }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params["username"] = "user"
                return params
            }
        }
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest)
        } else{
            breakfastMenu= fillTheMenu(JSONArray(this.intent.getStringExtra("breakfast")))
            dinnerMenu = fillTheMenu(JSONArray(this.intent.getStringExtra("dinner")))
            lunchMenu= fillTheMenu(JSONArray(this.intent.getStringExtra("lunch")))
            //breakfastList.layoutManager=LinearLayoutManager(this)
            breakfastAdapter= DishesAdapter(breakfastMenu,this)
            breakfastList.adapter=breakfastAdapter
            dinnerAdapter= DishesAdapter(dinnerMenu,this)
            dinnerList.adapter=dinnerAdapter
            lunchAdapter= DishesAdapter(lunchMenu,this)
            lunchList.adapter= lunchAdapter
            breakfastAdapter.onItemClick={
                breakfastList.visibility= View.GONE
                findViewById<CardView>(R.id.card_breakfast).visibility=View.VISIBLE
                findViewById<TextView>(R.id.card_title_breakfast).text=it.title
                val finalTime=it.time+it.additionTime
                findViewById<TextView>(R.id.card_subhead_breakfast).text=finalTime
                findViewById<TextView>(R.id.card_body_breakfast).text=it.calories.toString()
                try {
                    val fileName = if (it.id == -1) {
                        this.getDir("Images", Context.MODE_PRIVATE).path + "/" + "default_dish.png"
                    } else {
                        this.getDir("Images", Context.MODE_PRIVATE).path + "/" + it.id.toString() + ".jpeg"
                    }
                    val bitmap: Bitmap = BitmapFactory.decodeFile(fileName)
                    findViewById<ImageView>(R.id.card_header_image_breakfast).setImageBitmap(bitmap)
                    Log.i("Seiggailion", "Image loaded.")
                } catch (e: Exception) {
                    Log.i("Seiggailion", "Failed to load image.")
                }
                breakfastDish=it
                findViewById<Button>(R.id.card_button_breakfast).setOnClickListener{
                    breakfastDish=null
                    breakfastList.visibility= View.VISIBLE
                    findViewById<CardView>(R.id.card_breakfast).visibility=View.GONE

                }

            }
            dinnerAdapter.onItemClick={
                dinnerList.visibility= View.GONE
                findViewById<CardView>(R.id.card_dinner).visibility=View.VISIBLE
                findViewById<TextView>(R.id.card_title_dinner).text=it.title
                val finalTime=it.time+it.additionTime
                findViewById<TextView>(R.id.card_subhead_dinner).text=finalTime
                findViewById<TextView>(R.id.card_body_dinner).text=it.calories.toString()
                try {
                    val fileName = if (it.id == -1) {
                        this.getDir("Images", Context.MODE_PRIVATE).path + "/" + "default_dish.png"
                    } else {
                        this.getDir("Images", Context.MODE_PRIVATE).path + "/" + it.id.toString() + ".jpeg"
                    }
                    val bitmap: Bitmap = BitmapFactory.decodeFile(fileName)
                    findViewById<ImageView>(R.id.card_header_image_dinner).setImageBitmap(bitmap)
                    Log.i("Seiggailion", "Image loaded.")
                } catch (e: Exception) {
                    Log.i("Seiggailion", "Failed to load image.")
                }
                dinnerDish=it
                findViewById<Button>(R.id.card_button_dinner).setOnClickListener{
                    dinnerDish=null
                    dinnerList.visibility= View.VISIBLE
                    findViewById<CardView>(R.id.card_dinner).visibility=View.GONE

                }
            }
            lunchAdapter.onItemClick={
                lunchList.visibility= View.GONE
                findViewById<CardView>(R.id.card_lunch).visibility=View.VISIBLE
                findViewById<TextView>(R.id.card_title_lunch).text=it.title
                val finalTime=it.time+it.additionTime
                findViewById<TextView>(R.id.card_subhead_lunch).text=finalTime
                findViewById<TextView>(R.id.card_body_lunch).text=it.calories.toString()
                try {
                    val fileName = if (it.id == -1) {
                        this.getDir("Images", Context.MODE_PRIVATE).path + "/" + "default_dish.png"
                    } else {
                        this.getDir("Images", Context.MODE_PRIVATE).path + "/" + it.id.toString() + ".jpeg"
                    }
                    val bitmap: Bitmap = BitmapFactory.decodeFile(fileName)
                    findViewById<ImageView>(R.id.card_header_image_lunch).setImageBitmap(bitmap)
                    Log.i("Seiggailion", "Image loaded.")
                } catch (e: Exception) {
                    Log.i("Seiggailion", "Failed to load image.")
                }
                lunchDish=it
                findViewById<Button>(R.id.card_button_lunch).setOnClickListener{
                    lunchDish=null
                    lunchList.visibility= View.VISIBLE
                    findViewById<CardView>(R.id.card_lunch).visibility=View.GONE

                }

            }
        }
        confirmButton=findViewById(R.id.confirm_menu_button)
        confirmButton.setOnClickListener {
            if((lunchDish==null)||(dinnerDish==null)||(breakfastDish==null)){
                Toast.makeText(applicationContext, "Выберите все блюда", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val db=DBHelper(applicationContext,null)
            db.addAllDishes(listOf(breakfastDish!!,lunchDish!!,dinnerDish!!))
            //TODO создать страницу просмотра дневного рациона
        }

    }
    fun fillTheMenu(menu: JSONArray) : MutableList<Dish>{
        val ans= mutableListOf<Dish>()
        var i =0
        while (!menu.isNull(i)){
            val dish = Dish(
                menu.getJSONObject(i).getInt("id"),
                menu.getJSONObject(i).getString("title"),
                menu.getJSONObject(i).getString("photoLink"),
                menu.getJSONObject(i).getString("time"),
                menu.getJSONObject(i).getString("additionTime"),
                menu.getJSONObject(i).getString("recipe"),
                menu.getJSONObject(i).getString("calories").toFloat(),
                menu.getJSONObject(i).getString("p").toFloat(),
                menu.getJSONObject(i).getString("f").toFloat(),
                menu.getJSONObject(i).getString("c").toFloat())
            i+=1
            ans+= mutableListOf(dish)
        }
        return ans

    }

}
