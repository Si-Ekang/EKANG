package com.siekang.ui.library

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.siekang.data.IRepository
import com.siekang.data.remote.dto.Translation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LibraryViewModel @Inject constructor(
    val repository: IRepository
) : ViewModel() {

    private val translations: MutableLiveData<List<Translation>> = MutableLiveData()

    fun getTranslations(): LiveData<List<Translation>> = translations

    fun fetchTranslations() {
        viewModelScope.launch(Dispatchers.IO) {
            try {

                supervisorScope {
                    val response = repository.getTranslations(1, 10)

                    if (null == response) {
                        Timber.e("Response is null")
                    }

                    withContext(Dispatchers.Main) {
                        translations.value = response
                    }
                }
            } catch (exception: Exception) {
                exception.printStackTrace()
            }
        }
    }
}