package ViewModels;

import DataModels.*
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DataTransactionViewModel : ViewModel(){

    var addTravelerData = MutableLiveData<TravelerModel>()
    var getTravelersData = MutableLiveData<List<TravelerModel>>()
    var changeTravelersData = MutableLiveData<MutableList<TravelerModel>>()
    var setChangedTravelerToAdapter = MutableLiveData<TravelerModel>()


    var addFoodData = MutableLiveData<FoodModel>()
    var getFoodData = MutableLiveData<List<FoodModel>>()


    var addCreatedDaysMenuToRegistrationList = MutableLiveData<MutableList<DayMenuModel>>()
    var send = MutableLiveData<DishModel>()
    var getDaysData = MutableLiveData<List<DayMenuModel>>()
    var sendSelectedDishToMealAdapter = MutableLiveData<DishModel>()
    var addSelectedDishToMenuLists = MutableLiveData<MutableList<MealMenuModel>>()

}

