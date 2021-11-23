package com.siekang.ui.quizz

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.siekang.data.IRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
class QuizzViewModel @Inject constructor(private val repository: IRepository) : ViewModel() {

    private val shouldShowError: MutableLiveData<Boolean> = MutableLiveData()
    private val shouldDisplayQuiz: MutableLiveData<Boolean> = MutableLiveData()

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

    fun getQuestions() {
        Timber.d("getQuestions()")
        viewModelScope.launch(Dispatchers.IO) {
            try {
                supervisorScope {
                    val response = repository.getQuestions()

                    if (null == response) {
                        shouldShowError.value = true
                    } else {
                        withContext(Dispatchers.Main) {
                            shouldDisplayQuiz.value = true
                        }
                    }
                }
            } catch (exception: Exception) {
                exception.printStackTrace()

                if (exception is UnknownHostException) {
                    Timber.e("Check your connection cannot reach host")
                }

                Timber.e(exception.message)
            }
        }
    }

    companion object {
        val MAX_QUESTION_COUNT: Int = 15
    }
}