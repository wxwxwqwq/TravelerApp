package com.example.travelapp

import DataModels.TravelModel
import Screens.Using.FirstUse.MenuUseViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView


class TravelCardAdapter(private val travels: List<TravelModel>, private val listener: Listener) : RecyclerView.Adapter<TravelCardAdapter.MyViewHolder>() {

    //lateinit var rl: MainMenuViewModel

    interface Listener{
        fun Del(position: Int)
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val TravelTitle: TextView = view.findViewById(R.id.TravelTitle)
        val AmountMan: TextView = view.findViewById(R.id.AmountMan)
        val Days: TextView = view.findViewById(R.id.Days)
        val item: CardView = view.findViewById(R.id.DayItem)
        val DeleteBtn: ImageView = view.findViewById(R.id.DeleteBtn)

        val viewModel = ViewModelProvider(MainActivity_context).get(MenuUseViewModel::class.java)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.travel_card_layout, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.TravelTitle.text = travels[position].Name
        holder.AmountMan.text = travels[position].Travelers.toString()
        holder.Days.text = travels[position].Days.toString()

        holder.item.setOnClickListener(){

            holder.viewModel.SetId(travels[position].Id)

            it.findNavController().navigate(R.id.action_mainMenuFragment_to_usingActivity)
        }

        holder.DeleteBtn.setOnClickListener(){

            listener.Del(holder.adapterPosition)
        }

        //val context = holder.itemView.context
    }

    override fun getItemCount(): Int {
        return travels.size
    }
}
