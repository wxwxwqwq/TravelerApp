package Screens.Using.FirstUse

import DataModels.*
import android.database.Cursor
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.travelapp.DBHelper
import com.example.travelapp.MainActivity_context

class MenuUseViewModel : ViewModel() {

    val liveData = MutableLiveData<List<DayMenuModel>>()
    val list = mutableListOf<DayMenuModel>()

    val dbHelper = DBHelper(MainActivity_context)
    val database = dbHelper.readableDatabase

    var travel_id = 0

    init {

    }

    fun GetMenuData(){

        list.clear()

        val cursor: Cursor = database.query("Travel", null, "id = '${travel_id}'", null, null, null, null)

        cursor.moveToFirst()

        for(i in 1 .. cursor.getInt(cursor.getColumnIndex("days"))) {
            list.add(DayMenuModel(i))
        }


        val cursorBreakfast: Cursor = database.query("Breakfast", null, "travel_id = '${travel_id}'", null, null, null, null)
        val cursorDinner: Cursor = database.query("Dinner", null, "travel_id = '${travel_id}'", null, null, null, null)
        val cursorSupper: Cursor = database.query("Supper", null, "travel_id = '${travel_id}'", null, null, null, null)


        if(cursorBreakfast.moveToFirst()){
            do {

                val meal = MealMenuModel(
                    cursorBreakfast.getInt(cursorBreakfast.getColumnIndex("day")),
                    "Завтрак"
                )

                val cursorDish: Cursor = database.query("Dish", null, "id = '${cursorBreakfast.getInt(cursorBreakfast.getColumnIndex("dish_id"))}'", null, null, null, null)
                cursorDish.moveToFirst()
                val dish = DishModel(
                                cursorDish.getInt(cursorDish.getColumnIndex("id")),
                                cursorDish.getString(cursorDish.getColumnIndex("name")),
                                cursorDish.getInt(cursorDish.getColumnIndex("calories")),
                                cursorDish.getInt(cursorDish.getColumnIndex("portion_weight"))
                                )

                cursorDish.close()

                val cursorIngredients: Cursor = database.query("Ingredients_in_dish", null, "dish_id = '${cursorBreakfast.getInt(cursorBreakfast.getColumnIndex("dish_id"))}'", null, null, null, null)
                if(cursorIngredients.moveToFirst()){
                    do {
                        val cursorIngredient: Cursor = database.query("Ingredients", null, "id = '${cursorIngredients.getInt(cursorIngredients.getColumnIndex("ingredient_id"))}'", null, null, null, null)
                        cursorIngredient.moveToFirst()
                        dish.listOfIngredients.add(IngredientModel(
                            cursorIngredient.getInt(cursorIngredient.getColumnIndex("id")),
                            cursorIngredient.getString(cursorIngredient.getColumnIndex("name")),
                            cursorIngredients.getInt(cursorIngredients.getColumnIndex("weight_of_ingredient"))
                        ))

                        cursorIngredient.close()

                    }while(cursorIngredients.moveToNext())
                }

                cursorIngredients.close()


                meal.dish.add(dish)
                meal.show_travelers = true

                list[cursorBreakfast.getInt(cursorBreakfast.getColumnIndex("day")) - 1].listOfMeals.add(meal)


            }while(cursorBreakfast.moveToNext())
        }

        if(cursorDinner.moveToFirst()){
            do {

                val meal = MealMenuModel(
                    cursorDinner.getInt(cursorDinner.getColumnIndex("day")),
                    "Обед"
                )

                val cursorDish: Cursor = database.query("Dish", null, "id = '${cursorDinner.getInt(cursorDinner.getColumnIndex("dish_id"))}'", null, null, null, null)
                cursorDish.moveToFirst()
                val dish = DishModel(
                    cursorDish.getInt(cursorDish.getColumnIndex("id")),
                    cursorDish.getString(cursorDish.getColumnIndex("name")),
                    cursorDish.getInt(cursorDish.getColumnIndex("calories")),
                    cursorDish.getInt(cursorDish.getColumnIndex("portion_weight"))
                )

                cursorDish.close()

                val cursorIngredients: Cursor = database.query("Ingredients_in_dish", null, "dish_id = '${cursorDinner.getInt(cursorDinner.getColumnIndex("dish_id"))}'", null, null, null, null)
                if(cursorIngredients.moveToFirst()){
                    do {
                        val cursorIngredient: Cursor = database.query("Ingredients", null, "id = '${cursorIngredients.getInt(cursorIngredients.getColumnIndex("ingredient_id"))}'", null, null, null, null)
                        cursorIngredient.moveToFirst()
                        dish.listOfIngredients.add(IngredientModel(
                            cursorIngredient.getInt(cursorIngredient.getColumnIndex("id")),
                            cursorIngredient.getString(cursorIngredient.getColumnIndex("name")),
                            cursorIngredients.getInt(cursorIngredients.getColumnIndex("weight_of_ingredient"))
                        ))

                        cursorIngredient.close()

                    }while(cursorIngredients.moveToNext())
                }

                cursorIngredients.close()

                meal.dish.add(dish)
                meal.show_travelers = true

                list[cursorDinner.getInt(cursorDinner.getColumnIndex("day")) - 1].listOfMeals.add(meal)


            }while(cursorDinner.moveToNext())
        }

        if(cursorSupper.moveToFirst()){
            do {

                val meal = MealMenuModel(
                    cursorSupper.getInt(cursorSupper.getColumnIndex("day")),
                    "Ужин"
                )

                val cursorDish: Cursor = database.query("Dish", null, "id = '${cursorSupper.getInt(cursorSupper.getColumnIndex("dish_id"))}'", null, null, null, null)
                cursorDish.moveToFirst()
                val dish = DishModel(
                    cursorDish.getInt(cursorDish.getColumnIndex("id")),
                    cursorDish.getString(cursorDish.getColumnIndex("name")),
                    cursorDish.getInt(cursorDish.getColumnIndex("calories")),
                    cursorDish.getInt(cursorDish.getColumnIndex("portion_weight"))
                )

                cursorDish.close()

                val cursorIngredients: Cursor = database.query("Ingredients_in_dish", null, "dish_id = '${cursorSupper.getInt(cursorSupper.getColumnIndex("dish_id"))}'", null, null, null, null)
                if(cursorIngredients.moveToFirst()){
                    do {
                        val cursorIngredient: Cursor = database.query("Ingredients", null, "id = '${cursorIngredients.getInt(cursorIngredients.getColumnIndex("ingredient_id"))}'", null, null, null, null)
                        cursorIngredient.moveToFirst()
                        dish.listOfIngredients.add(IngredientModel(
                            cursorIngredient.getInt(cursorIngredient.getColumnIndex("id")),
                            cursorIngredient.getString(cursorIngredient.getColumnIndex("name")),
                            cursorIngredients.getInt(cursorIngredients.getColumnIndex("weight_of_ingredient"))
                        ))

                        cursorIngredient.close()

                    }while(cursorIngredients.moveToNext())
                }

                cursorIngredients.close()

                meal.dish.add(dish)
                meal.show_travelers = true

                list[cursorSupper.getInt(cursorSupper.getColumnIndex("day")) - 1].listOfMeals.add(meal)


            }while(cursorSupper.moveToNext())
        }


        cursor.close()
        cursorBreakfast.close()
        cursorDinner.close()
        cursorSupper.close()

        liveData.value = list
    }

    fun GetTravelersIngredientsData(day: Int, type: String): Triple<MutableList<Travelers_ingredientsModel>, MutableList<TravelerModel>, MutableList<IngredientModel>> {

        val listOfDistribution = mutableListOf<Travelers_ingredientsModel>()
        val listOfTravelers = mutableListOf<TravelerModel>()
        val listOfIngredients = mutableListOf<IngredientModel>()





        val cursorTraveler: Cursor = database.query("Travelers_in_travel", null, "travel_id = '${travel_id}'", null, null, null, null)

        if(cursorTraveler.moveToFirst()){
            do {

                val trav = TravelerModel(
                    cursorTraveler.getString(cursorTraveler.getColumnIndex("name")),
                    cursorTraveler.getInt(cursorTraveler.getColumnIndex("age")).toString(),
                    cursorTraveler.getString(cursorTraveler.getColumnIndex("gender")),
                    cursorTraveler.getString(cursorTraveler.getColumnIndex("restrictions"))
                )

                trav.Id = cursorTraveler.getInt(cursorTraveler.getColumnIndex("id"))

                listOfTravelers.add(trav)

            }while(cursorTraveler.moveToNext())
        }
        cursorTraveler.close()






        val cursorIngredient: Cursor = database.query("Ingredients", null, null, null, null, null, null)

        if(cursorIngredient.moveToFirst()){
            do {

                listOfIngredients.add(
                    IngredientModel(
                        cursorIngredient.getInt(cursorIngredient.getColumnIndex("id")),
                        cursorIngredient.getString(cursorIngredient.getColumnIndex("name")),
                        0
                    )
                )

            }while(cursorIngredient.moveToNext())
        }
        cursorIngredient.close()







        var type_of_meal = ""
        when(type){

            "Завтрак" -> type_of_meal = "Breakfast"
            "Обед" -> type_of_meal = "Dinner"
            "Ужин" -> type_of_meal = "Supper"
        }
        val cursorDistribution: Cursor = database.query("Traveler_ingredients", null, "day = '${day}' AND type_of_meal = '${type_of_meal}'", null, null, null, null)

        if(cursorDistribution.moveToFirst()){
            do {

                listOfDistribution.add(
                    Travelers_ingredientsModel(
                        cursorDistribution.getInt(cursorDistribution.getColumnIndex("day")),
                        cursorDistribution.getInt(cursorDistribution.getColumnIndex("traveler_id")),
                        cursorDistribution.getInt(cursorDistribution.getColumnIndex("ingredient_id")),
                        cursorDistribution.getString(cursorDistribution.getColumnIndex("type_of_meal")),
                        cursorDistribution.getInt(cursorDistribution.getColumnIndex("weight_of_food")),
                    )
                )

            }while(cursorDistribution.moveToNext())
        }
        cursorDistribution.close()


        var list1 = listOfDistribution.groupBy { it.traveler_id }
        var list2 : Map<Int, List<Travelers_ingredientsModel>>
        val list3 = mutableListOf<Travelers_ingredientsModel>()

        for(i in list1){
            list2 = i.value.groupBy { it.ingredient_id }

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
        }

        listOfDistribution.clear()
        listOfDistribution.addAll(list3)



        listOfTravelers.forEach {

            for (i in listOfDistribution){
                if(it.Id == i.traveler_id){
                    it.amount++
                }
            }
        }

        val list = mutableListOf<TravelerModel>()
        listOfTravelers.forEach {

            if(it.amount > 0){
                list.add(it)
            }

        }

        listOfTravelers.clear()
        listOfTravelers.addAll(list)

        return Triple(listOfDistribution, listOfTravelers, listOfIngredients)
    }

    fun GetTravelersData(): MutableList<TravelerModel> {

        val list = mutableListOf<TravelerModel>()

        val cursorTraveler: Cursor = database.query("Travelers_in_travel", null, "travel_id = '${travel_id}'", null, null, null, null)

        if(cursorTraveler.moveToFirst()){
            do {

                val trav = TravelerModel(
                    cursorTraveler.getString(cursorTraveler.getColumnIndex("name")),
                    cursorTraveler.getInt(cursorTraveler.getColumnIndex("age")).toString(),
                    cursorTraveler.getString(cursorTraveler.getColumnIndex("gender")),
                    cursorTraveler.getString(cursorTraveler.getColumnIndex("restrictions"))
                )

                trav.Id = cursorTraveler.getInt(cursorTraveler.getColumnIndex("id"))
                trav.Group = cursorTraveler.getInt(cursorTraveler.getColumnIndex("group_num"))

                list.add(trav)

            }while(cursorTraveler.moveToNext())
        }
        cursorTraveler.close()

        return list
    }

    fun GetIngredientsData(): MutableList<IngredientModel> {

        val list = mutableListOf<IngredientModel>()

        val cursorIngredient: Cursor = database.query("Ingredients", null, null, null, null, null, null)

        if(cursorIngredient.moveToFirst()){
            do {

                list.add(
                    IngredientModel(
                        cursorIngredient.getInt(cursorIngredient.getColumnIndex("id")),
                        cursorIngredient.getString(cursorIngredient.getColumnIndex("name")),
                        0
                    )
                )

            }while(cursorIngredient.moveToNext())
        }
        cursorIngredient.close()

        return list
    }

    fun GetDistributionData(travelers: MutableList<TravelerModel>): MutableList<Travelers_ingredientsModel> {

        val list = mutableListOf<Travelers_ingredientsModel>()

        travelers.forEach {

            val cursorDistribution: Cursor = database.query("Traveler_ingredients", null, "traveler_id = ${it.Id}", null, null, null, null)

            if (cursorDistribution.moveToFirst()) {
                do {

                    list.add(
                        Travelers_ingredientsModel(
                            cursorDistribution.getInt(cursorDistribution.getColumnIndex("day")),
                            cursorDistribution.getInt(cursorDistribution.getColumnIndex("traveler_id")),
                            cursorDistribution.getInt(cursorDistribution.getColumnIndex("ingredient_id")),
                            cursorDistribution.getString(cursorDistribution.getColumnIndex("type_of_meal")),
                            cursorDistribution.getInt(cursorDistribution.getColumnIndex("weight_of_food")),
                        )
                    )

                } while (cursorDistribution.moveToNext())
            }
            cursorDistribution.close()
        }

        return list
    }

    fun SetId(id : Int){
        travel_id = id
    }

    fun TravelerAmount(): Int {

        val cursor: Cursor = database.query("Travelers_in_travel", null, "travel_id = '${travel_id}'", null, null, null, null)
        var amount = 0
        if(cursor.moveToFirst()){
            do {

                amount++

            }while(cursor.moveToNext())
        }
        cursor.close()


        return amount
    }

}