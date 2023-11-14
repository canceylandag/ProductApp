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
private var productList: ProductModel?=null


class ProductList : Fragment() {

    private val BASE_URL = "https://dummyjson.com"
    private var job : Job? = null
    private var jobCategory : Job? = null
    var products:List<Product>?=null
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


        /*binding.navView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.mitem1->Toast.makeText(context,"Item1",Toast.LENGTH_LONG).show()
                R.id.mitem2->Toast.makeText(context,"Item2",Toast.LENGTH_LONG).show()
                R.id.mitem3->Toast.makeText(context,"Item3",Toast.LENGTH_LONG).show()
            }
            true
        }*/
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (toggle.onOptionsItemSelected(item)){
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    fun loadProductData(){

        val newRetro= RetrofitGeneric(BASE_URL,ProductService::class.java)

        job= CoroutineScope(Dispatchers.IO+exceptionHandler).launch {

            val response=newRetro.retrofit.getList()

            withContext(Dispatchers.Main){

                if (response.isSuccessful){
                    response.body()?.let { asd ->
                        productList= asd

                        productList?.products?.let {
                            adapter= ViewAdapter(it)
                            binding.recyclerView.adapter=adapter
                        }
                    }
                }

            }

        }

    }

    fun loadCategories(){

        val categoryRetro=RetrofitGeneric(BASE_URL,ProductService::class.java)
        jobCategory=CoroutineScope(Dispatchers.IO+exceptionHandler).launch {
            val response = categoryRetro.retrofit.getCaegories()

            withContext(Dispatchers.Main){
                if (response.isSuccessful){
                    response.body()?.let {
                        categoryAdapter= CategoryAdapter(it)
                        binding.recyclerViewCategory.adapter=categoryAdapter
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