package Screens.Registration.ThirdAdd.Food

import DataModels.FoodModel
import ViewModels.DataTransactionViewModel
import ViewModels.ThirdAdd.FoodListViewModel
import android.content.Context
import android.graphics.Color.rgb
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.travelapp.*
import com.google.android.material.textfield.TextInputLayout
import com.leo.searchablespinner.SearchableSpinner
import com.leo.searchablespinner.interfaces.OnItemSelectListener


class FoodListFragment : Fragment() {

    lateinit var recyclerView: RecyclerView

    private lateinit var viewModelGet: FoodListViewModel
    private lateinit var viewModelSave: DataTransactionViewModel

    var listFoodAdvice = mutableListOf<FoodModel>()
    var listFood = mutableListOf<FoodModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_food_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRetainInstance(true)
        FoodListFragment_context = this
        FoodListFragment_view = view

        val CreateTravelBtn: Button = RegistrationActivity_context.findViewById(R.id.CreateTravelBtn)

        val Calories: TextView = view.findViewById(R.id.Calories)
        val textInputWeight: TextInputLayout = view.findViewById(R.id.textInputWeight)
        val AddFoodBtn: Button = view.findViewById(R.id.AddFoodBtn)
        val InputFood: AutoCompleteTextView = view.findViewById(R.id.InputFood)
        val InputWeight: com.google.android.material.textfield.TextInputEditText = view.findViewById(R.id.InputWeight)
        val Warning: TextView = view.findViewById(R.id.Warning)


        viewModelGet = ViewModelProvider(RegistrationActivity_context).get(FoodListViewModel::class.java)

        viewModelGet.liveData.observe(activity as LifecycleOwner, Observer {
            listFoodAdvice.clear()
            for (i in it) {
                listFoodAdvice.add(i)
            }
        })

        viewModelSave = ViewModelProvider(RegistrationActivity_context).get(DataTransactionViewModel::class.java)
        recyclerView = view.findViewById(R.id.ListOfFood)

        viewModelSave.getFoodData.observe(activity as LifecycleOwner, Observer {
            listFood.clear()
            for (i in it) {
                listFood.add(i)
            }
            recyclerView.adapter = FoodCardAdapter(listFood, RegistrationActivity_context)
            SetInfo()
        })

        SetInfo()

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
        searchableSpinner.negativeButtonBackgroundColor = rgb(143, 99, 11)
        searchableSpinner.windowTopBackgroundColor = rgb(143, 99, 11)
        searchableSpinner.searchQueryHint = "Введите название"
        searchableSpinner.highlightSelectedItem = false

        //Заполнение диалогового окна данными
        val DataList = arrayListOf<String>()
        for (i in listFoodAdvice){
            DataList.add(i.Name)
        }
        searchableSpinner.setSpinnerListItems(DataList)

        //Логика диалогового окна
        textInputSpinner.editText?.keyListener = null
            //Обработка размещения данных, выбранных в диалоговом окне, в AutoCompleteTextView
        searchableSpinner.onItemSelectListener = object : OnItemSelectListener {
            override fun setOnItemSelectListener(position: Int, selectedString: String) {

                textInputSpinner.editText?.setText(selectedString)

                for (i in listFoodAdvice){
                    if(i.Name == selectedString){
                        Calories.text = "Калорий на 100 грамм: ${i.Calories.toString()} ккал"
                        break
                    }
                }

                Calories.visibility = View.VISIBLE
                textInputWeight.visibility = View.VISIBLE
            }
        }
            //Запуск диалогового окна при фокусировании на AutoCompleteTextView, чтобы спиннер работал с первого клика
            //(Сначала идёт обработка фокусировки на элементе, а потом обрабатываются клики)
        textInputSpinner.editText?.onFocusChangeListener = OnFocusChangeListener { vieww, hasFocus ->
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


        //Обработка нажатия на кнопку "Добавить"
        AddFoodBtn.setOnClickListener {

            val imm = RegistrationActivity_context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS)

            if (InputFood.text.toString() == "" || InputWeight.text.toString() == ""){
                Warning.text = "Введена не вся информация"
                Warning.visibility = View.VISIBLE
                Handler().postDelayed(Runnable {
                    Warning.visibility = View.INVISIBLE
                }, 5000)
            }
            else {
                var food = FoodModel(0, "0", 0, "0")

                for (i in listFoodAdvice){
                    if(i.Name == InputFood.text.toString()){
                        food = FoodModel(
                            i.Id,
                            i.Name,
                            i.Calories,
                            i.Category
                        )
                        break
                    }
                }

                food.Weight = InputWeight.text.toString().toInt()

                var check = 0
                for (i in listFood){
                    if(i.Name == food.Name){
                        check = 1
                        break
                    }
                }

                if(check == 0) {
                    listFood.add(food)
                    viewModelSave.addFoodData.value = food
                    recyclerView.adapter = FoodCardAdapter(listFood, RegistrationActivity_context)
                    SetInfo()
                }
                else{
                    Warning.text = "Такой продукт уже добавлен"
                    Warning.visibility = View.VISIBLE
                    Handler().postDelayed(Runnable {
                        Warning.visibility = View.INVISIBLE
                    }, 5000)
                }
            }
        }


        //Обрабока нажатия на кнопку "Добавить новую еду(плюс)"
        val AddFoodPlusBtn: ImageView = view.findViewById(R.id.AddFoodPlusBtn)
        AddFoodPlusBtn.setOnClickListener {

            CreateTravelBtn.visibility = View.INVISIBLE
            findNavController().navigate(R.id.action_foodListFragment_to_newFoodAddFragment)
        }
    }

    override fun onResume() {
        super.onResume()

        SetInfo()
    }

    fun SetInfo(){
        val InputDays: com.google.android.material.textfield.TextInputEditText = FirstAddFragment_view.findViewById(R.id.InputDays)
        val InfoCalories: TextView = FoodListFragment_view.findViewById(R.id.InfoCalories)
        val InfoWeight: TextView = FoodListFragment_view.findViewById(R.id.InfoWeight)

        if(InputDays.text.toString() == "") {
            InfoCalories.text = "Необходимая калорийность на\n" +
                    "0 дней для ${RegistrationActivity_context.listOfTravelers.size} человек: ${RegistrationActivity_context.CountNeededCalories()} ккал\n" +
                    "В наличии: ${RegistrationActivity_context.CountHavingCalories()} ккал"
        }
        else{
            InfoCalories.text = "Необходимая калорийность на\n" +
                    "${InputDays.text} дней для ${RegistrationActivity_context.listOfTravelers.size} человек:\n" +
                    "${RegistrationActivity_context.CountHavingCalories()} из ${RegistrationActivity_context.CountNeededCalories()} ккал"
        }

        if(InputDays.text.toString() == "") {
            InfoWeight.text = "Максимальный вес на\n" +
                    "0 дней для ${RegistrationActivity_context.listOfTravelers.size} человек:\n" +
                    "${RegistrationActivity_context.CountHavingWeight()} из ${RegistrationActivity_context.CountMaxWeight()} грамм"
        }
        else{
            InfoWeight.text = "Максимальный вес на\n" +
                    "${InputDays.text} дней для ${RegistrationActivity_context.listOfTravelers.size} человек:\n" +
                    "${RegistrationActivity_context.CountHavingWeight()} из ${RegistrationActivity_context.CountMaxWeight()} грамм"
        }
    }
}