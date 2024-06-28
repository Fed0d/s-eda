package com.example.s_eda_app.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.example.s_eda_app.adapters.DishesAdapter
import com.example.s_eda_app.R
import com.example.s_eda_app.URLs
import com.example.s_eda_app.singleton.VolleySingleton
import com.example.s_eda_app.entity.Dish
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class DishChoiceActivity : AppCompatActivity() {
    internal lateinit var breakfastList: RecyclerView
    internal lateinit var lunchList: RecyclerView
    internal lateinit var dinnerList: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_dish_choice)

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

                        intent1.putExtra("id" , breakfastMenuRes .toString())
                        intent1.putExtra("plantName" , dinnerMenuRes .toString())
                        intent1.putExtra("diseaseName" , lunchMenuRes .toString())

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
            breakfastList.adapter= DishesAdapter(breakfastMenu,this)
            dinnerList.adapter= DishesAdapter(dinnerMenu,this)
            lunchList.adapter= DishesAdapter(lunchMenu,this)
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
