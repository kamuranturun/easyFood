package com.turun.easyfood.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.turun.easyfood.db.MealDatabase
import com.turun.easyfood.pojo.Meal
import com.turun.easyfood.pojo.MealList
import com.turun.easyfood.retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealViewModel(
    //2 favorites
val mealDatabase:MealDatabase
//2 favorites go to homeviewmodel
):ViewModel() {

    private var mealDetailsLiveData = MutableLiveData<Meal>()

    //bir fonksiyon oluşturalım ve mealdetail için retrofit isteği yapalım
    fun getMealDetail(id:String){
        RetrofitInstance.api.getMealDetails(id).enqueue(object : Callback<MealList?> {
            override fun onResponse(call: Call<MealList?>, response: Response<MealList?>) {
                if(response.body()!=null){
                    mealDetailsLiveData.value= response.body()!!.meals[0]
                }

                else return
            }

            override fun onFailure(call: Call<MealList?>, t: Throwable) {
                Log.d("MealActivity",t.message.toString())
            }
        })
    }
    //observi de oluşturalım sonra meal activitiye gidip instance oluşturalım
    //yani oluşturduğumuz viewmodeli orda kullanalım
    fun observerMealDetailsLiveData():LiveData<Meal>{
        return mealDetailsLiveData
    }

    //3 favorites
    fun insertMeal(meal:Meal){
        //coroutine kullanmak için
        viewModelScope.launch {
            mealDatabase.mealDao().upsert(meal)
        }
    }//3 favorites


}