package com.siekang.data.remote.dto

import androidx.annotation.DrawableRes

data class QuizzDto(
    val question: String,
    @DrawableRes val quizzImage: Int,
    val correctAnswer: String,
    val possibleAnswers: List<String>
) {
    override fun toString(): String {
        return "Quizz(question='$question', quizzImage=$quizzImage, correctAnswer=$correctAnswer, possibleAnswers=$possibleAnswers)"
    }
}
