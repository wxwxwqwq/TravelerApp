package ViewModels.ThirdAdd

import DataModels.DishModel
import DataModels.FoodModel
import DataModels.IngredientModel
import android.database.Cursor
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import com.example.travelapp.DBHelper
import com.example.travelapp.MainActivity_context
import com.example.travelapp.MenuAddFragment_context
import com.example.travelapp.R

class MenuAddViewModel : ViewModel() {

    val liveData = MutableLiveData<List<DishModel>>()

    val dbHelper = DBHelper(MainActivity_context)
    val database = dbHelper.readableDatabase

    var list = mutableListOf<DishModel>()


    init {
        getDish()
    }

    fun getDish(){

        list.clear()
        var cursor: Cursor = database.query("Dish", null, null, null, null, null, null)

        if(cursor.moveToFirst()){
            do {
                list.add(DishModel(
                    cursor.getInt(cursor.getColumnIndex("id")),
                    cursor.getString(cursor.getColumnIndex("name")),
                    cursor.getFloat(cursor.getColumnIndex("calories")).toInt(),
                    cursor.getInt(cursor.getColumnIndex("portion_weight")),
                ))
            }while(cursor.moveToNext())
        }

        cursor.close()

        for (i in list) {
            cursor = database.query("Ingredients_in_dish", null, "dish_id='${i.id}'", null, null, null, null)

            if(cursor.moveToFirst()){
                do {

                    val cursor2: Cursor = database.query("Ingredients", null, "id='${cursor.getInt(cursor.getColumnIndex("ingredient_id"))}'", null, null, null, null)
                    if(cursor2.moveToFirst()) {
                        do {

                            i.listOfIngredients.add(
                                IngredientModel(
                                    cursor2.getInt(cursor2.getColumnIndex("id")),
                                    cursor2.getString(cursor2.getColumnIndex("name")),
                                    cursor.getInt(cursor.getColumnIndex("weight_of_ingredient"))
                                )
                            )
                        } while (cursor2.moveToNext())
                    }
                }while(cursor.moveToNext())
            }
        }

        liveData.value = list
    }
}