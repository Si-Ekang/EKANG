package com.siekang.ui.library

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.siekang.data.IRepository
import com.siekang.data.remote.dto.Translation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LibraryViewModel @Inject constructor(
    private val repository: IRepository
) : ViewModel() {

    private val translations: MutableLiveData<List<Translation>> = MutableLiveData()

    init {
        Timber.d("init")
        currentIndex = 1
        hasNextItemsInRemote = true
    }

    fun getTranslations(): LiveData<List<Translation>> = translations

    fun fetchTranslations(shouldIndexPlusOne: Boolean) {
        if (!hasNextItemsInRemote) {
            Timber.e("No more items available in remote. Avoid REST call operation.")
            return
        }

        viewModelScope.launch(IO) {
            try {
                supervisorScope {

                    if (!shouldIndexPlusOne) currentIndex else currentIndex += 1


                    val response = repository.getTranslations(currentIndex, 10)

                    if (null == response) {
                        Timber.e("Response is null")
                    }

                    withContext(Main) {
                        translations.value = response
                    }
                }
            } catch (exception: Exception) {
                exception.printStackTrace()
                Timber.e(exception.message)

                if (exception is NullPointerException) {
                    hasNextItemsInRemote = false
                }
            }
        }
    }

    fun resetCurrentIndex() { currentIndex = 1}
    fun resetHasNextIndex() { hasNextItemsInRemote = true}

    companion object {
        var currentIndex = 1
        var hasNextItemsInRemote: Boolean = false
    }
}