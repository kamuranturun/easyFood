package com.turun.easyfood.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.turun.easyfood.db.MealDatabase
import com.turun.easyfood.pojo.*

import com.turun.easyfood.retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(
    //1 favgörüntüle
private val mealDatabase:MealDatabase
//1 favgörüntüle
):ViewModel() {
    private var randomMealLiveData= MutableLiveData<Meal>()
    private var popularItemsLiveData = MutableLiveData<List<MealsByCategory>>()
    private var categoriesLiveData = MutableLiveData<List<Category>>()//3

    //10 bottomsheet
    private var bottomSheetLiveData = MutableLiveData<Meal>()
    //10 bottomsheet

    //2 favgörüntüle
    private var favoritesMealsLiveData = mealDatabase.mealDao().getAllMeals()
    //2 favgörüntüle

    //2 search
    private val searchedMealsLiveData = MutableLiveData<List<Meal>>()
    //2 search

    //1 yemekleri otomatik çağır burdan
    /*
     init {
        getRandomMeal()
    }
     */

    //1 yemekleri otomatik çağır ve homefragmenta git bu fonksiyonu sil burda çağırdık çünkü
    //birde burda çağırmamızın sebebi telefonu yatay moda alınca tekrar istek yapıp mevcut
    //yemeği değiştirmesin

    //2 (başkayol)
    private var saveStateRandomMeal:Meal?= null
    //2 (başkayol)
    fun getRandomMeal(){
        //3(başkayol)
        saveStateRandomMeal?.let {
            randomMeal->randomMealLiveData.postValue(randomMeal)
            return
        }//3 (başkayol)
        RetrofitInstance.api.getRandomMeal().enqueue(object : Callback<MealList?> {
            override fun onResponse(call: Call<MealList?>, response: Response<MealList?>) {

                //başarılı olma durumunda çekilecek veriler
                if (response.body() != null){

                    val randomMeal: Meal = response.body()!!.meals[0]
                  randomMealLiveData.value= randomMeal
                    //4(başkayol)
                    saveStateRandomMeal= randomMeal
                    //4(başkayol) and runapp , app finish




                }else{
                    return
                }

            }

            override fun onFailure(call: Call<MealList?>, t: Throwable) {
             //başarısız olma durumunda verilecek hata mesajı
                Log.d("Home",t.message.toString())

            }
        })
    }

    fun getPopularItems(){
        RetrofitInstance.api.getPopularItems("Seafood").enqueue(object : Callback<MealsByCategoryList?> {
            override fun onResponse(call: Call<MealsByCategoryList?>, response: Response<MealsByCategoryList?>) {

                if (response.body() !=null){
                    popularItemsLiveData.value = response.body()!!.meals
                }
            }

            override fun onFailure(call: Call<MealsByCategoryList?>, t: Throwable) {
                Log.d("homefragment",t.message.toString())
            }
        })
    }

    //2
    fun getCategories(){
        RetrofitInstance.api.getCategories().enqueue(object : Callback<CategoryList?> {
            override fun onResponse(call: Call<CategoryList?>, response: Response<CategoryList?>) {

                //4
                response.body()?.let { categoryList ->
                    categoriesLiveData.postValue(categoryList.categories)
                }

            }

            override fun onFailure(call: Call<CategoryList?>, t: Throwable) {
               //5
                Log.d("homeviewmodel",t.message.toString())
            }
        })
    }

    //9 bottomsheet
    fun getMealById(id:String){
        RetrofitInstance.api.getMealDetails(id).enqueue(object : Callback<MealList?> {
            override fun onResponse(call: Call<MealList?>, response: Response<MealList?>) {
                val meal = response.body()?.meals?.first()
                //11 bottomsheet
                meal?.let {meal->
                    bottomSheetLiveData.postValue(meal)
                }
            }

            override fun onFailure(call: Call<MealList?>, t: Throwable) {
                Log.e("HomeViewModel",t.message.toString())
                //11 bottomsheet go to bottomsheetfragment
            }
        })
    }//9 bottomsheet

    //3 favorites
    fun insertMeal(meal:Meal){
        //coroutine kullanmak için
        viewModelScope.launch {
            mealDatabase.mealDao().upsert(meal)
        }
    }//3 favorites

    //3 search
    fun searchMeals(searchQuery:String) = RetrofitInstance.api.searchMeals(searchQuery).enqueue(
        object : Callback<MealList?> {
            override fun onResponse(call: Call<MealList?>, response: Response<MealList?>) {
                val mealList = response.body()?.meals
                mealList?.let {
                    searchedMealsLiveData.postValue(it)
                }
            }

            override fun onFailure(call: Call<MealList?>, t: Throwable) {
                Log.e("homeviewModelSearch",t.message.toString())
            }
        }
    )
    //3 search

    //4 search
    fun observeMealSearchLiveData():LiveData<List<Meal>> =searchedMealsLiveData
    //4 search go to fragments and  create SearchFragment

    //4 favorites
    fun deleteMeal(meal:Meal){
        viewModelScope.launch {
            mealDatabase.mealDao().delete(meal)
        }
    }//4 favorites  create MealViewModelFactory

    fun observeRandomMealLiveData():LiveData<Meal>{
        return randomMealLiveData
    }

    fun observePopularItemsLiveData():LiveData<List<MealsByCategory>>{
        return popularItemsLiveData
    }

    //6
    fun observeCategoryLiveData():LiveData<List<Category>>{
        return categoriesLiveData
        //sonra goto home fragment
    }

    // 3 favgörüntüle
    fun observeFavoritesMealsLiveData():LiveData<List<Meal>>{
        return favoritesMealsLiveData
    }
    //3 favgörüntüle  sonra viewmodel pakete git ve homeviewmodelfactory adında class oluştur

    //15 bottomsheet
    fun observeBottomsheetMeal():LiveData<Meal> = bottomSheetLiveData //return gibi
    //15 bottomsheet go back mealbottomsheetfragment
}