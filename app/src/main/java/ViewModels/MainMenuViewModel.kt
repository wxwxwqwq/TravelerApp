package ViewModels

import DataModels.FoodModel
import DataModels.TravelModel
import android.database.Cursor
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.travelapp.DBHelper
import com.example.travelapp.MainActivity_context
import com.example.travelapp.TravelCardAdapter

class MainMenuViewModel : ViewModel(), TravelCardAdapter.Listener {

    val liveData = MutableLiveData<List<TravelModel>>()
    val list = mutableListOf<TravelModel>()

    val dbHelper = DBHelper(MainActivity_context)
    val database = dbHelper.readableDatabase

    init {
        GetTravel()
    }

    fun GetTravel(){

        list.clear()
        val cursor: Cursor = database.query("Travel", null, null, null, null, null, null)


        if(cursor.moveToFirst()){
            do {

                val cursor2: Cursor = database.query("Travelers_in_travel", null, "travel_id = '${cursor.getString(cursor.getColumnIndex("id"))}'", null, null, null, null)
                var amount = 0
                if(cursor2.moveToFirst()){
                    do {

                        amount++

                    }while(cursor2.moveToNext())
                }
                cursor2.close()

                list.add(
                    TravelModel(
                        cursor.getInt(cursor.getColumnIndex("id")),
                        cursor.getString(cursor.getColumnIndex("name")),
                        amount,
                        cursor.getFloat(cursor.getColumnIndex("days")).toInt()
                    )
                )
            }while(cursor.moveToNext())
        }

        cursor.close()

        liveData.value = list
    }

    override fun Del(position: Int) {

        val id = list[position].Id

        database.delete("Travel", "id = $id", null)

        GetTravel()

        //list[position].Id
//
        //liveData.value = list
    }


}