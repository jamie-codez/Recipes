package com.softech.code.recipe.api

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object FoodClient {
    private const val baseUrl = "https://www.themealdb.com/api/json/v1/1/"
    @JvmStatic
    fun getFoodClient():Retrofit{
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(provideOkHttp())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    @JvmStatic
    private fun provideLoggingInterceptor():Interceptor{
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }
    @JvmStatic
    private fun provideOkHttp():OkHttpClient{
        return OkHttpClient.Builder()
            .connectTimeout(30,TimeUnit.SECONDS)
            .writeTimeout(30,TimeUnit.SECONDS)
            .readTimeout(30,TimeUnit.SECONDS)
            .addNetworkInterceptor(provideLoggingInterceptor())
            .build()
    }
}