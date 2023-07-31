package com.turun.easyfood.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.turun.easyfood.db.MealDatabase

//4 favgörüntüle
class HomeViewModelFactory (
    private val mealDatabase: MealDatabase
        ):ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(mealDatabase) as T
    }
}//4 favgörüntüle ve go to homefragment delete homemvvm = ViewModelProvider(this)[HomeViewModel::class.java]
//and go to mainactivity