package ViewModels.SecondAdd

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.travelapp.DBHelper
import com.example.travelapp.MainActivity_context
import com.example.travelapp.TravelerCardAdapter

class TravelerListViewModel : ViewModel(), TravelerCardAdapter.Listener {

    val liveData = MutableLiveData<List<String>>()
    val list = mutableListOf<String>()

    val dbHelper = DBHelper(MainActivity_context)
    val database = dbHelper.readableDatabase


    init {
        Test()
    }

    fun Test(){
        //Здесь надо будет прописать новую голику добавления элементов
        for (i in 1..5){
            list.add(i.toString())
        }
        liveData.value = list
    }

    override fun DeleteTraveler(position: Int) {
        //Здесь надо бдет прописать Новую логику удаления
        list.removeAt(position)

        liveData.value = list
    }
}