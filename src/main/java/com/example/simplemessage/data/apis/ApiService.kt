package com.example.simplemessage.data.apis

import com.example.simplemessage.data.models.Message
import com.example.simplemessage.util.Consts
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET(Consts.key)
    fun getPosts(): Response<Message>


}