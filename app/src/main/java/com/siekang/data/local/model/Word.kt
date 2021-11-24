package com.siekang.data.local.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "word")
@Parcelize
data class Word(
    @PrimaryKey
    @ColumnInfo(name = "_id")
    val id: Int = 0,
    @ColumnInfo(name = "word_value")
    val wordValue: String,
    @ColumnInfo(name = "translations")
    val translations: List<String>
) : Parcelable


