package com.turun.easyfood.db


import androidx.room.TypeConverter
import androidx.room.TypeConverters

//5 room
@TypeConverters
class MealTypeConverter {

    @TypeConverter
    fun fromAnyToString(attribute:Any?):String{
        if (attribute == null)
            return  ""

        return attribute as String

    }

    @TypeConverter
    fun fromStrimgToAny(attribute:String):Any{
        if (attribute ==null)
            return ""
        return attribute
    }
    //5 room go to MealsDatabase
}