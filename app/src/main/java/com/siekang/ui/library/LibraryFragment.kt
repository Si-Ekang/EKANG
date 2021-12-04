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

    private var mAdapter: LibraryAdapter? = null


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

        setupRecyclerView()

        initViewModelObservers()

        mViewModel.fetchTranslations(false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _viewBinding = null
    }


    private fun setupRecyclerView() {
        binding.rvLibrary.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (!recyclerView.canScrollVertically(1)
                    && newState == RecyclerView.SCROLL_STATE_IDLE
                ) {
                    Timber.e("end")

                    showProgressBar()
                    mViewModel.fetchTranslations(true)
                }
            }
        })
    }

    private fun initViewModelObservers() {
        mViewModel.getTranslations().observe(viewLifecycleOwner, {
            Timber.d("$it")
            hideProgressBar()
            showRecyclerView()
            bindRecyclerView(it)
        })
    }

    private fun bindRecyclerView(translations: List<Translation>) {
        val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(activity)
        binding.rvLibrary.layoutManager = mLayoutManager
        binding.rvLibrary.itemAnimator = DefaultItemAnimator()

        if (null == mAdapter)
            mAdapter = LibraryAdapter()
        binding.rvLibrary.adapter = mAdapter

        mAdapter?.addAll(translations)
        mAdapter?.notifyDataSetChanged()
    }

    private fun showProgressBar() {
        if (binding.progressBar.visibility == View.INVISIBLE || binding.rvLibrary.visibility == View.GONE) {
            binding.progressBar.visibility = View.VISIBLE
        }
    }

    private fun hideProgressBar() {
        if (binding.progressBar.visibility == View.VISIBLE) {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun showRecyclerView() {
        if (binding.rvLibrary.visibility == View.INVISIBLE || binding.rvLibrary.visibility == View.GONE) {
            binding.rvLibrary.visibility = View.VISIBLE
        }
    }

    private fun hideRecyclerView() {
        if (binding.rvLibrary.visibility == View.VISIBLE) {
            binding.rvLibrary.visibility = View.INVISIBLE
        }

    }
}