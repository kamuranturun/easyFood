package com.turun.easyfood.fragments


import android.content.Intent
import android.os.Bundle


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.turun.easyfood.R
import com.turun.easyfood.activities.CategoryMealsActivity
import com.turun.easyfood.activities.MainActivity
import com.turun.easyfood.activities.MealActivity
import com.turun.easyfood.adapters.CategoriesAdapter
import com.turun.easyfood.adapters.MostPopularAdapter

import com.turun.easyfood.databinding.FragmentHomeBinding
import com.turun.easyfood.fragments.bottomsheet.MealBottomSheetFragment
import com.turun.easyfood.pojo.MealsByCategory
import com.turun.easyfood.pojo.Meal

import com.turun.easyfood.viewModel.HomeViewModel



class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel:HomeViewModel
    private lateinit var randomMeal:Meal
    private lateinit var popularItemsAdapter:MostPopularAdapter//adapterimizi aktive etmeye başlayalım

    //20
    private lateinit var categoriesAdapter: CategoriesAdapter



    companion object{
        const val  MEAL_ID = "com.turun.easyfood.fragments.idMeal"
        const val  MEAL_NAME = "com.turun.easyfood.fragments.nameMeal"
        const val  MEAL_THUMB = "com.turun.easyfood.fragments.thumbMeal"
        //benzersiz kimlikler

        //5 cma
        const val CATEGORY_NAME = "com.turun.easyfood.fragments.categoryName"


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //6 favgörüntüle
        viewModel = (activity as MainActivity).viewModel
        //6 favgörüntüle and go to favorites fragment

       //homemvvm = ViewModelProvider(this)[HomeViewModel::class.java]
        //veya
       //homemvvm = ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)
        //kullanılabilir
         //vey alttaki eski hali
        //homemvvm = androidx.lifecycle.ViewModelProviders.of(this)[HomeViewModel::class.java]

        //adapteri inişilize edelim
        popularItemsAdapter = MostPopularAdapter()


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preparePopularItemsRecyclerView()

        //2 yemekleri otomatik çağır bunu devre dışı bıraktık
       // viewModel.getRandomMeal()

        //1 (başkayol),yemekleri otomatik çağırmanın başka bir yoluda
        viewModel.getRandomMeal()
        //(başkayol), yazıp tekrar homeviewmodele gidip initi devredışı bırakıp diğer işlemleri yapmaktır


        observeRandomMeal()

        onRandomMealClick()

        viewModel.getPopularItems()
        observePopularItemsLiveData()

        onPopularItemClick()

        //18
        preparecategoriesRecyclerView()

        //7
        viewModel.getCategories()

        //8
        observeCategoriesLiveData()

        //3 cma
        onCategoryClick()

        //19 bottomsheet
        onPopularItemLongClick()
        //19 bottomsheet

        //25 search
        onSearchIconClick()
        //25 search


    }

    //26 search
    private fun onSearchIconClick() {

        binding.imgSearch.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
        }

    }//26 search and runapp sonra searchfragmente git otomatik arama için

    //20 bottomsheet
    private fun onPopularItemLongClick() {
        popularItemsAdapter.onLongItemClick = {meal->
            val mealBottomSheetFragment  =MealBottomSheetFragment.newInstance(meal.idMeal)
            //22 bottomsheet
            mealBottomSheetFragment.show(childFragmentManager,"Meal Info")
            //22 bottomsheet and go to fragmentmealbottomsheet xml

        }
    }//20 bottomsheet go to MealBottomSheetFragment

    //4 cma
    private fun onCategoryClick() {
        categoriesAdapter.onItemClick = {
            category->
            val intent = Intent(activity,CategoryMealsActivity::class.java)

            // 5 cma yukarıda tanımlama yap ardından buraya devam
            intent.putExtra(CATEGORY_NAME,category.strCategory)
            startActivity(intent)
            //go to meal api
        }
    }

    //19
    private fun preparecategoriesRecyclerView() {
        binding.recViewCategories.apply {
            layoutManager = GridLayoutManager(context,3,GridLayoutManager.VERTICAL,false)
            //grid ızgara görünümü

            //21
            categoriesAdapter= CategoriesAdapter()
            adapter= categoriesAdapter

        }
    }

    //9
    private fun observeCategoriesLiveData() {
        viewModel.observeCategoryLiveData().observe(viewLifecycleOwner, Observer {

                categories ->//go to adapter
            //22
            categoriesAdapter.setCategoryList(categories)
            //Log.d("test",category.strCategory)



        })
    }

    private fun onPopularItemClick() {
        popularItemsAdapter.onItemClick = {
            meal->
            val intent = Intent(activity,MealActivity::class.java)
            intent.putExtra(MEAL_ID,meal.idMeal)
            intent.putExtra(MEAL_NAME,meal.strMeal)
            intent.putExtra(MEAL_THUMB,meal.strMealThumb)
            startActivity(intent)

        }
    }


    private fun preparePopularItemsRecyclerView() {
        //recyclerviewi aktif ediyoruz
        binding.recViewMealsPopular.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL,false)

            //adapteri bağla
            adapter = popularItemsAdapter
        }
    }

    private fun observePopularItemsLiveData() {
        viewModel.observePopularItemsLiveData().observe(viewLifecycleOwner
        ) { mealList->
            //adapter ile elemanları alalım
            popularItemsAdapter.setMeals(mealsList = mealList as ArrayList<MealsByCategory>)
        }
    }

    private fun onRandomMealClick() {
        //random yemeğe tıklayınca olacaklar
        binding.randomMealCard.setOnClickListener {
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(MEAL_ID,randomMeal.idMeal)
            intent.putExtra(MEAL_NAME,randomMeal.strMeal)
            intent.putExtra(MEAL_THUMB,randomMeal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun observeRandomMeal() {

           viewModel.observeRandomMealLiveData().observe(viewLifecycleOwner,object :Observer<Meal?> {
        override fun onChanged(value: Meal?) {
        //Glide burda kullanacağız değime sırasında
        Glide.with(this@HomeFragment)
        .load(value!!.strMealThumb)
        .into(binding.imgRandomMeal)

            this@HomeFragment.randomMeal = value
        }
        })

    }


}