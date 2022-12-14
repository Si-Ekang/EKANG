package com.siekang.ui.mainactivity

import android.text.Spanned
import android.text.SpannedString
import androidx.core.text.HtmlCompat
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

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        exception.printStackTrace()

        Timber.e(
            HtmlCompat.toHtml(
                SpannedString.valueOf("exception caught | caused by : <b>${exception.cause}</b> with message : <b>${exception.message}</b>"),
                Spanned.SPAN_PARAGRAPH
            )
        )
    }

    private var searchJob: Job = Job()


    fun getTranslations(page: Int, size: Int) {

        Timber.e("Cancel all running jobs")
        viewModelScope.coroutineContext.cancelChildren()

        Timber.d("getTranslations() : page number : $page - size : $size")


        searchJob = viewModelScope.launch(IO + coroutineExceptionHandler) {

            supervisorScope {
                try {
                    delay(800)

                    // Make query to repository and retrieve its translation
                    val response = repository.getTranslations(page, size)

                    withContext(Main) {
                        Timber.d("$response")
                    }
                } catch (exception: Exception) {
                    exception.printStackTrace()
                }
            }
        }
    }

    fun searchWord(charSequence: CharSequence) {

        Timber.e("Cancel all running jobs")
        viewModelScope.coroutineContext.cancelChildren()

        Timber.d("searchWord() : $charSequence")
        val word = charSequence.toString()

        searchJob = viewModelScope.launch(IO + coroutineExceptionHandler) {

            delay(800)

            // Make query to repository and retrieve its translation
            val list = repository.getWordTranslation(word)

            withContext(Main) {
                Timber.d("$list")
            }
        }
    }
}