package com.siekang.ui.mainactivity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.siekang.data.IRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val repository: IRepository
) : ViewModel() {

    private var searchJob: Job = Job()

    fun searchWord(charSequence: CharSequence) {

        Timber.e("Cancel all running jobs")
        viewModelScope.coroutineContext.cancelChildren()

        Timber.d("searchWord() : $charSequence")
        val word = charSequence.toString()

        searchJob = viewModelScope.launch(Dispatchers.IO) {

            // Make query to repository and retrieve its translation
            val list = repository.getWordTranslation(word)

            withContext(Dispatchers.Main) {
                Timber.d("$list")
            }
        }

    }
}