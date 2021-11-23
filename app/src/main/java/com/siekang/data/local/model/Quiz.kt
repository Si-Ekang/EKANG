package com.siekang.data.local.model

import android.os.Parcelable
import androidx.annotation.DrawableRes
import com.siekang.data.remote.dto.Question
import kotlinx.parcelize.Parcelize

@Parcelize
data class Quiz(
    val id: String,
    val question: String,
    @DrawableRes val quizImage: Int,
    val correctAnswer: String,
    val possibleAnswers: List<String>
) : Parcelable

fun Question.toModel(): Quiz = Quiz(id, title, 0, answer, suggestions)