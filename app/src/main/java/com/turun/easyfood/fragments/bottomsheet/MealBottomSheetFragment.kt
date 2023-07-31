package com.turun.easyfood.fragments.bottomsheet

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.turun.easyfood.R
import com.turun.easyfood.activities.MainActivity
import com.turun.easyfood.activities.MealActivity
import com.turun.easyfood.databinding.FragmentMealBottomSheetBinding
import com.turun.easyfood.fragments.HomeFragment
import com.turun.easyfood.viewModel.HomeViewModel

//2 bottomsheet
private const val MEAL_ID = "param1"
//2 bottomsheet and go to bottomsheet xml



class MealBottomSheetFragment :
    //21 bottomsheet change Fragment to  BottomSheetDialogFragment
    BottomSheetDialogFragment()
//21 bottomsheet and go back homefragment
{

    //1 bottomsheet
    private var mealId: String? = null
    //1 bottomsheet

    //4 bottomsheet
    private lateinit var binding: FragmentMealBottomSheetBinding
    //4 bottomsheet

    //7 bottomsheet
    private lateinit var viewModel:HomeViewModel
    //7 bottomsheet

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mealId = it.getString(MEAL_ID)

            //8 bottomsheet
            viewModel= (activity as MainActivity).viewModel
            //8 bottomsheet go to homeviewmodel

        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        //5 bottomsheet
        binding =FragmentMealBottomSheetBinding.inflate(inflater)
        return binding.root
        //5 bottomsheet


    }

    //6 bottomsheet
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //12 bottomsheet
        mealId?.let { viewModel.getMealById(it) }
        //12 bottomsheet

        //13 bottomsheet
        observeBottomsheetMeal()
        //13 bottomsheet

        //24 bottomsheet
        onBottomSheetDialogClick()
        //24 bottomsheet

    } //6 bottomsheet

    //25 bottomsheet
    private fun onBottomSheetDialogClick() {
        binding.bottomSheet.setOnClickListener {
            //28 bottomsheet
            if (mealName !=null && mealThumb !=null){
                val intent = Intent(activity, MealActivity::class.java)
                intent.apply {
                    putExtra(HomeFragment.MEAL_ID,mealId)
                    putExtra(HomeFragment.MEAL_NAME,mealName)
                    putExtra(HomeFragment.MEAL_THUMB,mealThumb)
                }
                startActivity(intent)
                            }//28 bottomsheet and go to values and create styles resource file xml

        }
    }//25 bottomsheet

    //26 bottomsheet
    private var mealName:String?=null
    private var mealThumb:String? = null
    //26 bottomsheet


    //14 bottomsheet
    private fun observeBottomsheetMeal() {
        //16 bottomsheet
        viewModel.observeBottomsheetMeal().observe(viewLifecycleOwner, Observer {
            meal->
            Glide.with(this).load(meal.strMealThumb).into(binding.imgBottomSheet)
            binding.tvBottomSheetArea.text = meal.strArea
            binding.tvBottomSheetCategory.text = meal.strCategory
            binding.tvBottomSheetMealName.text = meal.strMeal

            //27 bottomsheet
            mealName = meal.strMeal
            mealThumb= meal.strMealThumb
            //27 bottomsheet

        })//16 bottomsheet go to mostpopularadapter

    }//14 bottomsheet go to homeviewmodel

    companion object {


        @JvmStatic fun newInstance(param1: String) =
                MealBottomSheetFragment().apply {
                    arguments = Bundle().apply {
                        putString(MEAL_ID, param1)
                    }
                }
    }
}