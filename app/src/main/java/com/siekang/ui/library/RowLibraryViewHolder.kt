package com.siekang.ui.library

import androidx.recyclerview.widget.RecyclerView
import com.siekang.data.remote.dto.Translation
import com.siekang.databinding.RowLibraryBinding

class RowLibraryViewHolder(
    val binding: RowLibraryBinding,
    listener: LibraryClickListener
) : RecyclerView.ViewHolder(binding.root) {

    private val viewBinding: RowLibraryBinding get() = binding

    init {
        itemView.run {
            binding.listener = listener
        }
    }

    fun bind(translation: Translation) {
        viewBinding.translation = translation
    }
}