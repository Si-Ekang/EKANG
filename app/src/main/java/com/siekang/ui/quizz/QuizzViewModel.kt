package com.siekang.ui.quizz

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class QuizzViewModel @Inject constructor() : ViewModel() {

    private val questionCountProgress: MutableLiveData<Int> = MutableLiveData()
    private val isAnswerSelected: MutableLiveData<Boolean> = MutableLiveData()

    init {
        questionCountProgress.value = 0
    }

    ///////////////////////////
    //
    // Observers
    //
    ///////////////////////////
    fun isAnswerSelected(): LiveData<Boolean> {
        return isAnswerSelected
    }


    ///////////////////////////
    //
    // Class Methods
    //
    ///////////////////////////
    fun isAnswerSelected(isSelected: Boolean) {
        isAnswerSelected.value = isSelected
    }


    companion object {
        val MAX_QUESTION_COUNT: Int = 15
    }
}