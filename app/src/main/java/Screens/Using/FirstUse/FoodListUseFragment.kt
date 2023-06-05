package Screens.Using.FirstUse

import DataModels.Travelers_ingredientsModel
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.travelapp.*


class FoodListUseFragment : Fragment() {

    private lateinit var viewModel: MenuUseViewModel
    lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_food_list_use, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val backBtn: ImageView = view.findViewById(R.id.BackBtn)


        viewModel = ViewModelProvider(MainActivity_context).get(MenuUseViewModel::class.java)

        val listOfIngredients = viewModel.GetIngredientsData()
        val listOfDistribution = viewModel.GetDistributionData(viewModel.GetTravelersData())

        val list1 = listOfDistribution.groupBy { it.ingredient_id }
        val list2 = mutableListOf<Travelers_ingredientsModel>()

        for(j in list1){
            var a = 0
            for(k in j.value) {

                a += k.weight_of_food

            }

            list2.add(
                Travelers_ingredientsModel(
                    j.value[0].day,
                    j.value[0].traveler_id,
                    j.key,
                    j.value[0].type_of_meal,
                    a
                )
            )
        }

        listOfDistribution.clear()
        listOfDistribution.addAll(list2)

        recyclerView = view.findViewById(R.id.ListOfFood2)
        recyclerView.adapter = FoodListCardAdapter(listOfDistribution, listOfIngredients)




        backBtn.setOnClickListener(){
            val ExitBtn: Button = UsingActivity_context.findViewById(R.id.ExitBtn)
            ExitBtn.visibility = View.VISIBLE
            findNavController().navigate(R.id.action_foodListUseFragment_to_menuUseFragment)
        }
    }
}