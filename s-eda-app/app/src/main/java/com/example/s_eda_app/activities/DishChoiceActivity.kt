package com.example.s_eda_app.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.s_eda_app.adapters.DishesAdapter
import com.example.s_eda_app.R
import com.example.s_eda_app.Tools
import com.example.s_eda_app.db.DBHelper
import com.example.s_eda_app.volley.VolleySingleton
import com.example.s_eda_app.entity.Dish
import com.example.s_eda_app.volley.Requests
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
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
    lateinit var progressBarBreakfast: ProgressBar
    lateinit var progressBarLunch: ProgressBar
    lateinit var progressBarDinner: ProgressBar
    private val tools= Tools()
    private val requests = Requests(this)
    var counter=0
    private var breakfastDish:Dish?=null
    private var dinnerDish:Dish?=null
    private var lunchDish:Dish?=null
    private val myCoroutineScope=CoroutineScope(Dispatchers.IO)
    private val myCoroutineScope1=CoroutineScope(Dispatchers.Main )
    override fun onCreate(savedInstanceState: Bundle?) {
        var job:Job
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val db=DBHelper(applicationContext,null)
        setContentView(R.layout.activity_dish_choice)
        breakfastList = findViewById(R.id.breakfast_view)
        lunchList= findViewById(R.id.lunch_view)
        dinnerList= findViewById(R.id.dinner_view)
        progressBarBreakfast=findViewById(R.id.progress_bar_breakfast)
        progressBarDinner=findViewById(R.id.progress_bar_dinner)
        progressBarLunch=findViewById(R.id.progress_bar_lunch)
        Handler().postDelayed(object : Runnable {
            override fun run() {
                if (counter <= 100) {
                    progressBarBreakfast.progress = counter
                    progressBarDinner.progress = counter
                    progressBarLunch.progress = counter
                    counter++
                    Handler().postDelayed(this, 60)
                } else {
                    Handler().removeCallbacks(this)
                }
            }
        }, 50)
        val onGetMenuResponse:(String)-> Unit={ response ->
            try {
                val s=tools.fixEncoding(response)
                val obj = JSONObject(s!!)

                val breakfastMenu= tools.fillTheMenu(obj.getJSONArray("breakfast"))
                val dinnerMenu = tools.fillTheMenu(obj.getJSONArray("dinner"))
                val lunchMenu= tools.fillTheMenu(obj.getJSONArray("lunch"))
                breakfastList.layoutManager= LinearLayoutManager(this,RecyclerView.HORIZONTAL,false)
                breakfastAdapter= DishesAdapter(breakfastMenu,this)
                breakfastList.adapter=breakfastAdapter
                dinnerList.layoutManager= LinearLayoutManager(this,RecyclerView.HORIZONTAL,false)
                dinnerAdapter= DishesAdapter(dinnerMenu,this)
                dinnerList.adapter=dinnerAdapter
                lunchList.layoutManager= LinearLayoutManager(this,RecyclerView.HORIZONTAL,false)
                lunchAdapter= DishesAdapter(lunchMenu,this)
                lunchList.adapter= lunchAdapter
                progressBarBreakfast.visibility = View.GONE
                progressBarDinner.visibility = View.GONE
                progressBarLunch.visibility = View.GONE
                breakfastList.visibility= View.VISIBLE
                dinnerList.visibility= View.VISIBLE
                lunchList.visibility= View.VISIBLE
                breakfastAdapter.onItemClick={ onDishChoose(it, breakfastList, R.id.card_breakfast) }
                dinnerAdapter.onItemClick={ onDishChoose(it, dinnerList, R.id.card_dinner) }
                lunchAdapter.onItemClick={ onDishChoose(it, lunchList, R.id.card_lunch) }
                job= myCoroutineScope.launch{
                    val allDishes: MutableList<Dish> = mutableListOf<Dish>()
                    allDishes+=breakfastMenu+dinnerMenu+lunchMenu
                    for (dish in allDishes){
                        val onGetIngredientsResponse:(String)-> Unit={response->
                            val responseString=tools.fixEncoding(response)
                            val ingrs = tools.fillTheIngrs(JSONArray(responseString!!))
                            db.addAllIngredient(ingrs,dish.id)
                        }
                        VolleySingleton.getInstance(applicationContext).addToRequestQueue(requests.getIngredientsOfDish(dish.id,onGetIngredientsResponse))
                    }
                }
                breakfastAdapter.onImageClick={ onRecipeView(it, job) }
                lunchAdapter.onImageClick={onRecipeView(it, job)}
                dinnerAdapter.onImageClick={onRecipeView(it, job)}

            } catch(e: JSONException) {
                e.printStackTrace()
            }
        }

        val stringRequest = requests.getTodayMenu(onGetMenuResponse)
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest)
        confirmButton=findViewById(R.id.confirm_menu_button)
        confirmButton.setOnClickListener {
            if((lunchDish==null)||(dinnerDish==null)||(breakfastDish==null)){
                Toast.makeText(applicationContext, "Выберите все блюда", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            db.addAllDishes(listOf(breakfastDish!!,lunchDish!!,dinnerDish!!))
            db.setIngredients(breakfastDish!!.id)
            db.setIngredients(lunchDish!!.id)
            db.setIngredients(dinnerDish!!.id)
            finish()
            startActivity(Intent(this,TodayMenuActivity::class.java))
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        myCoroutineScope.cancel()
        myCoroutineScope1.cancel()
    }
    private fun onDishChoose(dish: Dish, list:RecyclerView, id: Int){
        list.visibility= View.GONE
        val cardView=findViewById<CardView>(id)
        cardView.visibility=View.VISIBLE
        cardView.findViewById<TextView>(R.id.card_title_default).text=dish.title
        val finalTime=dish.time+dish.additionTime
        cardView.findViewById<TextView>(R.id.card_subhead_default).text=finalTime
        cardView.findViewById<TextView>(R.id.card_body_default).text=dish.calories.toString()
        cardView.findViewById<ImageView>(R.id.card_header_image_default).setImageBitmap(tools.getBitmapForId(dish.id, this))
        when(id){
            R.id.card_lunch->lunchDish=dish
            R.id.card_breakfast->breakfastDish=dish
            R.id.card_dinner->dinnerDish= dish
        }
        cardView.findViewById<TextView>(R.id.card_button_default).setOnClickListener{
            when(id){
                R.id.card_lunch->lunchDish=null
                R.id.card_breakfast->breakfastDish=null
                R.id.card_dinner->dinnerDish=null
            }
            list.visibility= View.VISIBLE
            cardView.visibility=View.GONE

        }
    }

    private fun onRecipeView(dish: Dish, job: Job){
       /* val intent = Intent(applicationContext, WaitActivity::class.java)
        applicationContext.startActivity(intent)*/
        myCoroutineScope1.launch {
            val intent1 = Intent(applicationContext, RecipeViewActivity::class.java)
            intent1.putExtra("dish", dish)
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            job.join()
            applicationContext.startActivity(intent1)
        }
    }


}
