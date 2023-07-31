package com.turun.easyfood.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.turun.easyfood.db.MealDatabase
//5 favorites
class MealViewModelFactory(
   private val mealDatabase:MealDatabase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MealViewModel(mealDatabase) as T
        //5 favorites go to mealactivity
    }
}