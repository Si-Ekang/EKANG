package com.siekang.utils

import com.siekang.R
import com.siekang.data.local.model.Quizz

object Constants {

    @ExperimentalStdlibApi
    fun getPreloadDtoQuizzes(): List<Quizz> {
        val quizzes = mutableListOf<Quizz>() as ArrayList

        // 1
        quizzes.add(
            Quizz(
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
            Quizz(
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
            Quizz(
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
            Quizz(
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
            Quizz(
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