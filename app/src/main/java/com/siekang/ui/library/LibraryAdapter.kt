package com.siekang.ui.library

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.siekang.data.remote.dto.Translation
import com.siekang.databinding.RowLibraryBinding

class LibraryAdapter() : RecyclerView.Adapter<RowLibraryViewHolder>() {

    private var mTranslationItems: ArrayList<Translation> =
        mutableListOf<Translation>() as ArrayList<Translation>

    fun addAll(items: List<Translation>) {
        mTranslationItems.addAll(items)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowLibraryViewHolder {
        val binding: RowLibraryBinding =
            RowLibraryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

        return RowLibraryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RowLibraryViewHolder, position: Int) {
        holder.bind(mTranslationItems[position])
    }

    override fun getItemCount(): Int {
        return mTranslationItems.size ?: 0
    }
}