package com.turun.easyfood.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.turun.easyfood.databinding.CategoryItemBinding
import com.turun.easyfood.pojo.Category

//10
class CategoriesAdapter:RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder>() {

    //13
    private var categoriesList = ArrayList<Category>()

    //1 cma
    var onItemClick:((Category)->Unit)? = null

    //14
    fun setCategoryList(categoriesList:List<Category>){
        this.categoriesList=categoriesList as ArrayList<Category>
        notifyDataSetChanged()
    }

    //11
    inner class CategoryViewHolder(val binding:CategoryItemBinding):RecyclerView.ViewHolder(binding.root)

    //12
    //alt+enter implement member
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        //15
        return CategoryViewHolder(
            CategoryItemBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun getItemCount(): Int {
        //16
        return categoriesList.size
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        //17
        Glide.with(holder.itemView).load(categoriesList[position].strCategoryThumb)
            .into(holder.binding.imgCategory)

        holder.binding.tvCategoryName.text= categoriesList[position].strCategory
        //go to home fragment :)

        //2 cma
        holder.itemView.setOnClickListener {
            onItemClick!!.invoke(categoriesList[position])
            //go to home fragment
        }
    }

}