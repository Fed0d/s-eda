package com.example.s_eda_app.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.s_eda_app.R
import com.example.s_eda_app.entity.Ingredient

class IngredientsAdapter (var ingredients:List<Ingredient>, var context: Context) :
    RecyclerView.Adapter<IngredientsAdapter.IngredientsViewHolder>() {

    class IngredientsViewHolder(view:View):RecyclerView.ViewHolder(view){
        val title: TextView= view.findViewById(R.id.ingredient_name)
        val weight: TextView= view.findViewById(R.id.ingredient_weight)
        val calories: TextView= view.findViewById(R.id.ingredient_calories)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.ingredient_card, parent, false)
        return IngredientsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return ingredients.count()
    }

    override fun onBindViewHolder(holder: IngredientsViewHolder, position: Int) {
        holder.title.text= ingredients[position].name
        holder.weight.text=ingredients[position].weight.toString()
        holder.calories.text= ingredients[position].calories.toString()

    }

}