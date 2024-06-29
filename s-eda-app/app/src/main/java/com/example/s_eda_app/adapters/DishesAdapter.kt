package com.example.s_eda_app.adapters

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.s_eda_app.R
import com.example.s_eda_app.activities.RecipeViewActivity
import com.example.s_eda_app.entity.Dish
import com.example.s_eda_app.singleton.ImageRequestSingleton
import com.example.s_eda_app.singleton.VolleySingleton
import java.io.File
import java.io.FileOutputStream

class DishesAdapter(var dishes:List<Dish>, var context: Context) :
    RecyclerView.Adapter<DishesAdapter.CardViewHolder>() {

    var onItemClick:((Dish)-> Unit)?=null
    class CardViewHolder(view:View):RecyclerView.ViewHolder(view){
        val image: ImageView= view.findViewById(R.id.card_header_image)
        val title: TextView= view.findViewById(R.id.card_title)
        val time: TextView= view.findViewById(R.id.card_subhead)
        val calories: TextView= view.findViewById(R.id.card_body)
        val id: TextView = view.findViewById(R.id.card_id)
        val button: Button= view.findViewById(R.id.card_button)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.dish_card, parent, false)
        return CardViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dishes.count()
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.title.text= dishes[position].title
        holder.time.text= StringBuilder(dishes[position].time).append(dishes[position].additionTime).toString()
        holder.calories.text= dishes[position].calories.toString()
        if(dishes[position].photoLink!=null){
        val request = ImageRequestSingleton.getInstance(dishes[position].photoLink!!) { bitmap ->
            try {
                val fileName = dishes[position].id.toString() + ".jpeg"
                var file = context.getDir("Images", Context.MODE_PRIVATE)
                file = File(file, fileName)
                val out = FileOutputStream(file)
                holder.image.setImageBitmap(bitmap)
                bitmap.compress(Bitmap.CompressFormat.JPEG, 85, out)
                out.flush()
                out.close()
                Log.i("Seiggailion", "Image saved.")
            } catch (e: Exception) {
                Log.i("Seiggailion", "Failed to save image.")
            }

        }
            VolleySingleton.getInstance(context).addToRequestQueue(request)
        }
        val imageId= R.drawable.default_dish
        holder.image.setImageResource(imageId)
        holder.button.setOnClickListener {
            onItemClick?.invoke(dishes[position])
        }

        holder.image.setOnClickListener{
            val intent1 = Intent(context, RecipeViewActivity::class.java)

            intent1.putExtra("id" , dishes[position].id)
            intent1.putExtra("title" , dishes[position].title)
            intent1.putExtra("time" , dishes[position].time)
            intent1.putExtra("additionTime" , dishes[position].additionTime)
            intent1.putExtra("calories" , dishes[position].calories)
            intent1.putExtra("p" , dishes[position].p)
            intent1.putExtra("f" , dishes[position].f)
            intent1.putExtra("c" , dishes[position].c)
            intent1.putExtra("recipe" , dishes[position].recipe)

            context.startActivity(intent1)
        }
    }

}