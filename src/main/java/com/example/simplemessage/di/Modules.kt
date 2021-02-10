package com.example.simplemessage.di

import android.app.Application
import androidx.room.Room
import com.example.simplemessage.data.apis.ApiService
import com.example.simplemessage.data.models.Post
import com.example.simplemessage.db.MessagesDao
import com.example.simplemessage.db.MessagesDatabase
import com.example.simplemessage.feature.messageslist.MessagesListAdapter
import com.example.simplemessage.feature.messages.MessagesRepository
import com.example.simplemessage.feature.messages.MessagesViewModel
import com.example.simplemessage.util.Consts
import com.example.simplemessage.util.NetworkUtil
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import org.koin.android.viewmodel.dsl.viewModel
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

    fun provideService(retrofit: Retrofit) = retrofit.create(ApiService::class.java)

    factory { Consts.url }
    single { provideRetrofit(get()) }
    single { provideService(get()) }
}

val databaseModule = module {
    fun provideDatabase(app: Application): MessagesDatabase =
        Room.databaseBuilder(app, MessagesDatabase::class.java, "messages_database").build()

    fun provideDao(db: MessagesDatabase): MessagesDao =
        db.getDao()

    single { provideDatabase(get()) }
    single { provideDao(get()) }
}

val architectureModule = module {
    single { MessagesRepository(get(), get(), get()) }
    viewModel { MessagesViewModel(get()) }
}

val adaptersModule = module {
    factory { (click: (post: Post) -> Unit) -> MessagesListAdapter(click) }
}

val utilModule = module {
    single { NetworkUtil(get()) }
}