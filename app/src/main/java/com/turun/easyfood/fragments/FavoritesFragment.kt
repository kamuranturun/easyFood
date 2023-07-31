package com.turun.easyfood.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.turun.easyfood.activities.MainActivity
import com.turun.easyfood.adapters.MealsAdapter
import com.turun.easyfood.databinding.FragmentFavoritesBinding
import com.turun.easyfood.viewModel.HomeViewModel


class FavoritesFragment : Fragment() {
    private lateinit var binding:FragmentFavoritesBinding
    //7 favgörüntüle
    private lateinit var viewModel: HomeViewModel
    //7 favgörüntüle

    //13 favgörüntüle
    private lateinit var favoritesAdapter:MealsAdapter
    //13 favgörüntüle



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //8 favgörüntüle
        viewModel = (activity as MainActivity).viewModel
        //8 favgörüntüle
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =FragmentFavoritesBinding.inflate(inflater)
        return binding.root
        // Inflate the layout for this fragment
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //14 favgörüntüle
        prepareRecyclerView()
        //14 fav görüntüle


        //9 favgörüntüle
        observeFavorites()
        //9 favgörüntüle

        //1 deleteitemfavorites
        val itemTouchHelper = object  : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            )= true

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                viewModel.deleteMeal(favoritesAdapter.differ.currentList[position])

                Snackbar.make(requireView(),"Meal Deleted",Snackbar.LENGTH_LONG)
                    .setAction("Undo",View.OnClickListener {
                        viewModel.insertMeal(favoritesAdapter.differ.currentList[position])
                    }).show()

            }

        }

        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.rvFavorites)
        //attachToRecyclerView ile recyclerviewi güncelledik
        //1 deleteitemfavorites  and run app and than go to categryfragment.xml

    }

    //15 favgörüntüle
    private fun prepareRecyclerView() {
        favoritesAdapter = MealsAdapter()
        binding.rvFavorites.apply {
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL,false)
            adapter = favoritesAdapter

        }
    }
    //15 favgörüntüle

    private fun observeFavorites() {
        //10 favgörüntüle
        viewModel.observeFavoritesMealsLiveData().observe(requireActivity(), Observer {
            //viewLifecycleOwner activity içindir, requireActivity ise fragment için kullanılır

            meals->
            //16 favgörüntüle
            favoritesAdapter.differ.submitList(meals)
            //16 favgörüntüle and runapp


            //10 favgörüntüle and go to favoritesfragment.xml
        })
    }


}