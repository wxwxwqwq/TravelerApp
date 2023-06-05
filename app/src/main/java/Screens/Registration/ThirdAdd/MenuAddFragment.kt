package Screens.Registration.ThirdAdd

import DataModels.*
import ViewModels.DataTransactionViewModel
import ViewModels.ThirdAdd.MenuAddViewModel
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.travelapp.*
import kotlin.math.abs
import androidx.navigation.fragment.findNavController

class MenuAddFragment : Fragment(), MealMenuCardAdapter.Listener {

    private lateinit var DataTransactionviewModel: DataTransactionViewModel
    private lateinit var viewModel: MenuAddViewModel
    lateinit var recyclerView: RecyclerView
    var days = 0
    var listOfDays = mutableListOf<DayMenuModel>()


    lateinit var bundle: Bundle

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_menu_add, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRetainInstance(true)

        MenuAddFragment_context = this
        DataTransactionviewModel = ViewModelProvider(MainActivity_context).get(DataTransactionViewModel::class.java)
        viewModel = ViewModelProvider(RegistrationActivity_context).get(MenuAddViewModel::class.java)
        recyclerView = view.findViewById(R.id.ListOfMenuUse)

        DataTransactionviewModel.getDaysData.observe(RegistrationActivity_context, Observer {
            listOfDays = mutableListOf<DayMenuModel>()
            for (i in it) {
                listOfDays.add(i)
            }
            recyclerView.adapter = DayMenuCardAdapter(listOfDays, this, RegistrationActivity_context.listOfTravelers.size)
        })

        val InputDays: com.google.android.material.textfield.TextInputEditText = FirstAddFragment_view.findViewById(R.id.InputDays)
        if (InputDays.text.toString() != ""){
            days = abs(InputDays.text.toString().toInt())
        }



        if (days != 0 && listOfDays.size == 0) {
            for (i in 1..days) {
                listOfDays.add(DayMenuModel(i))

                for (j in 1..3) {
                    when (j) {
                        1 -> listOfDays[i - 1].listOfMeals.add(MealMenuModel(i, "Завтрак"))
                        2 -> listOfDays[i - 1].listOfMeals.add(MealMenuModel(i, "Обед"))
                        3 -> listOfDays[i - 1].listOfMeals.add(MealMenuModel(i, "Ужин"))
                    }
                }
            }
        }


        recyclerView.adapter = DayMenuCardAdapter(listOfDays, this, RegistrationActivity_context.listOfTravelers.size)

        DataTransactionviewModel.addCreatedDaysMenuToRegistrationList.value = listOfDays


        //DataTransactionviewModel.addSelectedDishToMenuLists.observe(viewLifecycleOwner, Observer {
//
        //    if(it.size > 0){
//
        //        val test = it[0]
//
        //        for(i in listOfDays){
//
        //            if(i.day_number == test.dayNum){
//
        //                for(j in 0 until i.listOfMeals.size){
//
        //                    if(i.listOfMeals[j].type_of_meal == test.type_of_meal){
//
        //                        i.listOfMeals[j] = test
        //                        //Если в базу данных не будет сохраняться блюда, то проблема может быть в этом
        //                        DataTransactionviewModel.addCreatedDaysMenuToRegistrationList.value = listOfDays
        //                        break
        //                    }
        //                }
        //            }
        //        }
//
        //        recyclerView.adapter = DayMenuCardAdapter(listOfDays)
        //    }
        //})


        if(arguments?.getString("name") != null) {

             val dish = DishModel(
                 arguments?.getString("id").toString().toInt(),
                 arguments?.getString("name").toString(),
                 arguments?.getString("calories").toString().toInt(),
                 arguments?.getString("portion_weight").toString().toInt()
             )

            for (i in 0 until arguments?.getString("amount").toString().toInt()){

                dish.listOfIngredients.add(
                                            IngredientModel(
                                                            arguments?.getString("$i id").toString().toInt(),
                                                            arguments?.getString("$i name").toString(),
                                                            arguments?.getString("$i weight").toString().toInt()
                                                            )
                                            )
            }


            for(i in listOfDays){

                if(i.day_number == arguments?.getString("day").toString().toInt()){

                    for(j in 0 .. 2){

                        if(i.listOfMeals[j].type_of_meal == arguments?.getString("type").toString()){

                            i.listOfMeals[j].dish.clear()
                            i.listOfMeals[j].dish.add(dish)
                            //Если в базу данных не будет сохраняться блюда, то проблема может быть в этом


                        }
                    }
                }
            }

            DataTransactionviewModel.addCreatedDaysMenuToRegistrationList.value = listOfDays
        }

    }




    override fun onResume() {
        super.onResume()

        val InputDays: com.google.android.material.textfield.TextInputEditText = FirstAddFragment_view.findViewById(R.id.InputDays)

        days = if (InputDays.text.toString() != "") {
            abs(InputDays.text.toString().toInt())
        } else {
            0
        }

        if(days > listOfDays.size) {
            for (i in listOfDays.size + 1..days) {
                listOfDays.add(DayMenuModel(i))

                for(j in 1..3){
                    when (j){
                        1 -> listOfDays[i - 1].listOfMeals.add(MealMenuModel(i ,"Завтрак"))
                        2 -> listOfDays[i - 1].listOfMeals.add(MealMenuModel(i ,"Обед"))
                        3 -> listOfDays[i - 1].listOfMeals.add(MealMenuModel(i ,"Ужин"))
                    }
                }
            }

            recyclerView = view!!.findViewById(R.id.ListOfMenuUse)
            recyclerView.adapter = DayMenuCardAdapter(listOfDays, this, RegistrationActivity_context.listOfTravelers.size)

            DataTransactionviewModel.addCreatedDaysMenuToRegistrationList.value = listOfDays
        }
        else if(days < listOfDays.size){
            val list = mutableListOf<DayMenuModel>()

            for(i in 1.. days){
                list.add(listOfDays[i - 1])
            }

            listOfDays.clear()
            listOfDays.addAll(list)

            recyclerView = view!!.findViewById(R.id.ListOfMenuUse)
            recyclerView.adapter = DayMenuCardAdapter(listOfDays, this, RegistrationActivity_context.listOfTravelers.size)

            DataTransactionviewModel.addCreatedDaysMenuToRegistrationList.value = listOfDays
        }


        recyclerView = view!!.findViewById(R.id.ListOfMenuUse)
        recyclerView.adapter = DayMenuCardAdapter(listOfDays, this, RegistrationActivity_context.listOfTravelers.size)


        //if (InputDays.text.toString() != "") {
        //    if (InputDays.text.toString().toInt() != days) {
        //        days = InputDays.text.toString().toInt()
        //    }
        //}
        //else{
        //    days = 0
        //}
    }

    override fun Open(day: Int, type: String) {
        bundle = Bundle()

        bundle.putString("day", day.toString())
        bundle.putString("type", type)

        val CreateTravelBtn: Button = RegistrationActivity_context.findViewById(R.id.CreateTravelBtn)
        CreateTravelBtn.visibility = View.INVISIBLE
        findNavController().navigate(R.id.action_menuAddFragment_to_dishAddFragment, bundle)
    }

    override fun AddDish(dish: DishModel, day: Int, type: String) {

        for(i in listOfDays){

            if(i.day_number == day){

                for(j in 0 .. 2){

                    if(i.listOfMeals[j].type_of_meal == type){

                        i.listOfMeals[j].dish.clear()
                        i.listOfMeals[j].dish.add(dish)
                        //Если в базу данных не будет сохраняться блюда, то проблема может быть в этом


                    }
                }
            }
        }

        DataTransactionviewModel.addCreatedDaysMenuToRegistrationList.value = listOfDays
    }
}