package com.siekang.ui.library

import android.view.View
import com.siekang.data.remote.dto.Translation

interface LibraryClickListener {
    fun onLibraryItemClicked(view: View, item: Translation)
}