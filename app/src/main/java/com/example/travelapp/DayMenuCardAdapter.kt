package com.example.travelapp

import DataModels.DayMenuModel
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class DayMenuCardAdapter(private val days: List<DayMenuModel>, private val listener: MealMenuCardAdapter.Listener, val TravelersAmount : Int)  : RecyclerView.Adapter<DayMenuCardAdapter.MyViewHolder>() {


    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val DayTitle: TextView = view.findViewById(R.id.DayTitle)
        val recyclerView: RecyclerView = view.findViewById(R.id.ListOfMeals)
        val expandableLayout: androidx.constraintlayout.widget.ConstraintLayout = view.findViewById(R.id.expandableLayout1)
        val DayItem: CardView = view.findViewById(R.id.DayItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.daymenu_card_layout, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.recyclerView.adapter = MealMenuCardAdapter(days[position].listOfMeals, listener, TravelersAmount)

        holder.DayTitle.text = "День " + days[position].day_number

        if(days[position].expanded){
            holder.expandableLayout.visibility = View.VISIBLE
        }
        else{
            holder.expandableLayout.visibility = View.GONE
        }

        holder.DayItem.setOnClickListener(){
            days[position].expanded = !days[position].expanded

            if(days[position].expanded){
                holder.expandableLayout.visibility = View.VISIBLE
            }
            else{
                holder.expandableLayout.visibility = View.GONE
            }
        }
    }

    override fun getItemCount(): Int {
        return days.size
    }


}