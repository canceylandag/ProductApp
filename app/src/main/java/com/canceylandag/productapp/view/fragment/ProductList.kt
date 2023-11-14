package com.canceylandag.productapp.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.canceylandag.productapp.R
import com.canceylandag.productapp.databinding.FragmentProductListBinding
import com.canceylandag.productapp.model.ProductModel
import com.canceylandag.productapp.service.ProductService
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

private lateinit var binding:FragmentProductListBinding

private var productList: ProductModel?=null

class ProductList : Fragment() {

    private val BASE_URL = "https://dummyjson.com"
    private var job : Job? = null

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


        val retrofit= Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ProductService::class.java)

        job= CoroutineScope(Dispatchers.IO+exceptionHandler).launch {

            val response=retrofit.getList()

            withContext(Dispatchers.Main){

                if (response.isSuccessful){
                    response.body()?.let { asd ->
                        productList= asd

                        productList?.products?.forEach {
                            println(it.title)
                        }
                    }
                }

            }

        }

    }

}