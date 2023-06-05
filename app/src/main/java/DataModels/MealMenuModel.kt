package DataModels

class MealMenuModel(val dayNum: Int, var type_of_meal: String)
{
    var expanded = false
    var show_travelers = false
    var dish_id = 0
    val dish = mutableListOf<DishModel>()

}