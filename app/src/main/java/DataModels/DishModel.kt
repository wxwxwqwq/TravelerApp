package DataModels

class DishModel
    (
    val id: Int,
    val name: String,
    val calories: Int,
    val portion_weight: Int
    )
{
    val listOfIngredients = mutableListOf<IngredientModel>()
}