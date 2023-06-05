package com.example.travelapp

import DataModels.TravelerModel
import ViewModels.DataTransactionViewModel
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.lifecycle.Observer

class TravelerCardAdapter(private val travelers: List<TravelerModel>, private val listener: Listener, private val listener2: Listener2) : RecyclerView.Adapter<TravelerCardAdapter.MyViewHolder>() {

    interface Listener2{
        fun UpdateTraveler(traveler: TravelerModel)
        fun AddChangedTraveler(traveler: TravelerModel, position: Int)
    }

    interface Listener{
        fun DeleteTraveler(position: Int)
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val TravelerName: TextView = view.findViewById(R.id.TravelerName)
        val Age: TextView = view.findViewById(R.id.Age)
        val Gender: TextView = view.findViewById(R.id.Gender)
        val Restrictions: TextView = view.findViewById(R.id.Restrictions)
        val DeleteBtn: ImageView = view.findViewById(R.id.DeleteBtn)
        val TravelerIcon: ImageView = view.findViewById(R.id.TravelerIcon)
        val item: CardView = view.findViewById(R.id.DayItem)
        var viewModel: DataTransactionViewModel = ViewModelProvider(MainActivity_context).get(DataTransactionViewModel::class.java)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.traveler_card_layout, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.TravelerName.text = travelers[position].TravelerName
        holder.Age.text = travelers[position].Age
        holder.Gender.text = travelers[position].Gender
        holder.Restrictions.text = travelers[position].Restrictions

        if(travelers[position].Gender == "Жен."){
            holder.TravelerIcon.setImageResource(R.drawable.female)
        }



        if(!travelers[position].changeable){
            holder.DeleteBtn.visibility = View.GONE
        }
        else {
            holder.item.setOnClickListener() {
                listener2.UpdateTraveler(travelers[position])

                holder.viewModel.setChangedTravelerToAdapter.observe(
                    RegistrationActivity_context,
                    Observer {
                        listener2.AddChangedTraveler(it, position)
                    })
            }

            holder.DeleteBtn.setOnClickListener(){
                listener.DeleteTraveler(holder.adapterPosition)
            }

        }
    }

    override fun getItemCount(): Int {
        return travelers.size
    }
}