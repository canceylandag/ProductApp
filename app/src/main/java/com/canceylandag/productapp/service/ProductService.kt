package com.canceylandag.productapp.service

import com.canceylandag.productapp.model.ProductModel
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductService {

    @GET("/products")
    suspend fun getList():Response<ProductModel>

    @GET("/products/categories")
    suspend fun getCaegories():Response<List<String>>

    @GET("/products/category/{category}")
    suspend fun getProductOfCategories(@Path("category")category:String):Response<ProductModel>
}