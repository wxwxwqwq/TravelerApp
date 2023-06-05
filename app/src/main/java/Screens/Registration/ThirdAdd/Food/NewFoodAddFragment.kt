package Screens.Registration.ThirdAdd.Food

import DataModels.FoodModel
import ViewModels.ThirdAdd.FoodListViewModel
import ViewModels.ThirdAdd.NewFoodAddViewModel
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.travelapp.R
import com.example.travelapp.RegistrationActivity_context
import com.google.android.material.textfield.TextInputLayout
import com.leo.searchablespinner.SearchableSpinner
import com.leo.searchablespinner.interfaces.OnItemSelectListener


class NewFoodAddFragment : Fragment() {

    private lateinit var viewModel: NewFoodAddViewModel
    private lateinit var viewModelGet: FoodListViewModel

    var listFoodAdvice = mutableListOf<FoodModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_new_food_add, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRetainInstance(true)

        val CreateTravelBtn: Button = RegistrationActivity_context.findViewById(R.id.CreateTravelBtn)
        CreateTravelBtn.visibility = View.INVISIBLE

        val backBtn: ImageView = view.findViewById(R.id.BackBtn)
        val InputName: com.google.android.material.textfield.TextInputEditText = view.findViewById(R.id.InputName)
        val InputCalories: com.google.android.material.textfield.TextInputEditText = view.findViewById(R.id.InputCalories)
        val ChooseCategory: RadioGroup = view.findViewById(R.id.ChooseCategory)
        val Create: Button = view.findViewById(R.id.Create)
        val Warning: TextView = view.findViewById(R.id.Warning)
        val Delete: Button = view.findViewById(R.id.Delete)
        val DelFood: AutoCompleteTextView = view.findViewById(R.id.DelFood)
        val DelMes: TextView = view.findViewById(R.id.DelMes)


        viewModelGet = ViewModelProvider(RegistrationActivity_context).get(FoodListViewModel::class.java)

        viewModelGet.liveData.observe(activity as LifecycleOwner, Observer {
            listFoodAdvice.clear()
            for (i in it) {
                listFoodAdvice.add(i)
            }
        })

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


        //Обработка нажатия на кнопку "Добавить"
        Create.setOnClickListener{

            if (InputName.text.toString() == "" || InputCalories.text.toString() == "" || ChooseCategory.checkedRadioButtonId == -1){
                Warning.text = "Введена не вся информация"
                Warning.visibility = View.VISIBLE
                Handler().postDelayed(Runnable {
                    Warning.visibility = View.INVISIBLE
                }, 5000)
            }
            else {

                var check = 0
                for (i in listFoodAdvice){
                    if(i.Name == InputName.text.toString()){
                        check = 1
                        break
                    }
                }

                if(check == 0) {

                    val radioButtonID: Int = ChooseCategory.checkedRadioButtonId
                    val selectedRadioButton: RadioButton = view.findViewById(ChooseCategory.checkedRadioButtonId)
                    val selectedtext = selectedRadioButton.getText().toString()

                    viewModelGet.addFood(
                        InputName.text.toString(),
                        InputCalories.text.toString().toFloat(),
                        selectedtext
                    )

                    CreateTravelBtn.visibility = View.VISIBLE
                    findNavController().navigate(R.id.action_newFoodAddFragment_to_foodListFragment)
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

        //Обработка нажатия на кнопку "Удалить"
        Delete.setOnClickListener{

            if (DelFood.text.toString() == ""){
                DelMes.text = "Не выбран продукт"
                DelMes.setTextColor(Color.rgb(255, 0, 0))
                DelMes.visibility = View.VISIBLE
                Handler().postDelayed(Runnable {
                    DelMes.visibility = View.INVISIBLE
                }, 5000)
            }
            else {

                for (i in listFoodAdvice){

                    if(i.Name == DelFood.text.toString()){
                        viewModelGet.delFood(i.Id)
                        RegistrationActivity_context.DeleteFoodByIdFromDB(i.Id)
                        break
                    }
                }

                //Обновление данных в спинере продуктов после удаления
                DataList.clear()
                for (i in listFoodAdvice){
                    DataList.add(i.Name)
                }
                searchableSpinner.setSpinnerListItems(DataList)

                DelFood.setText("")

                DelMes.text = "Удалено"
                DelMes.setTextColor(Color.rgb(0, 0, 0))
                DelMes.visibility = View.VISIBLE
                Handler().postDelayed(Runnable {
                    DelMes.visibility = View.INVISIBLE
                }, 5000)

                //CreateTravelBtn.visibility = View.VISIBLE
                //findNavController().navigate(R.id.action_newFoodAddFragment_to_foodListFragment)
            }
        }

        //Обработка нажатия на кнопку "Назад"
        backBtn.setOnClickListener{

            CreateTravelBtn.visibility = View.VISIBLE
            findNavController().navigate(R.id.action_newFoodAddFragment_to_foodListFragment)
        }
    }
}