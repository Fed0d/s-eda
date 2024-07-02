package com.example.s_eda_app.activities

import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.s_eda_app.R
import com.example.s_eda_app.Tools
import com.example.s_eda_app.db.DBHelper
import com.example.s_eda_app.entity.Dish
import com.example.s_eda_app.entity.Ingredient
import com.example.s_eda_app.volley.Requests

class RecipeViewActivity : AppCompatActivity() {
    internal lateinit var titleField: TextView
    internal lateinit var timeField: TextView
    internal lateinit var backButton: ImageButton
    internal lateinit var calories: TextView
    internal lateinit var p: TextView
    internal lateinit var f: TextView
    internal lateinit var c: TextView
    internal lateinit var recipe: TextView
    internal lateinit var img: ImageView
    internal lateinit var tblLayout: TableLayout
    private val tools = Tools()
    private val requests= Requests(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val db= DBHelper(applicationContext,null)
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
        var dish:Dish?
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            dish=this.intent.getParcelableExtra("dish", Dish::class.java)
        } else{
            dish=intent.getParcelableExtra<Dish>("dish")
        }
        titleField.text = dish!!.title
        timeField.text = StringBuilder(dish.time!!).append(dish.additionTime).toString()
        calories.text = dish.calories.toString()
        p.text = dish.p.toString()
        f.text = dish.f.toString()
        c.text = dish.c.toString()
        recipe.text = dish.recipe
        val id = dish.id

        img.setImageBitmap(tools.getBitmapForId(id,this))

        backButton.setOnClickListener {
            finish()
        }
        if ((id != -1)) {
            tblLayout = findViewById(R.id.ingredient_table);
            val ingrs =db.getIngredientsOfDish(id)
            var i=1
            for (el:Ingredient in  ingrs){
                val tableRow =  TableRow(this);
                tableRow.setLayoutParams(ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));


                tableRow.addView(getNewCellWithParams(el.name!!), 0);
                tableRow.addView(getNewCellWithParams(el.weight.toString(), 40), 1);
                tableRow.addView(getNewCellWithParams(el.calories.toString(), 40), 2);
                tblLayout.addView(tableRow, i);
                i++
            }
        }
    }
    fun getNewCellWithParams(text:String, width:Int =110):TextView{
        val cellView:TextView = TextView(this);
        cellView.text=text
        cellView.setGravity(Gravity.CENTER)
        cellView.width=width
        cellView.setPadding(3,3,3,3)
        cellView.textSize=20.0f
        return cellView
    }

}