package com.example.s_eda_app.activities

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.s_eda_app.Constants
import com.example.s_eda_app.R
import com.example.s_eda_app.Tools
import com.example.s_eda_app.db.DBHelper
import com.example.s_eda_app.entity.Dish
import com.example.s_eda_app.entity.Ingredient
import java.time.LocalDateTime


class TodayMenuActivity : AppCompatActivity() {
    private val  tools=Tools()
    private lateinit var updateMenu: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_today_menu)
        updateMenu=findViewById(R.id.update_menu_button)
        val now = LocalDateTime.now()
        val db= DBHelper(applicationContext,null)
        val dishes=db.getDishesForToday()
        if(dishes.isEmpty()){
            startActivity(Intent(this, MainActivity::class.java))
        }
        if(dishes[0].day<now.dayOfMonth){
            db.deleteDishes()
            startActivity(Intent(this, MainActivity::class.java))
        }
        if(now.hour> Constants.MENU_UPDATE_TIME){
            updateMenu.visibility= ViewGroup.VISIBLE
        }
        updateMenu.setOnClickListener {
            val ad: AlertDialog.Builder =  AlertDialog.Builder(this)
            ad.setTitle("Unanswered Questions")
            ad.setMessage("You have not answered all the questions.")
            ad.setPositiveButton("OK", //TODO  доделать диалоговое окно обновления меню
                DialogInterface.OnClickListener { dialog, _ -> dialog.dismiss() })
            ad.show()
        }
        dishes.forEach {
            when(it.iter){
                0 -> {
                    fillTheCard(it, R.id.breakfast_dish_menu, db)
                }
                1->{
                    fillTheCard(it, R.id.lunch_dish_menu, db)
                }
                2->{
                    fillTheCard(it, R.id.dinner_dish_menu, db)
                }

            }
        }
    }
    fun fillTheCard(dish:Dish, id:Int, db: DBHelper){
        val linearLayoutView=findViewById<LinearLayout>(id)
        linearLayoutView.findViewById<ImageView>(R.id.dish_menu_image).setImageBitmap(tools.getBitmapForId(dish.id,this))
        linearLayoutView.findViewById<TextView>(R.id.dish_menu_title).text= dish.title
        linearLayoutView.findViewById<TextView>(R.id.dish_menu_subhead).text=StringBuilder(dish.time.toString()).append(dish.additionTime).toString()
        linearLayoutView.findViewById<TextView>(R.id.dish_menu_body).text=dish.calories.toString()

        linearLayoutView.findViewById<ImageView>(R.id.dish_menu_image).setOnClickListener{
            val intent1=Intent(this, RecipeViewActivity::class.java)
            intent1.putExtra("dish", dish)
            startActivity(intent1)
        }

        val ingrs :List<Ingredient> = db.getIngredientsOfDish(dish.id)
        val tblLayout =  linearLayoutView.findViewById<TableLayout>(R.id.dish_menu_ingredient_table)
        var i=1
        for (el: Ingredient in  ingrs){
            val tableRow =  TableRow(this);
            tableRow.setLayoutParams(
                ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));


            tableRow.addView(getNewCellWithParams(el.name!!), 0);
            tableRow.addView(getNewCellWithParams(el.weight.toString(), 40), 1);
            tableRow.addView(getNewCellWithParams(el.calories.toString(), 40), 2);
            tblLayout.addView(tableRow, i);
            i++
        }

    }
    private fun getNewCellWithParams(text:String, width:Int =110):TextView{
        val cellView:TextView = TextView(this);
        cellView.text=text
        cellView.setGravity(Gravity.CENTER)
        cellView.width=width
        cellView.setPadding(3,3,3,3)
        cellView.textSize=20.0f
        return cellView
    }
}

