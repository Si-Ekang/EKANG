package com.siekang.data.local.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "user")
@Parcelize
data class User(
    @PrimaryKey
    @ColumnInfo(name = "_id")
    val id: Int = 0,
    @ColumnInfo(name = "username")
    val username: String,
    @ColumnInfo(name = "password")
    val password: String,
    @ColumnInfo(name = "mailAddress")
    val mailAddress: String,
    @ColumnInfo(name = "phone")
    val phone: String
) : Parcelable {
    override fun toString(): String {
        return "User(id=$id, username='$username', password='$password', mailAddress='$mailAddress', phone='$phone')"
    }
}
