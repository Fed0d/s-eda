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
import com.example.s_eda_app.volley.Requests
import com.example.s_eda_app.volley.VolleySingleton
import java.io.File
import java.io.FileOutputStream

class DishesAdapter(var dishes:List<Dish>, var context: Context) :
    RecyclerView.Adapter<DishesAdapter.CardViewHolder>() {

    var onItemClick:((Dish)-> Unit)?=null
    var onImageClick:((Dish)-> Unit)?=null
    val requests = Requests(context)
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
        holder.time.text=
            dishes[position].time?.let { StringBuilder(it).append(dishes[position].additionTime).toString() }
        holder.calories.text= StringBuilder(dishes[position].calories.toString()).append(" ккал").toString()
        if(dishes[position].photoLink!=null){
            val onImageResponse:(Bitmap)-> Unit= { it -> try {
                val fileName = dishes[position].id.toString() + ".jpeg"
                var file = context.getDir("Images", Context.MODE_PRIVATE)
                file = File(file, fileName)
                val out = FileOutputStream(file)
                holder.image.setImageBitmap(it)
                it.compress(Bitmap.CompressFormat.JPEG, 85, out)
                out.flush()
                out.close()
                Log.i("Seiggailion", "Image saved.")
            } catch (e: Exception) {
                Log.i("Seiggailion", "Failed to save image.")
            } }
            val request = requests.getImagesRequest(dishes[position].photoLink!!, onImageResponse)
            VolleySingleton.getInstance(context).addToRequestQueue(request)
        }
        val imageId= R.drawable.default_dish
        holder.image.setImageResource(imageId)
        holder.button.setOnClickListener {
            onItemClick?.invoke(dishes[position])
        }

        holder.image.setOnClickListener{
            onImageClick?.invoke(dishes[position])

        }
    }

}