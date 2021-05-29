package com.romix.videoplayer.retrofit

import android.content.res.Configuration
import com.romix.videoplayer.BuildConfig
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitClient {
    private const val API_KEY = "c0902ba53738059a5b54a13870db88a1"
    private var retrofit: Retrofit? = null
    private val okHttpClient: OkHttpClient
    private val converter: Converter.Factory
    private val callAdapter: CallAdapter.Factory

    init {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
            else HttpLoggingInterceptor.Level.NONE

        okHttpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor { chain ->
                val request = chain.request()
                val requestBuilder = request.newBuilder()
                    .header("Authorization", "Bearer $API_KEY")
                    .build()
                return@addInterceptor chain.proceed(requestBuilder)
            }
            .build()

        converter = GsonConverterFactory.create()
        callAdapter = RxJava2CallAdapterFactory.create()
    }

    fun getVideoClient(url: String): Retrofit {
        retrofit = retrofit ?: (
                Retrofit.Builder()
                    .baseUrl(url)
                    .client(okHttpClient)
                    .addConverterFactory(converter)
                    .addCallAdapterFactory(callAdapter)
                    .build()
                )

        return retrofit!!
    }
}