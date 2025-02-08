package ru.elkael.weatherapp.data.network.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.elkael.weatherapp.BuildConfig

object ApiFactory {
    private const val BASE_URL = " https://api.weatherapi.com/v1/"
    private const val KEY = "key"

    private val login by lazy {
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    private val okhttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                login.intercept(chain)

                val originalRequest = chain.request()
                val url = originalRequest.url.newBuilder()
                    .addQueryParameter(KEY, BuildConfig.WEATHER_API_KEY)
                    .build()
                val newRequest = originalRequest.newBuilder().url(url).build()
                chain.proceed(newRequest)
            }
            .build()
    }

    private val retrofit by lazy {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(okhttpClient)
            .baseUrl(BASE_URL)
            .build()
    }

    val apiService: ApiService by lazy { retrofit.create(ApiService::class.java) }
}