package com.koperko.jll.di.modules

import com.koperko.jll.BASE_URL
import com.koperko.jll.network.GithubApi
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single {
        OkHttpClient.Builder().build()
    }

    single {
        Retrofit.Builder()
            .client(get())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
    }

    single {
        val retrofit: Retrofit = get()
        retrofit.create(GithubApi::class.java)
    }
}