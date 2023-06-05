package Screens.Registration.SecondAdd

import DataModels.TravelerModel
import ViewModels.DataTransactionViewModel
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.travelapp.*


class TravelerListFragment : Fragment(), TravelerCardAdapter.Listener2{

    lateinit var recyclerView: RecyclerView
    var list = mutableListOf<TravelerModel>()

    private lateinit var viewModel: DataTransactionViewModel

    lateinit var bundle: Bundle


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_traveler_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRetainInstance(true)

        //findNavController().navigate(R.id.action_travelerAddFragment_to_travelerListFragment)

        //TravelerListFragment_view = view

        viewModel = ViewModelProvider(MainActivity_context).get(DataTransactionViewModel::class.java)

        recyclerView = view.findViewById(R.id.ListOfTravelers)
        //recyclerView.adapter = TravelerCardAdapter(list, RegistrationActivity_context, this)

        viewModel.getTravelersData.observe(activity as LifecycleOwner, Observer {
            list = mutableListOf<TravelerModel>()
            for (i in it) {
                list.add(i)
            }
            recyclerView.adapter = TravelerCardAdapter(list, RegistrationActivity_context, this)
        })

        //Обработка нажатия на кнопку "Добавить"
        val addTravelerBtn: Button = view.findViewById(R.id.AddTravelerBtn)
        addTravelerBtn.setOnClickListener {
            val CreateTravelBtn: Button = RegistrationActivity_context.findViewById(R.id.CreateTravelBtn)
            CreateTravelBtn.visibility = View.INVISIBLE
            findNavController().navigate(R.id.action_travelerListFragment_to_travelerAddFragment)
        }
    }

    override fun UpdateTraveler(traveler: TravelerModel) {

        viewModel = ViewModelProvider(MainActivity_context).get(DataTransactionViewModel::class.java)

        bundle = Bundle()

        bundle.putString("TravelerName", traveler.TravelerName)
        bundle.putString("Age", traveler.Age)
        bundle.putString("Gender", traveler.Gender)
        bundle.putString("Restrictions", traveler.Restrictions)

        val CreateTravelBtn: Button = RegistrationActivity_context.findViewById(R.id.CreateTravelBtn)
        CreateTravelBtn.visibility = View.INVISIBLE
        findNavController().navigate(R.id.action_travelerListFragment_to_travelerAddFragment, bundle)

        //list[position].TravelerName = arguments?.getString("TravelerName1").toString()
        //list[position].Age = arguments?.getString("Age1").toString()
        //list[position].Gender = arguments?.getString("Gender1").toString()
        //list[position].Restrictions = arguments?.getString("Restrictions1").toString()
//
        //viewModel.cha.observe(activity as LifecycleOwner, Observer {
        //    list[position] = it
        //})


        //TravelerModel(
        //    arguments?.getString("TravelerName1").toString(),
        //    arguments?.getString("Age1").toString(),
        //    arguments?.getString("Gender1").toString(),
        //    arguments?.getString("Restrictions1").toString()
            //"1",
            //"1",
            //"Муж",
            //"Нет"

        //)

        //recyclerView = TravelerAddFragment_context.findViewById(R.id.ListOfTravelers)
        //recyclerView.adapter = TravelerCardAdapter(list, RegistrationActivity_context, this)

        //viewModel.changeTravelerData.value = list
    }

    override fun AddChangedTraveler(traveler: TravelerModel, position: Int) {
        list[position] = traveler
        viewModel.changeTravelersData.value = list
    }


}