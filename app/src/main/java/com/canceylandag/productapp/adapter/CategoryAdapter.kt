package com.canceylandag.productapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.canceylandag.productapp.R

class CategoryAdapter(private val CategoryList:List<String>):RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

   class CategoryViewHolder(view: View):RecyclerView.ViewHolder(view){
       var category: TextView?=null;
        init {
            category=view.findViewById(R.id.SingleCategoryRow)
        }
   }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.single_drawer_menu,parent,false)

        return CategoryAdapter.CategoryViewHolder(view)
    }

    override fun getItemCount(): Int {
        return CategoryList.size
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.category?.text=CategoryList[position]
    }

}