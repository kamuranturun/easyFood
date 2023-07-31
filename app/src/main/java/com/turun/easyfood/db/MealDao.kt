package com.turun.easyfood.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.turun.easyfood.pojo.Meal

//3 room
@Dao
interface MealDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE) //anahtar çakışmasında yenisi ile değiştirmek
    suspend fun upsert(meal: Meal)

    @Delete
    suspend fun delete(meal:Meal)

    @Query("SELECT * FROM mealInformation")
    fun getAllMeals():LiveData<List<Meal>> //3 room go to create MealsDatabase
}