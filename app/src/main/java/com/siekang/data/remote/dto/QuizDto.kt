package com.siekang.data.remote.dto

import androidx.annotation.DrawableRes

data class QuizDto(
    val question: String,
    @DrawableRes val quizImage: Int,
    val correctAnswer: String,
    val possibleAnswers: List<String>
) {
    override fun toString(): String {
        return "QuizDto(question='$question', quizzImage=$quizImage, correctAnswer=$correctAnswer, possibleAnswers=$possibleAnswers)"
    }
}
