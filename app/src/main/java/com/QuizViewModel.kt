package com.bignerdranch.android.geoquiz.com


import androidx.lifecycle.ViewModel
import com.bignerdranch.android.geoquiz.Question
import com.bignerdranch.android.mygeoquiz.R


class QuizViewModel : ViewModel() {

    var currentIndex = 0


    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true)
    )

    val currentQuestionAnswer: Boolean
        get() = questionBank[currentIndex].answer

    val currentQuestionText: Int
        get() = questionBank[currentIndex].textResId

    fun moveToNext() {
        currentIndex = (currentIndex + 1) % questionBank.size
    }

    fun moveToBack() {
        currentIndex = if (currentIndex > 0) {
            (currentIndex - 1) % questionBank.size
        } else {
            questionBank.size - 1
        }
    }
}

