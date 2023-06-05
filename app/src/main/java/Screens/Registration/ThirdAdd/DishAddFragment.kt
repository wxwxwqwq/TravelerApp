package Screens.Registration.ThirdAdd

import DataModels.DishModel
import DataModels.MealMenuModel
import ViewModels.DataTransactionViewModel
import ViewModels.ThirdAdd.MenuAddViewModel
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import com.example.travelapp.R
import com.example.travelapp.RegistrationActivity_context
import com.google.android.material.textfield.TextInputLayout
import com.leo.searchablespinner.SearchableSpinner
import com.leo.searchablespinner.interfaces.OnItemSelectListener
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.travelapp.MainActivity_context


class DishAddFragment : Fragment() {

    private lateinit var viewModel: MenuAddViewModel
    private lateinit var DataTransactionviewModel: DataTransactionViewModel
    var listDishAdvice = mutableListOf<DishModel>()
    //val list = mutableListOf<MealMenuModel>()

    lateinit var bundle: Bundle

    private lateinit var meal: MealMenuModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dish_add, container, false)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRetainInstance(true)

        val CreateTravelBtn: Button = RegistrationActivity_context.findViewById(R.id.CreateTravelBtn)
        val backBtn: ImageView = view.findViewById(R.id.BackBtn)
        val AddDishBtn: Button = view.findViewById(R.id.AddDishBtn)
        val portionWeight: TextView = view.findViewById(R.id.portion_weight_add)
        val portionCalories: TextView = view.findViewById(R.id.portion_calories_add)
        val InputDish: AutoCompleteTextView = view.findViewById(R.id.InputDish)
        val DishWarning: TextView = view.findViewById(R.id.DishWarning)

        DataTransactionviewModel = ViewModelProvider(MainActivity_context).get(DataTransactionViewModel::class.java)
        viewModel = ViewModelProvider(RegistrationActivity_context).get(MenuAddViewModel::class.java)


        viewModel.liveData.observe(activity as LifecycleOwner, Observer {
            listDishAdvice.clear()
            for (i in it) {
                listDishAdvice.add(i)
            }
        })

        //DataTransactionviewModel.send.observe(viewLifecycleOwner, Observer {
//
        //    if(it.size > 0) {
        //        meal = it[0]
        //    }
        //    //DataTransactionviewModel.sendSelectedDishToMealAdapter.value = i
        //})


//Создание диалогового окна, используещегося как Spinner, с выбором продукта
//**************************************************************************************************
        /*Подобный спиннер представляет собой контейнер TextInputLayout
         с текстовым полем AutoCompleteTextView для размещения данных, чтобы добиться сходства
         со стандартным Spinner (можно использовать TextInputEditText, но уже не будет похоже на спиннер)*/
        val searchableSpinner = SearchableSpinner(RegistrationActivity_context)
        val textInputSpinner: TextInputLayout = view.findViewById(R.id.textInputSpinner)


        //Определенияе параметров внешнего вида диалогового окна
        searchableSpinner.showKeyboardByDefault = false
        searchableSpinner.animationDuration = 105
        searchableSpinner.windowTitle = "Выберите продукт"
        searchableSpinner.negativeButtonText = "Назад"
        searchableSpinner.negativeButtonBackgroundColor = Color.rgb(143, 99, 11)
        searchableSpinner.windowTopBackgroundColor = Color.rgb(143, 99, 11)
        searchableSpinner.searchQueryHint = "Введите название"
        searchableSpinner.highlightSelectedItem = false

        //Заполнение диалогового окна данными
        val DataList = arrayListOf<String>()
        for (i in listDishAdvice){
            DataList.add(i.name)
        }
        searchableSpinner.setSpinnerListItems(DataList)

        //Логика диалогового окна
        textInputSpinner.editText?.keyListener = null
        //Обработка размещения данных, выбранных в диалоговом окне, в AutoCompleteTextView
        searchableSpinner.onItemSelectListener = object : OnItemSelectListener {
            override fun setOnItemSelectListener(position: Int, selectedString: String) {

                textInputSpinner.editText?.setText(selectedString)

                for (i in listDishAdvice){
                    if(i.name == selectedString){

                        portionWeight.text = "Размер порции: " + i.portion_weight.toString() + " г"
                        portionCalories.text = "Калорийность порции: " + i.calories.toString() + " ккал"
                        break
                    }
                }

                portionWeight.visibility = View.VISIBLE
                portionCalories.visibility = View.VISIBLE
            }
        }
        //Запуск диалогового окна при фокусировании на AutoCompleteTextView, чтобы спиннер работал с первого клика
        //(Сначала идёт обработка фокусировки на элементе, а потом обрабатываются клики)
        textInputSpinner.editText?.onFocusChangeListener = View.OnFocusChangeListener { vieww, hasFocus ->
            if (hasFocus) {
                searchableSpinner.show()
            }
        }
        //Запуск диалогового окна при клике на AutoCompleteTextView, чтобы спиннер работал со второго нажатия на сфокусированном AutoCompleteTextView
        textInputSpinner.editText?.setOnClickListener {
            searchableSpinner.show()
        }
        //Запуск диалогового окна при нажатии на стрелку спинера, расположеннуя справа
        textInputSpinner.setEndIconOnClickListener {
            searchableSpinner.show()
        }
//**************************************************************************************************


        //Обработка нажатия кнопки "Добавить"
        AddDishBtn.setOnClickListener {

            if (InputDish.text.toString() == ""){
                DishWarning.text = "Не выбрано блюдо!"
                DishWarning.visibility = View.VISIBLE
                Handler().postDelayed(Runnable {
                    DishWarning.visibility = View.INVISIBLE
                }, 5000)
            }
            else {
                for (i in listDishAdvice){
                    if(i.name == InputDish.text.toString()){

                        //meal.dish.clear()
                        //meal.dish.add(i)

                        //val test = MealMenuModel(meal.dayNum, meal.type_of_meal)
//
                        //test.dish.addAll(meal.dish)
                        //test.expanded = meal.expanded
                        //test.show_travelers = meal.show_travelers
//
                        //list.clear()
                        //list.add(test)

                        //val dish = DishModel(i.id, i.name, i.calories, i.portion_weight)

                        //i.listOfIngredients.forEach { dish.listOfIngredients.add() }

                        //dish.listOfIngredients.addAll(i.listOfIngredients)

                        //DataTransactionviewModel.send.value = dish

                        bundle = Bundle()

                        bundle.putString("day", arguments?.getString("day").toString())
                        bundle.putString("type", arguments?.getString("type").toString())

                        bundle.putString("id", i.id.toString())
                        bundle.putString("name", i.name)
                        bundle.putString("calories", i.calories.toString())
                        bundle.putString("portion_weight", i.portion_weight.toString())
                        bundle.putString("amount", i.listOfIngredients.size.toString())

                        i.listOfIngredients.forEach {
                            bundle.putString(it.name + " id", it.id.toString())
                            bundle.putString(it.name + " name", it.name)
                            bundle.putString(it.name + " weight", it.weight.toString())
                        }

                        for(j in 0 until i.listOfIngredients.size){
                            bundle.putString("$j id", i.listOfIngredients[j].id.toString())
                            bundle.putString("$j name", i.listOfIngredients[j].name)
                            bundle.putString("$j weight", i.listOfIngredients[j].weight.toString())
                        }

                        break
                    }
                }

                CreateTravelBtn.visibility = View.VISIBLE
                findNavController().navigate(R.id.action_dishAddFragment_to_menuAddFragment, bundle)
            }
        }



        //Обработка нажатия на кнопку "Назад"
        backBtn.setOnClickListener(){

            CreateTravelBtn.visibility = View.VISIBLE
            findNavController().navigate(R.id.action_dishAddFragment_to_menuAddFragment)
        }
    }
}