package com.canceylandag.productapp.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.canceylandag.productapp.R
import com.canceylandag.productapp.adapter.CategoryAdapter
import com.canceylandag.productapp.adapter.ViewAdapter
import com.canceylandag.productapp.databinding.FragmentProductListBinding
import com.canceylandag.productapp.model.Product
import com.canceylandag.productapp.model.ProductModel
import com.canceylandag.productapp.service.ProductService
import com.canceylandag.productapp.service.RetrofitGeneric
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

private lateinit var binding:FragmentProductListBinding
lateinit var toggle:ActionBarDrawerToggle
private lateinit var productList: MutableList<Product>
private lateinit var categoryList: MutableList<String>



class ProductList : Fragment() {

    private val BASE_URL = "https://dummyjson.com"
    private var job : Job? = null
    private var jobCategory : Job? = null
    val globalRetro=RetrofitGeneric(BASE_URL,ProductService::class.java)
    private lateinit var adapter: ViewAdapter
    private lateinit var categoryAdapter: CategoryAdapter

    val exceptionHandler= CoroutineExceptionHandler { coroutineContext, throwable ->
        println("Error ${throwable.localizedMessage}")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProductListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Drawer Kısmı
        val drawerLayout= binding.drawerLayout
        toggle = ActionBarDrawerToggle(activity,drawerLayout,R.string.open,R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)



        //RecyclerViewer
        //Ürünler İçin
        val layoutManager:RecyclerView.LayoutManager=LinearLayoutManager(context)
        //Drawerdaki Kategoriler için
        val layoutManager2:RecyclerView.LayoutManager=LinearLayoutManager(context)

        binding.recyclerView.layoutManager=layoutManager
        binding.recyclerViewCategory.layoutManager=layoutManager2
        loadCategories()
        loadProductData()


    }


    private fun loadProductData(){


        job= CoroutineScope(Dispatchers.IO+exceptionHandler).launch {

            val response=globalRetro.retrofit.getList()

            withContext(Dispatchers.Main){

                if (response.isSuccessful){
                    response.body()?.let { asd ->
                        productList= asd.products as MutableList<Product>
                        adapter= ViewAdapter(productList)
                        binding.recyclerView.adapter=adapter


                    }
                }

            }

        }

    }

    private fun loadCategories(){

        jobCategory=CoroutineScope(Dispatchers.IO+exceptionHandler).launch {
            val response = globalRetro.retrofit.getCaegories()

            withContext(Dispatchers.Main){
                if (response.isSuccessful){
                    categoryList=response.body() as MutableList<String>
                    categoryList.add(0,"All")
                    categoryList?.let {
                        categoryAdapter= CategoryAdapter(categoryList){
                            if (it!="All"){
                                loadProductsOfCategory(it)
                            }else {
                                loadProductData()
                            }
                            
                        }
                        binding.recyclerViewCategory.adapter=categoryAdapter
                    }

                }
            }
        }
    }

    private fun loadProductsOfCategory(category:String){

        job=CoroutineScope(Dispatchers.IO+exceptionHandler).launch {
            val response = globalRetro.retrofit.getProductOfCategories(category)

            withContext(Dispatchers.Main){
                if (response.isSuccessful){
                    response.body()?.let {
                        productList.clear()
                        productList.addAll(it.products as MutableList<Product>)
                        adapter.notifyDataSetChanged()


                    }

                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        job?.cancel()
    }

}