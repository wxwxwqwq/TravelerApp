package com.example.travelapp

import DataModels.*
import ViewModels.DataTransactionViewModel
import android.content.ContentValues
import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView


class RegistrationActivity : AppCompatActivity(), TravelerCardAdapter.Listener, FoodCardAdapter.Listener {

    lateinit var BottomNav: BottomNavigationView

    lateinit var adapter: RegistrationSwipeAdapter
    lateinit var viewPager: ViewPager2

    val listOfTravelers = mutableListOf<TravelerModel>()
    private val listOfFood = mutableListOf<FoodModel>()
    private val listOfDays = mutableListOf<DayMenuModel>()

    private lateinit var DataTransactionviewModel: DataTransactionViewModel



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        adapter = RegistrationSwipeAdapter(this)
        viewPager = findViewById(R.id.pager)
        viewPager.adapter = adapter

        RegistrationActivity_context = this

        DataTransactionviewModel = ViewModelProvider(MainActivity_context).get(DataTransactionViewModel::class.java)
    }



    override fun onStart() {
        super.onStart()

        //Установка экрана ViewPager2 в зависимости от BottomNavigationView
        BottomNav = findViewById(R.id.bottomNavigationView)
        BottomNav.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.firstAddFragment -> {
                    viewPager.currentItem = 0
                }
                R.id.secondAddFragment -> {
                    viewPager.currentItem = 1
                }
                R.id.thirdAddFragment -> {
                    viewPager.currentItem = 2
                }
            }
            true
        }

        //Установка экрана BottomNavigationView в зависимости от ViewPager2
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                when(position){
                    0 -> {
                        BottomNav.menu.findItem(R.id.firstAddFragment).isChecked = true
                    }
                    1 -> {
                        BottomNav.menu.findItem(R.id.secondAddFragment).isChecked = true
                    }
                    2 -> {
                        BottomNav.menu.findItem(R.id.thirdAddFragment).isChecked = true
                    }
                }
            }
        })


        //Обрабока нажатия на кнопку "Создать поход"
        val CreateTravelBtn: Button = findViewById(R.id.CreateTravelBtn)
        CreateTravelBtn.setOnClickListener{

            val InputTitle: com.google.android.material.textfield.TextInputEditText = FirstAddFragment_view.findViewById(R.id.InputTitle)

            if(InputTitle.text.toString() == ""){
                val TitleWarning: TextView = FirstAddFragment_view.findViewById(R.id.TitleWarning)
                TitleWarning.visibility = View.VISIBLE
                Handler().postDelayed(Runnable {
                    TitleWarning.visibility = View.INVISIBLE
                }, 5000)

                viewPager.currentItem = 0
                BottomNav.menu.findItem(R.id.firstAddFragment).isChecked = true
            }
            else {

                var days = 0
                val InputDays: com.google.android.material.textfield.TextInputEditText = FirstAddFragment_view.findViewById(R.id.InputDays)
                if (InputDays.text.toString() != ""){
                    days = InputDays.text.toString().toInt()
                }

                //var mess = "В вашем походе есть следующие недостатки:\n"
//
                //if(CountHavingCalories() < (CountNeededCalories() - 500 * listOfTravelers.size).toDouble()){
                //    mess += "•Малая калорийность списка продуктов для ${listOfTravelers.size} человек на ${days} дней\n"
                //}
//
                //if(CountHavingWeight() > (CountMaxWeight() + 500 * listOfTravelers.size)){
                //    mess += "•Слишком большой вес продуктов для ${listOfTravelers.size} человек на ${days} дней\n"
                //}
//
                //var weight = 0
                //for(i in listOfFood){
                //    if(i.Category == "Основной продукт"){
                //        weight += i.Weight
                //    }
                //}
//
                //if(weight == 0){
                //    mess += "В списке продуктов нет основного продукта\n"
                //}
                //else if(weight < 150 * 2 * listOfTravelers.size * days){
                //    mess += "Количество основного продукта недостаточно для похода\n"
                //}
//
                //if(mess != "В вашем походе есть следующие недостатки:\n"){
                //    val builder = AlertDialog.Builder(this)
                //    mess += "Продолжить?"
                //    builder.setMessage(mess)
//
                //    builder.setNegativeButton("Нет"){ dialog, which ->
//
                //    }
                //    builder.setPositiveButton("Да") { dialog, which ->
//
                //        CreateTravel(InputTitle, days)
                //        val intent = Intent(this, MainActivity::class.java)
                //        startActivity(intent)
                //    }
                //    builder.show()
                //}
                //else{
//
                //    CreateTravel(InputTitle, days)
                //    val intent = Intent(this, MainActivity::class.java)
                //    startActivity(intent)
                //}


                //for (i in 1 .. 2000){
                //    listOfTravelers.add(TravelerModel(
                //        "$i",
                //        "$i",
                //        "Муж.",
                //        "Нет"
                //    ))
                //}

                CreateTravel(InputTitle, days)
                //if(intent==null) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                //}
            }
        }


        //Получение данных о добавленных туристах из фрагментов
        DataTransactionviewModel.addTravelerData.observe(this, Observer {
            listOfTravelers.add(it)
        })

        //Передача списка добавленных туристов фрагментам
        DataTransactionviewModel.getTravelersData.value = listOfTravelers

        //Получение изменённых данных о туристах из фрагментов
        DataTransactionviewModel.changeTravelersData.observe(this, Observer {
            listOfTravelers.clear()
            listOfTravelers.addAll(it)
        })


        //Получение созданного списка приёмов пищи
        DataTransactionviewModel.addCreatedDaysMenuToRegistrationList.observe(this, Observer {
            listOfDays.clear()
            listOfDays.addAll(it)
        })

        DataTransactionviewModel.getDaysData.value = listOfDays


    }



    override fun onDestroy() {
        super.onDestroy()
        viewPager.unregisterOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                when(position){
                    0 -> {
                        BottomNav.menu.findItem(R.id.firstAddFragment).isChecked = true
                    }
                    1 -> {
                        BottomNav.menu.findItem(R.id.secondAddFragment).isChecked = true
                    }
                    2 -> {
                        BottomNav.menu.findItem(R.id.thirdAddFragment).isChecked = true
                    }
                }
            }
        })
    }



    //Функция для создания похода и добавления данных, введённых пользователем, в БД
    fun CreateTravel(InputTitle: com.google.android.material.textfield.TextInputEditText, days: Int){

        val dbHelper = DBHelper(this)
        val database = dbHelper.writableDatabase

        //database.delete("Travel", "id = 2", null)
        var cursor: Cursor
        var contentValues = ContentValues()
        //contentValues.put("seq", 0)
        //database.update("sqlite_sequence", contentValues, "name='Travel'", null)





        //Добавление данных о походе в БД и получение id похода
        contentValues.put("name", InputTitle.text.toString())
        contentValues.put("days", days.toFloat())
        database.insert("Travel", null, contentValues)
        cursor = database.query("sqlite_sequence", null, "name='Travel'", null, null, null, null)
        cursor.moveToFirst()
        val TravelId = cursor.getString(cursor.getColumnIndex("seq")).toInt()
        cursor.close()






        //Расчёт максимального веса и добавление туристов в БД
        val listOfStrongTravelers = mutableListOf<TravelerModel>()
        val listOfWeakTravelers = mutableListOf<TravelerModel>()
        var extra_weight = 0

        //Разделение на "сильных" и "слабых" туристов
        for (i in listOfTravelers){
            if(i.Age.toInt() in 18 .. 60 && i.Restrictions == "Нет"){
                listOfStrongTravelers.add(i)
            }
            else {
                listOfWeakTravelers.add(i)
            }
        }

        //Расширение границ "сильных" туристов
        if (listOfStrongTravelers.size == 0){
            for (i in listOfTravelers){
                if(i.Age.toInt() in 14 .. 60 && i.Restrictions == "Нет"){
                    listOfStrongTravelers.add(i)
                    listOfWeakTravelers.remove(i)
                }
            }
        }

        //Просматриваем список "слабых" туристов, чтобы расчитать нагрузку
        for (i in listOfWeakTravelers){

            contentValues = ContentValues()
            contentValues.put("name", i.TravelerName)
            contentValues.put("gender", i.Gender)
            contentValues.put("age", i.Age.toInt())
            contentValues.put("restrictions", i.Restrictions)
            contentValues.put("carrying_weight", 0)

            var max_weight: Int = (CountMaxWeight() / listOfTravelers.size) + 20

            //Процесс уменьшения максимальной нагрузки, если "сильные" туристы есть"
            if (listOfStrongTravelers.size > 0) {

                if (i.Gender == "Жен."){
                    extra_weight += (max_weight * 0.4).toInt()
                    max_weight = (max_weight * 0.6).toInt()
                }

                if (i.Age.toInt() < 10){
                    extra_weight += max_weight
                    max_weight = 0
                }

                if (i.Age.toInt() in 10 .. 13){
                    extra_weight += (max_weight * 0.6).toInt()
                    max_weight = (max_weight * 0.4).toInt()
                }

                if (i.Age.toInt() in 14 .. 17){
                    extra_weight += (max_weight * 0.4).toInt()
                    max_weight = (max_weight * 0.6).toInt()
                }

                if (i.Age.toInt() > 60){
                    extra_weight += (max_weight * 0.2).toInt()
                    max_weight = (max_weight * 0.8).toInt()
                }

                if(i.Restrictions == "Да"){
                    extra_weight += (max_weight * 0.6).toInt()
                    max_weight = (max_weight * 0.4).toInt()
                }
            }

            //Запись полученного значения максимальной нагрузки, а также id похода
            contentValues.put("max_weight", max_weight)
            i.MaxWeight = max_weight
            contentValues.put("travel_id", TravelId)

            database.insert("Travelers_in_travel", null, contentValues)

            cursor = database.query("sqlite_sequence", null, "name='Travelers_in_travel'", null, null, null, null)
            cursor.moveToFirst()
            i.Id = cursor.getString(cursor.getColumnIndex("seq")).toInt()
            cursor.close()
            //listOfTravelersId.add(cursor.getString(cursor.getColumnIndex("seq")).toInt())
        }

        //Распределений уменьшеной нагрузки, если "сильные" туристы есть
        if(listOfStrongTravelers.size > 0) {

            //Обработка уменьшения веса у "сильных" женцин по сравнению с "сильными" мужчинами
            for (i in listOfStrongTravelers) {
                if (i.Gender == "Жен.") {
                    extra_weight += (((CountMaxWeight() / listOfTravelers.size) + 20) * 0.4).toInt()
                }
            }

            //Обработка распределения веса между "сильными" участниками похода
            val onePart: Int = extra_weight / listOfStrongTravelers.size

            for (i in listOfStrongTravelers) {
                contentValues = ContentValues()
                contentValues.put("name", i.TravelerName)
                contentValues.put("gender", i.Gender)
                contentValues.put("age", i.Age.toInt())
                contentValues.put("restrictions", i.Restrictions)
                contentValues.put("carrying_weight", 0)

                var max_weight: Int = (CountMaxWeight() / listOfTravelers.size) + 20

                if (i.Gender == "Жен.") {
                    max_weight = (max_weight * 0.6).toInt()
                }

                max_weight += onePart
                extra_weight -= onePart

                contentValues.put("max_weight", max_weight)
                i.MaxWeight = max_weight
                contentValues.put("travel_id", TravelId)

                database.insert("Travelers_in_travel", null, contentValues)

                cursor = database.query("sqlite_sequence", null, "name='Travelers_in_travel'", null, null, null, null)
                cursor.moveToFirst()
                i.Id = cursor.getString(cursor.getColumnIndex("seq")).toInt()
                cursor.close()
            }
        }



        //for(i in listOfTravelersId){
        //    contentValues = ContentValues()
        //    contentValues.put("traveler_id", i)
        //    contentValues.put("travel_id", TravelId)
        //    database.insert("Travelers_in_travel", null, contentValues)
        //}

        ////Добавление в поход выбранной еды
        //for(i in listOfFood){
        //    contentValues = ContentValues()
        //    contentValues.put("food_id", i.Id)
        //    contentValues.put("travel_id", TravelId)
        //    contentValues.put("weight_of_food", i.Weight)
        //    database.insert("Food_in_travel", null, contentValues)
        //}







        //Добавление в поход составленного меню
        for(i in listOfDays){

            for(j in i.listOfMeals){

                if(j.dish.size != 0) {
                    contentValues = ContentValues()
                    contentValues.put("day", i.day_number)
                    contentValues.put("travel_id", TravelId)
                    contentValues.put("dish_id", j.dish[0].id)
                    contentValues.put(
                        "weight_of_dish",
                        j.dish[0].portion_weight * listOfTravelers.size
                    )

                    when (j.type_of_meal) {
                        "Завтрак" -> database.insert("Breakfast", null, contentValues)
                        "Обед" -> database.insert("Dinner", null, contentValues)
                        "Ужин" -> database.insert("Supper", null, contentValues)
                    }
                }
            }
        }







        //Распределение продуктов между туристами
        val CarriersGroup1 = mutableListOf<TravelerModel>()
        val CarriersGroup2 = mutableListOf<TravelerModel>()
        val CarriersGroup3 = mutableListOf<TravelerModel>()

        //Отделение детей от остальных туристов
        val listForDistribution = mutableListOf<TravelerModel>()
        listOfTravelers.forEach {
            if(it.MaxWeight > 0) {
                listForDistribution.add(it)
            }
        }

        //Разделение на группы
        if(listForDistribution.size < 3){
            CarriersGroup1.addAll(listForDistribution)
            CarriersGroup2.addAll(listForDistribution)
            CarriersGroup3.addAll(listForDistribution)
        }
        else{
            var counter = 3
            for (i in listForDistribution){

                if(counter == 3){

                    CarriersGroup3.add(i)

                    contentValues = ContentValues()
                    contentValues.put("group_num", 3)
                    i.Group = 3
                    database.update("Travelers_in_travel", contentValues, "id = ${i.Id}", null)

                    counter--

                }
                else if (counter == 2) {

                    CarriersGroup2.add(i)

                    contentValues = ContentValues()
                    contentValues.put("group_num", 2)
                    i.Group = 2
                    database.update("Travelers_in_travel", contentValues, "id = ${i.Id}", null)

                    counter--

                }
                else if(counter == 1){

                    CarriersGroup1.add(i)

                    contentValues = ContentValues()
                    contentValues.put("group_num", 1)
                    i.Group = 1
                    database.update("Travelers_in_travel", contentValues, "id = ${i.Id}", null)

                    counter = 3
                }

            }
        }



        //val listOfDestridution = mutableListOf<Travelers_ingredientsModel>()



        //Обход дней меню и раздача ингредиентов
        for(i in listOfDays.size - 1 downTo 0){

            val BreakfastIngredients = mutableListOf<IngredientModel>()
            val DinnerIngredients = mutableListOf<IngredientModel>()
            val SupperIngredients = mutableListOf<IngredientModel>()

            //Обход 3 приёмов пищи в дне и раздача продуктов
            for(meal in listOfDays[i].listOfMeals){

                if(meal.dish.size > 0) {

                    when (meal.type_of_meal) {
                        "Завтрак" -> {

                            BreakfastIngredients.addAll(meal.dish[0].listOfIngredients)
                            BreakfastIngredients.forEach { it.weight *= listOfTravelers.size }
                            BreakfastIngredients.sortByDescending { it.weight }

                            while (BreakfastIngredients.size > 0 && CarriersGroup1.size > 0) {

                                CarriersGroup1.sortBy { it.AllCarryingWeight }

                                var divider = 500

                                if(CarriersGroup1[0].MaxWeight - CarriersGroup1[0].AllCarryingWeight < 500){
                                    divider = CarriersGroup1[0].MaxWeight - CarriersGroup1[0].AllCarryingWeight
                                }

                                if(BreakfastIngredients[0].weight > divider){
                                    CarriersGroup1[0].AllCarryingWeight += divider
                                    BreakfastIngredients[0].weight -= divider

                                    contentValues = ContentValues()
                                    contentValues.put("carrying_weight", CarriersGroup1[0].AllCarryingWeight)

                                    database.update("Travelers_in_travel", contentValues, "id = ${CarriersGroup1[0].Id}", null)

                                    contentValues = ContentValues()
                                    contentValues.put("day", listOfDays[i].day_number)
                                    contentValues.put("traveler_id", CarriersGroup1[0].Id)
                                    contentValues.put("ingredient_id", BreakfastIngredients[0].id)
                                    contentValues.put("type_of_meal", "Breakfast")
                                    contentValues.put("weight_of_food", divider)

                                    database.insert("Traveler_ingredients", null, contentValues)
                                }
                                else{
                                    CarriersGroup1[0].AllCarryingWeight += BreakfastIngredients[0].weight

                                    contentValues = ContentValues()
                                    contentValues.put("carrying_weight", CarriersGroup1[0].AllCarryingWeight)

                                    database.update("Travelers_in_travel", contentValues, "id = ${CarriersGroup1[0].Id}", null)

                                    contentValues = ContentValues()
                                    contentValues.put("day", listOfDays[i].day_number)
                                    contentValues.put("traveler_id", CarriersGroup1[0].Id)
                                    contentValues.put("ingredient_id", BreakfastIngredients[0].id)
                                    contentValues.put("type_of_meal", "Breakfast")
                                    contentValues.put("weight_of_food", BreakfastIngredients[0].weight)

                                    database.insert("Traveler_ingredients", null, contentValues)

                                    BreakfastIngredients.removeAt(0)
                                }

                                if(CarriersGroup1[0].AllCarryingWeight >= CarriersGroup1[0].MaxWeight - 50){

                                    CarriersGroup1.removeAt(0)
                                }
                            }
                        }
                        "Обед" -> {

                            DinnerIngredients.addAll(meal.dish[0].listOfIngredients)
                            DinnerIngredients.forEach { it.weight *= listOfTravelers.size }
                            DinnerIngredients.sortByDescending { it.weight }

                            while (DinnerIngredients.size > 0 && CarriersGroup2.size > 0) {

                                CarriersGroup2.sortBy { it.AllCarryingWeight }

                                var divider = 500

                                if(CarriersGroup2[0].MaxWeight - CarriersGroup2[0].AllCarryingWeight < 500){
                                    divider = CarriersGroup2[0].MaxWeight - CarriersGroup2[0].AllCarryingWeight
                                }

                                if(DinnerIngredients[0].weight > divider){
                                    CarriersGroup2[0].AllCarryingWeight += divider
                                    DinnerIngredients[0].weight -= divider

                                    contentValues = ContentValues()
                                    contentValues.put("carrying_weight", CarriersGroup2[0].AllCarryingWeight)

                                    database.update("Travelers_in_travel", contentValues, "id = ${CarriersGroup2[0].Id}", null)

                                    contentValues = ContentValues()
                                    contentValues.put("day", listOfDays[i].day_number)
                                    contentValues.put("traveler_id", CarriersGroup2[0].Id)
                                    contentValues.put("ingredient_id", DinnerIngredients[0].id)
                                    contentValues.put("type_of_meal", "Dinner")
                                    contentValues.put("weight_of_food", divider)

                                    database.insert("Traveler_ingredients", null, contentValues)
                                }
                                else{
                                    CarriersGroup2[0].AllCarryingWeight += DinnerIngredients[0].weight

                                    contentValues = ContentValues()
                                    contentValues.put("carrying_weight", CarriersGroup2[0].AllCarryingWeight)

                                    database.update("Travelers_in_travel", contentValues, "id = ${CarriersGroup2[0].Id}", null)

                                    contentValues = ContentValues()
                                    contentValues.put("day", listOfDays[i].day_number)
                                    contentValues.put("traveler_id", CarriersGroup2[0].Id)
                                    contentValues.put("ingredient_id", DinnerIngredients[0].id)
                                    contentValues.put("type_of_meal", "Dinner")
                                    contentValues.put("weight_of_food", DinnerIngredients[0].weight)

                                    database.insert("Traveler_ingredients", null, contentValues)

                                    DinnerIngredients.removeAt(0)
                                }

                                if(CarriersGroup2[0].AllCarryingWeight >= CarriersGroup2[0].MaxWeight - 50){

                                    CarriersGroup2.removeAt(0)
                                }
                            }
                        }
                        "Ужин" -> {

                            SupperIngredients.addAll(meal.dish[0].listOfIngredients)
                            SupperIngredients.forEach { it.weight *= listOfTravelers.size }
                            SupperIngredients.sortByDescending { it.weight }

                            while (SupperIngredients.size > 0 && CarriersGroup3.size > 0) {

                                CarriersGroup3.sortBy { it.AllCarryingWeight }

                                var divider = 500

                                if(CarriersGroup3[0].MaxWeight - CarriersGroup3[0].AllCarryingWeight < 500){
                                    divider = CarriersGroup3[0].MaxWeight - CarriersGroup3[0].AllCarryingWeight
                                }

                                if(SupperIngredients[0].weight > divider){
                                    CarriersGroup3[0].AllCarryingWeight += divider
                                    SupperIngredients[0].weight -= divider

                                    contentValues = ContentValues()
                                    contentValues.put("carrying_weight", CarriersGroup3[0].AllCarryingWeight)

                                    database.update("Travelers_in_travel", contentValues, "id = ${CarriersGroup3[0].Id}", null)

                                    contentValues = ContentValues()
                                    contentValues.put("day", listOfDays[i].day_number)
                                    contentValues.put("traveler_id", CarriersGroup3[0].Id)
                                    contentValues.put("ingredient_id", SupperIngredients[0].id)
                                    contentValues.put("type_of_meal", "Supper")
                                    contentValues.put("weight_of_food", divider)

                                    database.insert("Traveler_ingredients", null, contentValues)
                                }
                                else{
                                    CarriersGroup3[0].AllCarryingWeight += SupperIngredients[0].weight

                                    contentValues = ContentValues()
                                    contentValues.put("carrying_weight", CarriersGroup3[0].AllCarryingWeight)

                                    database.update("Travelers_in_travel", contentValues, "id = ${CarriersGroup3[0].Id}", null)

                                    contentValues = ContentValues()
                                    contentValues.put("day", listOfDays[i].day_number)
                                    contentValues.put("traveler_id", CarriersGroup3[0].Id)
                                    contentValues.put("ingredient_id", SupperIngredients[0].id)
                                    contentValues.put("type_of_meal", "Supper")
                                    contentValues.put("weight_of_food", SupperIngredients[0].weight)

                                    database.insert("Traveler_ingredients", null, contentValues)

                                    SupperIngredients.removeAt(0)
                                }

                                if(CarriersGroup3[0].AllCarryingWeight >= CarriersGroup3[0].MaxWeight - 50){

                                    CarriersGroup3.removeAt(0)
                                }
                            }
                        }
                    }
                    //Подготовка списка распределяемых ингредиентов


                    //meal.dish[0].listOfIngredients.forEach {
//
                    //    //listOfDestridution.add(
                    //    //    Travelers_ingredientsModel(
                    //    //        listOfDays[i].day_number,
                    //    //        listForDistribution[0].Id,
                    //    //        it.id,
                    //    //        meal.type_of_meal,
                    //    //        it.weight
                    //    //    )
                    //    //)
//
                    //    contentValues = ContentValues()
                    //    contentValues.put("day", listOfDays[i].day_number)
                    //    contentValues.put("traveler_id", listForDistribution[0].Id)
                    //    contentValues.put("ingredient_id", it.id)
                    //    contentValues.put("type_of_meal", meal.type_of_meal)
                    //    contentValues.put("weight_of_food", it.weight)
//
                    //    database.insert("Traveler_ingredients", null, contentValues)
//
                    //}



                    //if(CarriersGroup1.size > 0 && BreakfastIngredients.size > 0) {
//
                    //    val (ingredients, carriers) = Distribution(
                    //        BreakfastIngredients,
                    //        CarriersGroup1,
                    //        listOfDays[i].day_number,
                    //        meal.type_of_meal
                    //    )
//
                    //    BreakfastIngredients.clear()
                    //    BreakfastIngredients.addAll(ingredients)
//
                    //    CarriersGroup1.clear()
                    //    CarriersGroup1.addAll(carriers)
                    //}





                }







                //while (BreakfastIngredients.size > 0 && CarriersGroup1.size > 0) {
//
                //    //Подсчитываем общий вес ингредиентов для распределения
                //    var weightOfIngredients = 0
                //    BreakfastIngredients.forEach {  weightOfIngredients += it.weight }
//
                //    //Определение максимального веса ингредиентов на рассматриваемый приём пищи для одного туриста из группы транспортиповки
                //    val MealCarriersGroup = mutableListOf<TravelerModel>()
                //    MealCarriersGroup.addAll(CarriersGroup1)
//
                //    MealCarriersGroup.forEach{
//
                //        it.MealMaxCarryingWeight = (weightOfIngredients / MealCarriersGroup.size) + 50
//
                //        if(it.MaxWeight - it.AllCarryingWeight < it.MealMaxCarryingWeight){
//
                //            it.MealMaxCarryingWeight = it.MaxWeight - it.AllCarryingWeight
                //        }
                //    }
//
                //    //Распределяем ингредиенты из списка пока не кончатся ингредиенты или место у группы транспортировки
                //    while(BreakfastIngredients.size > 0 && MealCarriersGroup.size > 0){
//
                //        //Сортировка списка туристов в группе и списка ингредиентов, чтобы туристу с наибольшим свободным местом выдавался продукт с наибольшим весом
                //        BreakfastIngredients.sortByDescending { it.weight }
                //        MealCarriersGroup.sortByDescending { it.MealMaxCarryingWeight - it.MealCarryingWeight  }
//
                //        //Проверка, есть ли у туриста свободное место, для распределения
                //        if(MealCarriersGroup[0].MealCarryingWeight < MealCarriersGroup[0].MealMaxCarryingWeight - 50){
//
                //            val AvailableWeight : Int = MealCarriersGroup[0].MealMaxCarryingWeight - MealCarriersGroup[0].MealCarryingWeight
//
                //            //Распределение, если масса продукта больше, чем свободное место у туриста
                //            if(BreakfastIngredients[0].weight > AvailableWeight){
//
                //                MealCarriersGroup[0].MealCarryingWeight += AvailableWeight
                //                MealCarriersGroup[0].AllCarryingWeight += AvailableWeight
                //                BreakfastIngredients[0].weight -= AvailableWeight

                                //contentValues = ContentValues()
                                //contentValues.put("carrying_weight", MealCarriersGroup[0].AllCarryingWeight)

                                //database.update("Travelers_in_travel", contentValues, "id = ${MealCarriersGroup[0].Id}", null)

                                //contentValues = ContentValues()
                                //contentValues.put("day", listOfDays[i].day_number)
                                //contentValues.put("traveler_id", MealCarriersGroup[0].Id)
                                //contentValues.put("ingredient_id", BreakfastIngredients[0].id)
                                //contentValues.put("type_of_meal", "Breakfast")
                                //contentValues.put("weight_of_food", AvailableWeight)
//
                                //database.insert("Traveler_ingredients", null, contentValues)

                //                listOfDestridution.add(
                //                    Travelers_ingredientsModel(
                //                        listOfDays[i].day_number,
                //                        MealCarriersGroup[0].Id,
                //                        BreakfastIngredients[0].id,
                //                        "Breakfast",
                //                        AvailableWeight
                //                    ))
//
//
                //            }
                //            //Распределение, если масса продукта меньше или равно свободному месту у туриста
                //            else{
                //                MealCarriersGroup[0].MealCarryingWeight += BreakfastIngredients[0].weight
                //                MealCarriersGroup[0].AllCarryingWeight += BreakfastIngredients[0].weight

                                //contentValues = ContentValues()
                                //contentValues.put("carrying_weight", MealCarriersGroup[0].AllCarryingWeight)
//
                                //database.update("Travelers_in_travel", contentValues, "id = ${MealCarriersGroup[0].Id}", null)
//
                                //contentValues = ContentValues()
                                //contentValues.put("day", listOfDays[i].day_number)
                                //contentValues.put("traveler_id", MealCarriersGroup[0].Id)
                                //contentValues.put("ingredient_id", BreakfastIngredients[0].id)
                                //contentValues.put("type_of_meal", "Breakfast")

                                //when(meal.type_of_meal) {
                                //    "Звтрак" -> contentValues.put("type_of_meal", "Breakfast")
                                //    "Обед" -> contentValues.put("type_of_meal", "Dinner")
                                //    "Ужин" -> contentValues.put("type_of_meal", "Supper")
                                //}
                                //contentValues.put("weight_of_food", BreakfastIngredients[0].weight)
//
                                //database.insert("Traveler_ingredients", null, contentValues)

                //                listOfDestridution.add(
                //                    Travelers_ingredientsModel(
                //                        listOfDays[i].day_number,
                //                        MealCarriersGroup[0].Id,
                //                        BreakfastIngredients[0].id,
                //                        "Breakfast",
                //                        AvailableWeight
                //                    ))

                //                BreakfastIngredients.removeAt(0)
                //            }
                //        }

                //        val traveler = MealCarriersGroup[0]

                //        //Проверка, остались ли у туриста свободное место на данный приём пищи
                //        if(MealCarriersGroup[0].MealCarryingWeight >= MealCarriersGroup[0].MealMaxCarryingWeight - 50){
                //            MealCarriersGroup.removeAt(0)
                //        }

                        //Проверка, остались ли у туриста вообще свободное место для распределения
                //        if(traveler.AllCarryingWeight >= traveler.MaxWeight - 50){
                //            CarriersGroup1.remove(traveler)
                //        }
                //    }
//
                //    MealCarriersGroup.forEach {
//
                //        it.MealMaxCarryingWeight = 0
                //        it.MealCarryingWeight = 0
                //    }
                //}






            }

            //Дораспределение продуктов
            if(BreakfastIngredients.size > 0){

                val list = mutableListOf<TravelerModel>()
                list.addAll(CarriersGroup3)
                list.addAll(CarriersGroup2)

                BreakfastIngredients.sortByDescending { it.weight }

                while (BreakfastIngredients.size > 0){

                    list.sortBy { it.AllCarryingWeight }

                    var devider = 500

                    if(list[0].MaxWeight - list[0].AllCarryingWeight < 500){
                        devider = list[0].MaxWeight - list[0].AllCarryingWeight
                    }

                    if(BreakfastIngredients[0].weight > devider){
                        list[0].AllCarryingWeight += devider
                        BreakfastIngredients[0].weight -= devider

                        contentValues = ContentValues()
                        contentValues.put("carrying_weight", list[0].AllCarryingWeight)

                        database.update("Travelers_in_travel", contentValues, "id = ${list[0].Id}", null)

                        contentValues = ContentValues()
                        contentValues.put("day", listOfDays[i].day_number)
                        contentValues.put("traveler_id", list[0].Id)
                        contentValues.put("ingredient_id", BreakfastIngredients[0].id)
                        contentValues.put("type_of_meal", "Breakfast")
                        contentValues.put("weight_of_food", devider)

                        database.insert("Traveler_ingredients", null, contentValues)
                    }
                    else{
                        list[0].AllCarryingWeight += BreakfastIngredients[0].weight

                        contentValues = ContentValues()
                        contentValues.put("carrying_weight", list[0].AllCarryingWeight)

                        database.update("Travelers_in_travel", contentValues, "id = ${list[0].Id}", null)

                        contentValues = ContentValues()
                        contentValues.put("day", listOfDays[i].day_number)
                        contentValues.put("traveler_id", list[0].Id)
                        contentValues.put("ingredient_id", BreakfastIngredients[0].id)
                        contentValues.put("type_of_meal", "Breakfast")
                        contentValues.put("weight_of_food", BreakfastIngredients[0].weight)

                        database.insert("Traveler_ingredients", null, contentValues)

                        BreakfastIngredients.removeAt(0)
                    }

                    if(list[0].AllCarryingWeight >= list[0].MaxWeight - 50){
                        if(list[0].Group == 2) {
                            CarriersGroup2.remove(list[0])
                        }
                        else {
                            CarriersGroup3.remove(list[0])
                        }

                        list.removeAt(0)
                    }
                }
            }

            if(DinnerIngredients.size > 0){

                val list = mutableListOf<TravelerModel>()
                list.addAll(CarriersGroup3)
                list.addAll(CarriersGroup1)

                DinnerIngredients.sortByDescending { it.weight }

                while (DinnerIngredients.size > 0){

                    list.sortBy { it.AllCarryingWeight }

                    var devider = 500

                    if(list[0].MaxWeight - list[0].AllCarryingWeight < 500){
                        devider = list[0].MaxWeight - list[0].AllCarryingWeight
                    }

                    if(DinnerIngredients[0].weight > devider){

                        list[0].AllCarryingWeight += devider
                        DinnerIngredients[0].weight -= devider

                        contentValues = ContentValues()
                        contentValues.put("carrying_weight", list[0].AllCarryingWeight)

                        database.update("Travelers_in_travel", contentValues, "id = ${list[0].Id}", null)

                        contentValues = ContentValues()
                        contentValues.put("day", listOfDays[i].day_number)
                        contentValues.put("traveler_id", list[0].Id)
                        contentValues.put("ingredient_id", DinnerIngredients[0].id)
                        contentValues.put("type_of_meal", "Dinner")
                        contentValues.put("weight_of_food", devider)

                        database.insert("Traveler_ingredients", null, contentValues)
                    }
                    else{
                        list[0].AllCarryingWeight += DinnerIngredients[0].weight

                        contentValues = ContentValues()
                        contentValues.put("carrying_weight", list[0].AllCarryingWeight)

                        database.update("Travelers_in_travel", contentValues, "id = ${list[0].Id}", null)

                        contentValues = ContentValues()
                        contentValues.put("day", listOfDays[i].day_number)
                        contentValues.put("traveler_id", list[0].Id)
                        contentValues.put("ingredient_id", DinnerIngredients[0].id)
                        contentValues.put("type_of_meal", "Dinner")
                        contentValues.put("weight_of_food", DinnerIngredients[0].weight)

                        database.insert("Traveler_ingredients", null, contentValues)

                        DinnerIngredients.removeAt(0)
                    }

                    if(list[0].AllCarryingWeight >= list[0].MaxWeight - 50){
                        if(list[0].Group == 1) {
                            CarriersGroup1.remove(list[0])
                        }
                        else {
                            CarriersGroup3.remove(list[0])
                        }

                        list.removeAt(0)
                    }
                }
            }

            if(SupperIngredients.size > 0){

                val list = mutableListOf<TravelerModel>()
                list.addAll(CarriersGroup2)
                list.addAll(CarriersGroup1)

                SupperIngredients.sortByDescending { it.weight }

                while (SupperIngredients.size > 0){

                    list.sortBy { it.AllCarryingWeight }

                    var devider = 500

                    if(list[0].MaxWeight - list[0].AllCarryingWeight < 500){
                        devider = list[0].MaxWeight - list[0].AllCarryingWeight
                    }

                    if(SupperIngredients[0].weight > devider){

                        list[0].AllCarryingWeight += devider
                        SupperIngredients[0].weight -= devider

                        contentValues = ContentValues()
                        contentValues.put("carrying_weight", list[0].AllCarryingWeight)

                        database.update("Travelers_in_travel", contentValues, "id = ${list[0].Id}", null)

                        contentValues = ContentValues()
                        contentValues.put("day", listOfDays[i].day_number)
                        contentValues.put("traveler_id", list[0].Id)
                        contentValues.put("ingredient_id", SupperIngredients[0].id)
                        contentValues.put("type_of_meal", "Supper")
                        contentValues.put("weight_of_food", devider)

                        database.insert("Traveler_ingredients", null, contentValues)
                    }
                    else{
                        list[0].AllCarryingWeight += SupperIngredients[0].weight

                        contentValues = ContentValues()
                        contentValues.put("carrying_weight", list[0].AllCarryingWeight)

                        database.update("Travelers_in_travel", contentValues, "id = ${list[0].Id}", null)

                        contentValues = ContentValues()
                        contentValues.put("day", listOfDays[i].day_number)
                        contentValues.put("traveler_id", list[0].Id)
                        contentValues.put("ingredient_id", SupperIngredients[0].id)
                        contentValues.put("type_of_meal", "Supper")
                        contentValues.put("weight_of_food", SupperIngredients[0].weight)

                        database.insert("Traveler_ingredients", null, contentValues)

                        SupperIngredients.removeAt(0)
                    }

                    if(list[0].AllCarryingWeight >= list[0].MaxWeight - 50){
                        if(list[0].Group == 1) {
                            CarriersGroup1.remove(list[0])
                        }
                        else {
                            CarriersGroup2.remove(list[0])
                        }

                        list.removeAt(0)
                    }
                }
            }

        }
    }






    fun Distribution ( ingredients : MutableList<IngredientModel>, carriers : MutableList<TravelerModel>, day : Int, type: String): Pair<MutableList<IngredientModel>, MutableList<TravelerModel>> {

        while (ingredients.size > 0 && carriers.size > 0) {

            carriers.sortBy { it.AllCarryingWeight }

            var divider = 500

            if(carriers[0].MaxWeight - carriers[0].AllCarryingWeight < 500){
                divider = carriers[0].MaxWeight - carriers[0].AllCarryingWeight
            }

            val dbHelper = DBHelper(this)
            val database = dbHelper.writableDatabase
            var contentValues: ContentValues

            if(ingredients[0].weight > divider){
                carriers[0].AllCarryingWeight += divider
                ingredients[0].weight -= divider

                contentValues = ContentValues()
                contentValues.put("carrying_weight", carriers[0].AllCarryingWeight)

                database.update("Travelers_in_travel", contentValues, "id = ${carriers[0].Id}", null)

                contentValues = ContentValues()
                contentValues.put("day", day)
                contentValues.put("traveler_id", carriers[0].Id)
                contentValues.put("ingredient_id", ingredients[0].id)
                contentValues.put("type_of_meal", type)
                contentValues.put("weight_of_food", divider)

                database.insert("Traveler_ingredients", null, contentValues)
            }
            else{
                carriers[0].AllCarryingWeight += ingredients[0].weight

                contentValues = ContentValues()
                contentValues.put("carrying_weight", carriers[0].AllCarryingWeight)

                database.update("Travelers_in_travel", contentValues, "id = ${carriers[0].Id}", null)

                contentValues = ContentValues()
                contentValues.put("day", day)
                contentValues.put("traveler_id", carriers[0].Id)
                contentValues.put("ingredient_id", ingredients[0].id)
                contentValues.put("type_of_meal", type)
                contentValues.put("weight_of_food", ingredients[0].weight)

                database.insert("Traveler_ingredients", null, contentValues)

                ingredients.removeAt(0)
            }

            if(carriers[0].AllCarryingWeight >= carriers[0].MaxWeight - 50){

                carriers.removeAt(0)
            }

        }

        return Pair( ingredients, carriers)
    }






    fun  CountHavingCalories(): Double{
        var amount = 0.0

        for (i in listOfDays){
            for (j in i.listOfMeals){
                if(j.dish.size != 0) {
                    amount += j.dish[0].calories
                }
            }
        }

        return amount
    }



    fun CountNeededCalories(): Int{
        var amount = 0
        var days = 0
        val InputDays: com.google.android.material.textfield.TextInputEditText = FirstAddFragment_view.findViewById(R.id.InputDays)

        if (InputDays.text.toString() != ""){
            days = InputDays.text.toString().toInt()
        }

        amount = 3000 * listOfTravelers.size * days

        return amount
    }



    fun CountHavingWeight(): Int{
        var amount = 0

        for (i in listOfDays){
            for (j in i.listOfMeals){
                if(j.dish.size != 0) {
                    amount += j.dish[0].portion_weight
                }
            }
        }

        return amount
    }



    fun CountMaxWeight(): Int{
        var amount = 0
        var days = 0
        val InputDays: com.google.android.material.textfield.TextInputEditText = FirstAddFragment_view.findViewById(R.id.InputDays)

        if (InputDays.text.toString() != ""){
            days = InputDays.text.toString().toInt()
        }

        amount = 700 * listOfTravelers.size * days * 3

        return amount
    }



    override fun DeleteTraveler(position: Int) {
        listOfTravelers.removeAt(position)
        DataTransactionviewModel.getTravelersData.value = listOfTravelers
    }



    override fun DeleteFood(position: Int) {
        listOfFood.removeAt(position)
        DataTransactionviewModel.getFoodData.value = listOfFood
    }



    fun DeleteFoodByIdFromDB(id: Int){
        for(i in listOfFood){
            if(i.Id == id){
                listOfFood.removeAt(listOfFood.indexOf(i))
                break
            }
        }
    }
}
