package com.siekang.ui.mainactivity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.siekang.data.IRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val repository: IRepository
) : ViewModel() {

    private var searchJob: Job = Job()


    fun getTranslations(page: Int, size: Int) {

        Timber.e("Cancel all running jobs")
        viewModelScope.coroutineContext.cancelChildren()

        Timber.d("getTranslations() : page number : $page - size : $size")

        searchJob = viewModelScope.launch(IO) {

            delay(800)

            // Make query to repository and retrieve its translation
            val response = repository.getTranslations(page, size)

            withContext(Main) {
                Timber.d("$response")
            }
        }

    }

    fun searchWord(charSequence: CharSequence) {

        Timber.e("Cancel all running jobs")
        viewModelScope.coroutineContext.cancelChildren()

        Timber.d("searchWord() : $charSequence")
        val word = charSequence.toString()

        searchJob = viewModelScope.launch(IO) {

            delay(800)

            // Make query to repository and retrieve its translation
            val list = repository.getWordTranslation(word)

            withContext(Main) {
                Timber.d("$list")
            }
        }
    }
}