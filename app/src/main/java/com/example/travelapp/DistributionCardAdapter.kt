package com.example.travelapp

import DataModels.IngredientModel
import DataModels.MealMenuModel
import DataModels.TravelerModel
import DataModels.Travelers_ingredientsModel
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DistributionCardAdapter (private val travelers: List<TravelerModel>, private val distributions: List<Travelers_ingredientsModel>, private val ingredients: List<IngredientModel>) : RecyclerView.Adapter<DistributionCardAdapter.MyViewHolder>()  {

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val Traveler: TextView = view.findViewById(R.id.Traveler)
        val FoodList : GridLayout = view.findViewById(R.id.FoodList)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.distribution_card_layout, parent, false)
        return MyViewHolder(view)
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.Traveler.text = travelers[position].TravelerName

        val list = mutableListOf<Travelers_ingredientsModel>()
        val list2 : Map<Int, List<Travelers_ingredientsModel>>
        val list3 = mutableListOf<Travelers_ingredientsModel>()

        distributions.forEach {
            if (it.traveler_id == travelers[position].Id){
                list.add(it)
            }
        }

        list2 = list.groupBy { it.ingredient_id }

        for(j in list2){
            var a = 0
            for(k in j.value) {

                a += k.weight_of_food

            }

            list3.add(
                Travelers_ingredientsModel(
                    j.value[0].day,
                    j.value[0].traveler_id,
                    j.key,
                    j.value[0].type_of_meal,
                    a
                )
            )
        }

        list.clear()
        list.addAll(list3)

        for(i in list){

            val ingredient_name = TextView(MainActivity_context)
            val ingredient_weight = TextView(MainActivity_context)

            var params = LinearLayout.LayoutParams((120 * MainActivity_context.resources.displayMetrics.density).toInt(), LinearLayout.LayoutParams.WRAP_CONTENT)
            params.setMargins(0, 0, 10, 4)
            ingredient_name.layoutParams = params
            for(j in ingredients){
                if(j.id == i.ingredient_id){
                    ingredient_name.text = j.name
                }
            }
            ingredient_name.setTextColor(Color.rgb(0, 0, 0))

            params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            params.setMargins(0, 0, 0, 4)
            ingredient_weight.layoutParams = params
            ingredient_weight.text = i.weight_of_food.toString() + " г"
            ingredient_weight.setTextColor(Color.rgb(0, 0, 0))

            holder.FoodList.addView(ingredient_name)
            holder.FoodList.addView(ingredient_weight)

        }

    }

    override fun getItemCount(): Int = travelers.size


}