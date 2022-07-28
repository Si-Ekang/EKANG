package com.siekang.utils

import com.siekang.R
import com.siekang.data.local.model.Quiz

object Constants {

    const val BASE_ENDPOINT_SI_EKANG: String = "https://ekang-api.herokuapp.com/v1/"

    const val DATASTORE_SI_EKANG_NAME = "SI_EKANG_DATASTORE"

    @JvmStatic
    fun getPreloadDtoQuizzes(): List<Quiz> {
        val quizzes = mutableListOf<Quiz>() as ArrayList

        // 1
        quizzes.add(
            Quiz(
                "0",
                "What is the capital of France ? ",
                R.drawable.ic_home,
                "Paris",
                listOf(
                    "Tokyo",
                    "Paris",
                    "New-York",
                    "Sydney"
                )
            )
        )

        // 2
        quizzes.add(
            Quiz(
                "1",
                "What is the capital of Japan ? ",
                R.drawable.ic_home,
                "Tokyo",
                listOf(
                    "Tokyo",
                    "Paris",
                    "New-York",
                    "Sydney"
                )
            )
        )

        // 3
        quizzes.add(
            Quiz(
                "2",
                "Which company has been created by Elon Musk ? ",
                R.drawable.ic_home,
                "Tesla",
                listOf(
                    "Chrysler",
                    "Renault",
                    "Tesla",
                    "General Motors"
                )
            )
        )

        // 4
        quizzes.add(
            Quiz(
                "3",
                "Where is the Sahara Desert",
                R.drawable.ic_home,
                "Africa",
                listOf(
                    "South America",
                    "Asia",
                    "Europe",
                    "Africa"
                )
            )
        )

        // 5
        quizzes.add(
            Quiz(
                "4",
                "What is the capital of the United States of America ? ",
                R.drawable.ic_home,
                "New-York",
                listOf(
                    "Tokyo",
                    "Paris",
                    "New-York",
                    "Sydney"
                )
            )
        )

        return quizzes

    }
}