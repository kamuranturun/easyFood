package com.turun.easyfood.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.turun.easyfood.R
import com.turun.easyfood.activities.MainActivity
import com.turun.easyfood.adapters.CategoriesAdapter
import com.turun.easyfood.databinding.FragmentCategoriesBinding
import com.turun.easyfood.viewModel.HomeViewModel


class CategoriesFragment : Fragment() {

    //2 allcategories
    private lateinit var binding:FragmentCategoriesBinding
    //2 allcategories

    //6 allcategories
    private lateinit var categoriesAdapter: CategoriesAdapter
    //6 allcategories

    //8 allcategories
    private lateinit var viewModel:HomeViewModel
    //8 allcategories





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //9 allcategories
        viewModel = (activity as MainActivity).viewModel
        //9 allcategories



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //3 allcategories
        binding = FragmentCategoriesBinding.inflate(inflater)
        return binding.root
        //3 allcategories

    }

    //4 allcategories
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareRecyclerView()

        //10 allcategories
        observeCategories()
        //10 allcategories

    }

    //11 allcategories
    private fun observeCategories() {
        viewModel.observeCategoryLiveData().observe(viewLifecycleOwner, Observer {
            categories->categoriesAdapter.setCategoryList(categories)
        })
    }    //11 allcategories and runapp and create bottomsheet package in fragments

    //4 allcategories


    //5 allcategories
    private fun prepareRecyclerView() {
        //7 allcategories
        categoriesAdapter = CategoriesAdapter()
        binding.rvCategories.apply {
            layoutManager =GridLayoutManager(context,3,GridLayoutManager.VERTICAL,false)
            adapter =categoriesAdapter
        }    //7 allcategories


    }
    //5 allcategories



}