package DataModels

class DayMenuModel(var day_number: Int)
{
    var expanded = false
    val listOfMeals = mutableListOf<MealMenuModel>()
}