package com.turun.easyfood.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.turun.easyfood.R
import com.turun.easyfood.databinding.ActivityMealBinding
import com.turun.easyfood.db.MealDatabase
import com.turun.easyfood.fragments.HomeFragment
import com.turun.easyfood.pojo.Meal
import com.turun.easyfood.viewModel.MealViewModel
import com.turun.easyfood.viewModel.MealViewModelFactory

class MealActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMealBinding
    private lateinit var mealId:String
    private lateinit var mealName:String
    private lateinit var mealThumb:String
    private lateinit var youtubeLink:String

    private lateinit var mealmvvm:MealViewModel //viewModeli cağıralım

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
     //inişilize edelim
        binding = ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //simdi inişilize edelim

        //6 favorites
        val mealDataBase = MealDatabase.getInstance(this)
        val viewModelFactory = MealViewModelFactory(mealDataBase)
        mealmvvm = ViewModelProvider(this,viewModelFactory)[MealViewModel::class.java]
        //6 favorites ekledik
        //mealmvvm = ViewModelProvider(this)[MealViewModel::class.java]

        getMealInformationFromIntent()
        setInformationInViews()
        loadingCase()

        mealmvvm.getMealDetail(mealId)
        observerMealDetailsLiveData()

        onYoutubeImageClick()

        //7 favorites
        onFavoritesClick()
        //7 favorites


// homemvvm = ViewModelProvider(this).get(HomeViewModel::class.java)

    //setContentView(R.layout.activity_meal) bunu kaldırdık




    }

    //8 favorites
    private fun onFavoritesClick() {
        binding.btnAddToFav.setOnClickListener {
            //11 favorites
            mealToSave?.let {
                mealmvvm.insertMeal(it)
                Toast.makeText(this,"Meal Saved",Toast.LENGTH_LONG).show()
                //11 favorites (yani favorilere ekle) bitti finish
                //bundan sonra favorites fragment da görüntüleme işlemi yapacağız
                //go to homeviewmodel
            }
        }
    }//8 favorites

    private fun onYoutubeImageClick() {
        binding.imgYoutube.setOnClickListener {
            //youtuba gidelim tıklanınca yemeğin videosuna bakalım
            val intent= Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink))
            startActivity(intent)
        }
    }
    //9 favorites
    private var mealToSave:Meal?= null
    //9 favorites

    private fun observerMealDetailsLiveData() {
        mealmvvm.observerMealDetailsLiveData().observe(this,object : Observer<Meal?> {
            override fun onChanged(value: Meal?) {
                onResponseCase()
                val meal = value
                //10 favorites
                mealToSave=meal
                //10 favorites


                //gelenleri çekelim
                binding.tvCategory.text= "Category : ${meal!!.strCategory}"
                binding.tvArea.text = "Area: ${meal.strArea}"
                binding.tvInstructionsSteps.text = meal.strInstructions

                youtubeLink = meal.strYoutube.toString()

            }
        })
    }

    private fun setInformationInViews() {
        //glide ile resmi alacağız
        Glide.with(applicationContext)
            .load(mealThumb)
            .into(binding.imgMealDetail)

        //detaya gidince toolbarı değiştirelim
        binding.collapsingToolbar.title = mealName
        //gelen title rengini değiştirelim her iki durumda
        binding.collapsingToolbar.setCollapsedTitleTextColor(resources.getColor(R.color.white))
        binding.collapsingToolbar.setExpandedTitleColor(resources.getColor(R.color.white))
    }

    private fun getMealInformationFromIntent() {
        val intent = intent
        //string almak için
        mealId= intent.getStringExtra(HomeFragment.MEAL_ID)!!
        mealName= intent.getStringExtra(HomeFragment.MEAL_NAME)!!
        mealThumb = intent.getStringExtra(HomeFragment.MEAL_THUMB)!!

    }

    private fun loadingCase(){
        //yükleniyor kısmı
        binding.progressBar.visibility=View.VISIBLE
        binding.btnAddToFav.visibility = View.INVISIBLE
        binding.tvInstruction.visibility = View.INVISIBLE
        binding.tvCategory.visibility = View.INVISIBLE
        binding.tvArea.visibility = View.INVISIBLE
        binding.imgYoutube.visibility = View.INVISIBLE

    }

    private fun onResponseCase(){
        //favori butonu görünür yapalım
        binding.progressBar.visibility=View.INVISIBLE
        binding.btnAddToFav.visibility = View.VISIBLE
        binding.tvInstruction.visibility = View.VISIBLE
        binding.tvCategory.visibility = View.VISIBLE
        binding.tvArea.visibility = View.VISIBLE
        binding.imgYoutube.visibility = View.VISIBLE

    }
}