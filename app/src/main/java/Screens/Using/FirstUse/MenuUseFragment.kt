package Screens.Using.FirstUse

import DataModels.DishModel
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.travelapp.*

class MenuUseFragment : Fragment(), MealMenuCardAdapter.Listener {

    private lateinit var viewModel: MenuUseViewModel
    lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_menu_use, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(MainActivity_context).get(MenuUseViewModel::class.java)

        viewModel.GetMenuData()

        recyclerView = view.findViewById(R.id.ListOfMenuUse)
        //Загрузка данных из ViewModel в RecyclerView через LiveData
        viewModel.liveData.observe(viewLifecycleOwner, Observer {
            recyclerView.adapter = DayMenuCardAdapter(it, this, viewModel.TravelerAmount())
        })


        val FoodBtn: Button = view.findViewById(R.id.FoodBtn)
        FoodBtn.setOnClickListener() {

            val ExitBtn: Button = UsingActivity_context.findViewById(R.id.ExitBtn)
            ExitBtn.visibility = View.GONE
            findNavController().navigate(R.id.action_menuUseFragment_to_foodListUseFragment)
        }
    }





    override fun Open(day: Int, type: String) {

    }

    override fun AddDish(dish: DishModel, day: Int, type: String) {

    }
}