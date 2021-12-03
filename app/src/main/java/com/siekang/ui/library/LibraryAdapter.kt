package com.siekang.ui.library

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.siekang.data.remote.dto.Translation
import com.siekang.databinding.RowLibraryBinding

class LibraryAdapter(
    private val items: List<Translation>
) : RecyclerView.Adapter<RowLibraryViewHolder>() {

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
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size ?: 0
    }
}