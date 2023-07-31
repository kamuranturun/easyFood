package com.turun.easyfood.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.turun.easyfood.R
import com.turun.easyfood.activities.MainActivity
import com.turun.easyfood.adapters.MealsAdapter
import com.turun.easyfood.databinding.FragmentSearchBinding
import com.turun.easyfood.viewModel.HomeViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


//5 search
class SearchFragment : Fragment() {
    //13 search
    private lateinit var binding:FragmentSearchBinding
    //13 search

    //15 search
    private lateinit var viewModel:HomeViewModel
    //15 search

    //17 search
    private lateinit var searchRecyclerviewAdapter:MealsAdapter
    //17 search


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //16 search
        viewModel = (activity as MainActivity).viewModel
        //16 search go to favoritemealsadapter and changen name as MealsAdapter and go back searchfragment

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //14 search
        binding = FragmentSearchBinding.inflate(inflater)
        return binding.root
        //14 search
    }

    //18 search
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //19
        prepareRecyclerView()
        //19

        //21 search
        binding.imgSearchArrow.setOnClickListener { searchMeals() }
        //21 search

        //23 search
        observeSearchedMealsLiveData()
        //23 search

        //27 search
        var searchJob:Job? = null
        binding.edSearchBox.addTextChangedListener {searchQuery->

            searchJob?.cancel()
            searchJob = lifecycleScope.launch {
                delay(500)
                viewModel.searchMeals(searchQuery.toString())

            }
        }//27 search and runapp finish search
    }

    //24 search
    private fun observeSearchedMealsLiveData() {
        viewModel.observeMealSearchLiveData().observe(viewLifecycleOwner, Observer {
            mealsList-> searchRecyclerviewAdapter.differ.submitList(mealsList)
        })
    }//24 search go to nav_graph add fragment search ve home fragmentdan search fragmenta
    //action ver sonra homefragment.kt ye geç

    //22 search
    private fun searchMeals() {
        val searchQuery = binding.edSearchBox.text.toString()
        if (searchQuery.isNotEmpty()){
            viewModel.searchMeals(searchQuery)
        }
    }
    //22 search



    //20 search
    private fun prepareRecyclerView() {
        searchRecyclerviewAdapter = MealsAdapter()
        binding.rvSearchedMeals.apply {
            layoutManager = GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
            adapter = searchRecyclerviewAdapter
        }
    }//20 search

    //18 search


}
//5 search go to fragment_search.xml