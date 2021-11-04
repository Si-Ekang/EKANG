package com.siekang.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [],
    version = 1,
    exportSchema = false
)
abstract class SiEkangDatabase : RoomDatabase() {
    companion object {
        const val DATABASE_NAME = "si_ekang"
    }


}