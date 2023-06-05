package DataModels

class TravelerModel (
    var TravelerName: String,
    var Age: String,
    var Gender: String,
    var Restrictions: String
    )
{
    var changeable = true

    var Id: Int = 0

    var Group: Int = 0

    var MaxWeight: Int = 0
    var AllCarryingWeight: Int = 0
    var MealMaxCarryingWeight: Int = 0
    var MealCarryingWeight: Int = 0

    var amount = 0
}

