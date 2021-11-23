package com.siekang.data.local.model

import android.os.Parcelable
import androidx.annotation.DrawableRes
import com.siekang.data.remote.dto.QuizDto
import kotlinx.parcelize.Parcelize

@Parcelize
data class Quiz(
    val question: String,
    @DrawableRes val quizImage: Int,
    val correctAnswer: String,
    val possibleAnswers: List<String>
) : Parcelable

fun QuizDto.toModel(): Quiz = Quiz(question, quizImage, correctAnswer, possibleAnswers)