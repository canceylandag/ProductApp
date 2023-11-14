package com.canceylandag.productapp.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.canceylandag.productapp.R
import com.canceylandag.productapp.model.Product
import com.canceylandag.productapp.model.ProductModel

class ViewAdapter(private var ProductList:List<Product>):RecyclerView.Adapter<ViewAdapter.ProductViewHolder>() {

    class ProductViewHolder(view: View):RecyclerView.ViewHolder(view){

        var thumbnail:ImageView?=null;
        var title:TextView?=null;
        var rating:TextView?=null;
        var date:TextView?=null;
        var detailButton:Button?=null;

        init {
            thumbnail=view.findViewById(R.id.SingleThumbnail)
            title=view.findViewById(R.id.SingleTitle)
            rating=view.findViewById(R.id.SingleRating)
            date=view.findViewById(R.id.SingleDate)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        return ProductList.size
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.title?.text=ProductList[position].title
        holder.rating?.text=ProductList[position].rating.toString()
        holder.date?.text=ProductList[position].price.toString()

    }

}