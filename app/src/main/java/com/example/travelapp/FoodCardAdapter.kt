package com.example.travelapp

import DataModels.FoodModel
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FoodCardAdapter(private val food: List<FoodModel>, private val listener: Listener) : RecyclerView.Adapter<FoodCardAdapter.MyViewHolder>() {

    interface Listener{
        fun DeleteFood(position: Int)
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val FoodName: TextView = view.findViewById(R.id.FoodName)
        val CaloriesAmount: TextView = view.findViewById(R.id.CaloriesAmount)
        val Weight: TextView = view.findViewById(R.id.Weight)
        val DeleteBtn: ImageView = view.findViewById(R.id.DeleteBtn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.food_card_layout, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.FoodName.text = food[position].Name
        holder.CaloriesAmount.text = food[position].Calories.toString()
        holder.Weight.text = food[position].Weight.toString()

        holder.DeleteBtn.setOnClickListener(){
            listener.DeleteFood(holder.adapterPosition)
        }
    }

    override fun getItemCount(): Int {
        return food.size
    }


}