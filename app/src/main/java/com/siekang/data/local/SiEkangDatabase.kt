package com.siekang.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.siekang.data.local.converters.TranslationsTypeConverter
import com.siekang.data.local.dao.UserDao
import com.siekang.data.local.dao.WordDao
import com.siekang.data.local.model.User
import com.siekang.data.local.model.Word

@Database(
    entities = [User::class, Word::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(TranslationsTypeConverter::class)
abstract class SiEkangDatabase : RoomDatabase() {
    companion object {
        const val DATABASE_NAME = "si_ekang"
    }

    abstract fun getUserDao(): UserDao

    abstract fun getWordDao(): WordDao
}