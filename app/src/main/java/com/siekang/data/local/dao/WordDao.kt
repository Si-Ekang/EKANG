package com.siekang.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.siekang.data.local.model.User

@Dao
interface WordDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User)

    /* Method to fetch contacts stored locally */
    @Query("SELECT translations FROM word WHERE word_value = :word")
    suspend fun getTranslations(word: String): List<String>

    @Query("DELETE FROM user")
    fun deleteAll()
}