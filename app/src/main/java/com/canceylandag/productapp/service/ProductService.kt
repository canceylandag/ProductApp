package com.canceylandag.productapp.service

import com.canceylandag.productapp.model.ProductModel
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface ProductService {

    @GET("/products")
    suspend fun getList():Response<ProductModel>

    @GET("/products/categories")
    suspend fun getCaegories():Response<List<String>>
}