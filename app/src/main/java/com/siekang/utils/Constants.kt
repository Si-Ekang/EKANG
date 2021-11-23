package com.siekang.utils

import com.siekang.R
import com.siekang.data.local.model.Quiz

object Constants {

    @ExperimentalStdlibApi
    fun getPreloadDtoQuizzes(): List<Quiz> {
        val quizzes = mutableListOf<Quiz>() as ArrayList

        // 1
        quizzes.add(
            Quiz(
                "What is the capital of France ? ",
                R.drawable.ic_home,
                "Paris",
                buildList(4) {
                    add("Tokyo")
                    add("Paris")
                    add("New-York")
                    add("Sydney")
                })
        )

        // 2
        quizzes.add(
            Quiz(
                "What is the capital of Japan ? ",
                R.drawable.ic_home,
                "Tokyo",
                buildList(4) {
                    add("Tokyo")
                    add("Paris")
                    add("New-York")
                    add("Sydney")
                })
        )

        // 3
        quizzes.add(
            Quiz(
                "Which company has been created by Elon Musk ? ",
                R.drawable.ic_home,
                "Tesla",
                buildList(4) {
                    add("Chrysler")
                    add("Renault")
                    add("Tesla")
                    add("General Motors")
                })
        )

        // 4
        quizzes.add(
            Quiz(
                "Where is the Sahara Desert",
                R.drawable.ic_home,
                "Africa",
                buildList(4) {
                    add("South America")
                    add("Asia")
                    add("Europe")
                    add("Africa")
                })
        )

        // 5
        quizzes.add(
            Quiz(
                "What is the capital of the United States of America ? ",
                R.drawable.ic_home,
                "New-York",
                buildList(4) {
                    add("Tokyo")
                    add("Paris")
                    add("New-York")
                    add("Sydney")
                })
        )

        return quizzes

    }
}