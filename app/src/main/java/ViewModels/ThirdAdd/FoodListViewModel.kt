package ViewModels.ThirdAdd

import DataModels.FoodModel
import android.content.ContentValues
import android.database.Cursor
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.travelapp.DBHelper
import com.example.travelapp.MainActivity_context

class FoodListViewModel : ViewModel() {

    val liveData = MutableLiveData<List<FoodModel>>()

    val dbHelper = DBHelper(MainActivity_context)
    val database = dbHelper.readableDatabase

    var list = mutableListOf<FoodModel>()


    init {
        getFood()
    }

    fun getFood(){

        list.clear()
        val cursor: Cursor = database.query("Food", null, null, null, null, null, null)

        if(cursor.moveToFirst()){
            do {
                list.add(FoodModel(
                    cursor.getInt(cursor.getColumnIndex("id")),
                    cursor.getString(cursor.getColumnIndex("name")),
                    cursor.getFloat(cursor.getColumnIndex("calories")).toInt(),
                    cursor.getString(cursor.getColumnIndex("category")),
                ))
            }while(cursor.moveToNext())
        }

        cursor.close()

        liveData.value = list


    }

    fun addFood(name: String, calories: Float, category: String){

        val contentValues = ContentValues()
        contentValues.put("name", name)
        contentValues.put("calories", calories)
        contentValues.put("category", category)

        database.insert("Food", null, contentValues)

        getFood()
    }

    fun delFood(id: Int){

        database.delete("Food", "id = $id", null)

        getFood()
    }
}