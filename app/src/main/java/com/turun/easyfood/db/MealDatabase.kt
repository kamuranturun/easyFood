package com.turun.easyfood.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.turun.easyfood.pojo.Meal

//4 room
@Database(entities = [Meal::class] , version = 1)
//6 room
@TypeConverters(MealTypeConverter::class)
//6 room son
abstract class MealDatabase:RoomDatabase() {

    abstract fun mealDao():MealDao

    companion object{

        @Volatile
        var INSTANCE:MealDatabase? =null

        @Synchronized
        fun getInstance(context:Context):MealDatabase{
            if (INSTANCE == null){
                INSTANCE = Room.databaseBuilder(
                    context,
                    MealDatabase::class.java,
                    "meal.db"
                ).fallbackToDestructiveMigration().build()
            }
            return INSTANCE as MealDatabase
            //4 room go db and create MealTypeConventer
        }

    }
}