package com.turun.easyfood.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.turun.easyfood.R
import com.turun.easyfood.adapters.CategoryMealsAdapter
import com.turun.easyfood.databinding.ActivityCategoryMealsBinding
import com.turun.easyfood.fragments.HomeFragment
import com.turun.easyfood.viewModel.CategoryMealsViewModel

class CategoryMealsActivity : AppCompatActivity() {

    //11 cma
    lateinit var binding: ActivityCategoryMealsBinding
    lateinit var categoryMealsViewModel:CategoryMealsViewModel

    //24 cma
    lateinit var categoryMealsAdapter: CategoryMealsAdapter




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //12 cma
        binding = ActivityCategoryMealsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //23 cma
        prepareRecyclerView()

        //13 cma
        categoryMealsViewModel = ViewModelProvider(this)[CategoryMealsViewModel::class.java]
        //go to categorymealsviewmodel

        //15 cma
        categoryMealsViewModel.getMealsByCategory(intent.getStringExtra(HomeFragment.CATEGORY_NAME)!!)

        //16 cma
        categoryMealsViewModel.obserMealsLiveData().observe(this, Observer { mealsList->
            //mealsList->mealsList.forEach{}
                //Log.d("tcma",it.strMeal)
            //go and create meal_item.xml 17 cma

                //26 cma
            binding.tvCategoryCount.text=mealsList.size.toString()
            categoryMealsAdapter.setMealsList(mealsList)


        })



    }


    //25 cma
    private fun prepareRecyclerView() {
        categoryMealsAdapter = CategoryMealsAdapter()
        binding.rvMeals.apply {
            layoutManager = GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
            adapter =categoryMealsAdapter
        }
    }
}