package com.siekang.ui.library

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.siekang.data.remote.dto.Translation
import com.siekang.databinding.FragmentLibraryBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class LibraryFragment : Fragment() {

    private var _viewBinding: FragmentLibraryBinding? = null
    private val binding get() = _viewBinding!!

    private val mViewModel: LibraryViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _viewBinding = FragmentLibraryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModelObservers()

        mViewModel.fetchTranslations()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _viewBinding = null
    }


    private fun initViewModelObservers() {
        mViewModel.getTranslations().observe(viewLifecycleOwner, {
            Timber.d("$it")
            bindRecyclerView(it)
        })
    }

    private fun bindRecyclerView(translations: List<Translation>) {
        val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(activity)
        binding.rvLibrary.layoutManager = mLayoutManager
        binding.rvLibrary.itemAnimator = DefaultItemAnimator()

        val mAdapter = LibraryAdapter(translations)
        binding.rvLibrary.adapter = mAdapter
    }
}