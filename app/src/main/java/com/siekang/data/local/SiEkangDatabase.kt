package com.siekang.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.siekang.data.local.dao.UserDao
import com.siekang.data.local.model.User

@Database(
    entities = [User::class],
    version = 1,
    exportSchema = false
)
abstract class SiEkangDatabase : RoomDatabase() {
    companion object {
        const val DATABASE_NAME = "si_ekang"
    }

    abstract fun getUserDao(): UserDao
}