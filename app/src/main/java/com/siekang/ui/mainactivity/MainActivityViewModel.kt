package com.siekang.ui.mainactivity

import androidx.lifecycle.ViewModel
import com.siekang.data.IRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val repository: IRepository
) : ViewModel() {
}