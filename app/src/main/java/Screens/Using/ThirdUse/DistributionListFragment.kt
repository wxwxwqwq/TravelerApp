package Screens.Using.ThirdUse

import DataModels.TravelerModel
import DataModels.Travelers_ingredientsModel
import Screens.Using.FirstUse.MenuUseViewModel
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.travelapp.DistributionCardAdapter
import com.example.travelapp.MainActivity_context
import com.example.travelapp.R
import com.example.travelapp.TravelerCardAdapter


class DistributionListFragment : Fragment() {

    private lateinit var viewModel: MenuUseViewModel
    lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_distribution_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(MainActivity_context).get(MenuUseViewModel::class.java)

        val listOfTravelers = viewModel.GetTravelersData()
        val listOfIngredients = viewModel.GetIngredientsData()
        val listOfDistribution = viewModel.GetDistributionData(listOfTravelers)

        recyclerView = view.findViewById(R.id.ListOfDistribution)
        recyclerView.adapter = DistributionCardAdapter(listOfTravelers, listOfDistribution, listOfIngredients)
    }
}