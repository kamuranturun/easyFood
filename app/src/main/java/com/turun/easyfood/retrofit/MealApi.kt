package com.turun.easyfood.retrofit

import com.turun.easyfood.pojo.CategoryList
import com.turun.easyfood.pojo.MealsByCategoryList
import com.turun.easyfood.pojo.MealList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface MealApi {

    //ana sayfada göstereceğimiz yemeklerin get isteği
    @GET("random.php")
    fun getRandomMeal():Call<MealList>

    //detay sayfada göstereceğimiz yemeklerin get isteği
    //Lookup full meal details by id
    //www.themealdb.com/api/json/v1/1/lookup.php?i=52772  id ye göre aldığımız için
    @GET("lookup.php")   //soru işareti olan kısma kadar alacağız
    fun getMealDetails(@Query("i")id:String):Call<MealList>//id başındaki i harfini alırız

    //sonra mealViewmodel oluşturalım viewmodel klasörüne


    //filterphp kısmına get yapalım popular itemları almak için
    @GET("filter.php")
    fun getPopularItems(@Query("c")categoryName:String):Call<MealsByCategoryList>

    //ardından homeviewmodele fonksiyon oluşturalım getpopularıtems diye



    @GET("categories.php")//1
    fun getCategories():Call<CategoryList>

    //6 cma
    @GET("filter.php")
    fun getMealsByCategory(@Query("c")categoryName: String):Call<MealsByCategoryList>
    //go and create CategoryMealsViewModel

    //1 search
    @GET("search.php")
    fun searchMeals(@Query("s") seachQuery:String):Call<MealList>
    //1 search go to homeviewmodel
}