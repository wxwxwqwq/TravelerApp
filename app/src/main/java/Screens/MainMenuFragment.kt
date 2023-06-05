package Screens

import ViewModels.MainMenuViewModel
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.travelapp.R
import com.example.travelapp.TravelCardAdapter
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.travelapp.MainActivity_context

class MainMenuFragment : Fragment() {

    lateinit var viewModel: MainMenuViewModel
    lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainMenuViewModel::class.java)


        recyclerView = view.findViewById(R.id.ListOfTravels)
        //Загрузка данных из ViewModel в RecyclerView через LiveData
        viewModel.liveData.observe(viewLifecycleOwner, Observer {
            recyclerView.adapter = TravelCardAdapter(it, viewModel)
        })


        //Обработка нажатия на круглую кнопку с плюсом
        val addButton: at.markushi.ui.CircleButton = view.findViewById(R.id.addButton)
        addButton.setOnClickListener {
            MainActivity_context.navController.navigate(R.id.action_mainMenuFragment_to_registrationActivity)
        }
    }
}