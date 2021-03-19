package com.example.myapplication.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myapplication.ui.dashboard.navigation.home.data.DataItem

@Dao
interface UserDao {

    @Query("SELECT * FROM users")
    fun getAllUsers() : LiveData<List<DataItem>>

    @Query("SELECT * FROM users WHERE id = :id")
    fun getUser(id: Int): LiveData<DataItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users: List<DataItem>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: DataItem)


}