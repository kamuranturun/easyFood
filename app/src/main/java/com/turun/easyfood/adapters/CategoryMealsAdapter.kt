package com.turun.easyfood.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.turun.easyfood.databinding.MealItemBinding
import com.turun.easyfood.pojo.MealList
import com.turun.easyfood.pojo.MealsByCategory

//18 cma
class CategoryMealsAdapter:RecyclerView.Adapter<CategoryMealsAdapter.CategoryMealsViewHolder>() {

    private var mealsList = ArrayList<MealsByCategory>()

    fun setMealsList(mealsList: List<MealsByCategory>){
        this.mealsList= mealsList as ArrayList<MealsByCategory>
        notifyDataSetChanged() //liste kendini yeniler
    }
    //19 cma
    inner class CategoryMealsViewHolder(val binding:MealItemBinding):RecyclerView.ViewHolder(binding.root)

    //20 cma implement member
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryMealsViewHolder {
        return CategoryMealsViewHolder(MealItemBinding.inflate(LayoutInflater.from(parent.context)))

    }

    //22 cma and goto categorymealsactivity
    override fun getItemCount(): Int {
        return mealsList.size
    }

    //21 cma
    override fun onBindViewHolder(holder: CategoryMealsViewHolder, position: Int) {


        Glide.with(holder.itemView).load(mealsList[position].strMealThumb).into(holder.binding.imgMeal)
        holder.binding.tvMealName.text = mealsList[position].strMeal


    }



}