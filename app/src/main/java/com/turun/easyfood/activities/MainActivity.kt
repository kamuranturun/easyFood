package com.turun.easyfood.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.turun.easyfood.R
import com.turun.easyfood.db.MealDatabase
import com.turun.easyfood.viewModel.HomeViewModel
import com.turun.easyfood.viewModel.HomeViewModelFactory

class MainActivity : AppCompatActivity() {

    //5 favgörüntüle
     val viewModel: HomeViewModel by lazy{
        val mealDatabase= MealDatabase.getInstance(this)
        val homeViewModelProviderFactory = HomeViewModelFactory(mealDatabase)
        ViewModelProvider(this,homeViewModelProviderFactory)[HomeViewModel::class.java]
    }//5 favgörüntüle and go to homefragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.btm_nav)
        val navController= Navigation.findNavController(this, R.id.host_fragment)

        NavigationUI.setupWithNavController(bottomNavigation,navController)
        //fragment ile bottom navı birbirine bağlar
    }
}
/**
 * Serch işlemleri için başlangıcı api den get isteği yaparak verdik mealapi
 *
 * statusbar ayarları için themesxml e gidiyoruz
 */