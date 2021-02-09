package com.example.simplemessage.di

import com.example.simplemessage.data.apis.ApiService
import com.example.simplemessage.feature.messages.MessagesActivity
import com.example.simplemessage.util.Consts
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import org.koin.dsl.module
import retrofit2.Retrofit

val retrofitModule = module {

    fun provideRetrofit(base_url: String): Retrofit {
        val content = MediaType.get("application/json")
        val json = Json { ignoreUnknownKeys = true }.asConverterFactory(content)
        return Retrofit.Builder()
            .baseUrl(base_url)
            .addConverterFactory(json)
            .build()
    }

    factory { Consts.url }
    single { provideRetrofit(get()) }

}