package com.siekang.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.siekang.data.local.model.User

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User)

    /* Method to fetch contacts stored locally */
    @Query("SELECT * FROM user")
    suspend fun getUser(): User

    @Query("DELETE FROM user")
    fun deleteAll()
}