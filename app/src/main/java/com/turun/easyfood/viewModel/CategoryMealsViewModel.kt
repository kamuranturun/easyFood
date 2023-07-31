package com.turun.easyfood.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.turun.easyfood.pojo.MealsByCategory
import com.turun.easyfood.pojo.MealsByCategoryList
import com.turun.easyfood.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

//7 cma
class CategoryMealsViewModel :ViewModel(){

    //8 cma
    val mealsLiveData = MutableLiveData<List<MealsByCategory>>()

     //7 cma
    fun getMealsByCategory(categoryName:String){
        RetrofitInstance.api.getMealsByCategory(categoryName).enqueue(object : Callback<MealsByCategoryList?> {
            override fun onResponse(
                call: Call<MealsByCategoryList?>,
                response: Response<MealsByCategoryList?>
            ) {


                //9 cma
                response.body()?.let {
                    mealsList->
                    mealsLiveData.postValue(mealsList.meals)
                }

            }

            override fun onFailure(call: Call<MealsByCategoryList?>, t: Throwable) {
                //10 cma
                Log.e("categoryMealsViewModel",t.message.toString())
                //go to CategoryMealsActivity
            }
        })
    }

    //14 cma
    fun obserMealsLiveData():LiveData<List<MealsByCategory>>{
        return mealsLiveData
        //go to activity
    }
}