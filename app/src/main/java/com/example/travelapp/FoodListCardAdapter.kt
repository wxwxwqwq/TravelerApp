package com.example.travelapp

import DataModels.IngredientModel
import DataModels.Travelers_ingredientsModel
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FoodListCardAdapter(private val distributions: List<Travelers_ingredientsModel>, private val ingredients: List<IngredientModel>) : RecyclerView.Adapter<FoodListCardAdapter.MyViewHolder>() {


    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val FoodName: TextView = view.findViewById(R.id.FoodName)
        val Weight: TextView = view.findViewById(R.id.Weight)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.food_card_layout, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        ingredients.forEach {
            if(it.id == distributions[position].ingredient_id){
                holder.FoodName.text = it.name
            }
        }

        holder.Weight.text = distributions[position].weight_of_food.toString()
    }

    override fun getItemCount(): Int = distributions.size


}