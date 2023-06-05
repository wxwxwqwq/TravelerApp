package Screens.Using.SecondUse

import DataModels.TravelerModel
import Screens.Using.FirstUse.MenuUseViewModel
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.travelapp.*


class TravelerListUseFragment : Fragment(), TravelerCardAdapter.Listener, TravelerCardAdapter.Listener2 {

    private lateinit var viewModel: MenuUseViewModel
    lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_traveler_list_use, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(MainActivity_context).get(MenuUseViewModel::class.java)

        recyclerView = view.findViewById(R.id.ListOfTravelers)
        //Загрузка данных из ViewModel в RecyclerView через LiveData

        val list = viewModel.GetTravelersData()

        list.forEach { it.changeable = false }

        recyclerView.adapter = TravelerCardAdapter(list, this, this)
    }






    override fun UpdateTraveler(traveler: TravelerModel) {

    }

    override fun AddChangedTraveler(traveler: TravelerModel, position: Int) {

    }

    override fun DeleteTraveler(position: Int) {

    }
}