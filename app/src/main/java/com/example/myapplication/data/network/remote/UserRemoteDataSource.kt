package com.example.myapplication.data.network.remote

import com.example.myapplication.data.network.MyApi
import javax.inject.Inject

class UserRemoteDataSource @Inject constructor(
    private val myApi: MyApi
): BaseDataSource() {

    suspend fun users(pageNumber: String) {
        getResult{ myApi.users( pageNumber)}
    }

    suspend fun getUsers(pageNumber: String) = getResult { myApi.users( pageNumber)}

}