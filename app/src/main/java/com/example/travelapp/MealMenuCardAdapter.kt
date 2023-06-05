package com.example.travelapp

import DataModels.DishModel
import DataModels.MealMenuModel
import Screens.Using.FirstUse.MenuUseViewModel
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
//import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.RecyclerView

class MealMenuCardAdapter(private val meals: List<MealMenuModel>, private val listener: Listener, val TravelersAmount : Int) : RecyclerView.Adapter<MealMenuCardAdapter.MyViewHolder>() {

    interface Listener{
        fun Open(day: Int, type: String)
        fun AddDish(dish: DishModel, day: Int, type: String)
    }


    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        //val CreateTravelBtn: Button = RegistrationActivity_context.findViewById(R.id.CreateTravelBtn)

        val MealTitle: TextView = view.findViewById(R.id.MealTitle)
        val DishContainer : LinearLayout = view.findViewById(R.id.DishContainer)
        val IngredientContainer : LinearLayout = view.findViewById(R.id.FoodContainer)
        val dish_name: TextView = view.findViewById(R.id.dish_name)
        val portion_weight: TextView = view.findViewById(R.id.portion_weight)
        val portion_calories: TextView = view.findViewById(R.id.portion_calories)
        val IngredientsList : GridLayout = view.findViewById(R.id.IngredientList)
        val Travelers_ingredients : GridLayout = view.findViewById(R.id.Travelers_ingredients)
        val expandableLayout: androidx.constraintlayout.widget.ConstraintLayout = view.findViewById(R.id.expandableLayout2)
        val MealItem: CardView = view.findViewById(R.id.MealItem)
        val TravelersContainer: LinearLayout = view.findViewById(R.id.TravelersContainer)
        val CreateMealBtn: ImageView = view.findViewById(R.id.CreateMealBtn)

        val viewModel = ViewModelProvider(MainActivity_context).get(MenuUseViewModel::class.java)

        //val DataTransactionviewModel = ViewModelProvider(RegistrationActivity_context).get(DataTransactionViewModel::class.java)


        lateinit var bundle: Bundle
        //val list = mutableListOf<MealMenuModel>()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.mealmenu_card_layout, parent, false)
        return MyViewHolder(view)
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.MealTitle.text = meals[position].type_of_meal

        //Отображение раздела с туристами, транспортирующими продукты
        if(meals[position].show_travelers){
            holder.TravelersContainer.visibility = View.VISIBLE

            val (listOfDistribution, listOfTravelers, listOfIngredients) = holder.viewModel.GetTravelersIngredientsData(meals[position].dayNum, meals[position].type_of_meal)

            var counter = 0
            listOfTravelers.forEach {

                val traveler_name = TextView(MainActivity_context)

                traveler_name.text = it.TravelerName + ":"
                traveler_name.setTextColor(Color.rgb(0, 0, 0))
                val gridParams = GridLayout.LayoutParams()
                gridParams.width = GridLayout.LayoutParams.WRAP_CONTENT
                gridParams.height = GridLayout.LayoutParams.WRAP_CONTENT
                gridParams.rightMargin = 15
                gridParams.rowSpec = GridLayout.spec(counter,it.amount)

                counter++

                holder.Travelers_ingredients.addView(traveler_name, gridParams)

                for(i in listOfDistribution){

                    if(i.traveler_id == it.Id){

                        val ingredient_name = TextView(MainActivity_context)
                        val ingredient_weight = TextView(MainActivity_context)

                        var params = LinearLayout.LayoutParams((120 * MainActivity_context.resources.displayMetrics.density).toInt(), LinearLayout.LayoutParams.WRAP_CONTENT)
                        params.setMargins(0, 0, 10, 4)
                        ingredient_name.layoutParams = params
                        for(j in listOfIngredients){
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

                        holder.Travelers_ingredients.addView(ingredient_name)
                        holder.Travelers_ingredients.addView(ingredient_weight)
                    }
                }
            }
        }
        else{
            holder.CreateMealBtn.visibility = View.VISIBLE
        }



        //Отображение блюда в приёме пищи
        if(meals[position].dish.size != 0){

            holder.DishContainer.visibility = View.VISIBLE
            holder.IngredientContainer.visibility = View.VISIBLE

            //Заполнение раздела "Блюдо"
            holder.dish_name.text = meals[position].dish[0].name
            holder.portion_weight.text = meals[position].dish[0].portion_weight.toString() + " грамм"
            holder.portion_calories.text = meals[position].dish[0].calories.toString() + " ккал"

            //Заполнение раздела "Ингредиенты"
            for(i in meals[position].dish[0].listOfIngredients){
                val ingredient_name = TextView(MainActivity_context)
                val ingredient_weight = TextView(MainActivity_context)

                var params = LinearLayout.LayoutParams((120 * MainActivity_context.resources.displayMetrics.density).toInt(), LinearLayout.LayoutParams.WRAP_CONTENT)
                params.setMargins(0, 0, 6, 4)
                ingredient_name.layoutParams = params
                ingredient_name.text = i.name
                ingredient_name.setTextColor(Color.rgb(0, 0, 0))

                params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                params.setMargins(0, 0, 0, 4)
                ingredient_weight.layoutParams = params
                ingredient_weight.text = (i.weight * TravelersAmount).toString() + " г"
                ingredient_weight.setTextColor(Color.rgb(0, 0, 0))

                holder.IngredientsList.addView(ingredient_name)
                holder.IngredientsList.addView(ingredient_weight)
            }
        }


        //Обработка открытия списка по нажатию
        if(meals[position].expanded){
            holder.expandableLayout.visibility = View.VISIBLE
        }
        else{
            holder.expandableLayout.visibility = View.GONE
        }

        holder.MealItem.setOnClickListener(){
            meals[position].expanded = !meals[position].expanded

            if(meals[position].expanded){
                holder.expandableLayout.visibility = View.VISIBLE
            }
            else{
                holder.expandableLayout.visibility = View.GONE
            }
        }


        //Переход на вкладку с выбором блюда
        holder.CreateMealBtn.setOnClickListener(){

            //holder.CreateTravelBtn.visibility = View.INVISIBLE

            //val test = MealMenuModel(meals[position].dayNum, meals[position].type_of_meal)

            //test.dish.addAll(meals[position].dish)
            //test.expanded = meals[position].expanded
            //test.show_travelers = meals[position].show_travelers
//
            //holder.list.clear()
            //holder.list.add(test)
//
            //holder.DataTransactionviewModel.send.value = holder.list



            //findNavController(MenuAddFragment_context).navigate(R.id.action_menuAddFragment_to_dishAddFragment)

            listener.Open(meals[position].dayNum, meals[position].type_of_meal)


            //holder.DataTransactionviewModel.send.observe(RegistrationActivity_context, Observer {
            //    listener.AddDish(it, meals[position].dayNum, meals[position].type_of_meal)
            //})

            //Получение выбранного блюда
            //holder.DataTransactionviewModel.sendSelectedDishToMealAdapter.observe(RegistrationActivity_context, Observer {
            //    //meals[position].dish.clear()
            //    meals[position].dish.add(it)
//
            //    holder.DataTransactionviewModel.addSelectedDishToMenuLists.value = meals[position]
            //})
        }
    }


    override fun getItemCount(): Int {
        return meals.size
    }
}