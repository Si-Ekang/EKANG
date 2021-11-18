package com.siekang.data.local.model

import android.os.Parcelable
import androidx.annotation.DrawableRes
import com.siekang.data.remote.dto.QuizzDto
import kotlinx.parcelize.Parcelize

@Parcelize
data class Quizz(
    val question: String,
    @DrawableRes val quizzImage: Int,
    val correctAnswer: String,
    val possibleAnswers: List<String>
) : Parcelable {

    constructor(quizzDto: QuizzDto) : this(
        quizzDto.question,
        quizzDto.quizzImage,
        quizzDto.correctAnswer,
        quizzDto.possibleAnswers
    )


    override fun toString(): String {
        return "Quizz(question='$question', quizzImage=$quizzImage, correctAnswer=$correctAnswer, possibleAnswers=$possibleAnswers)"
    }
}