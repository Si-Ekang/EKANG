package com.siekang.ui.library

import android.content.res.AssetFileDescriptor
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.siekang.R
import com.siekang.data.remote.dto.Translation
import com.siekang.databinding.FragmentLibraryBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber


@AndroidEntryPoint
class LibraryFragment : Fragment(), LibraryClickListener {

    private var _viewBinding: FragmentLibraryBinding? = null
    private val binding get() = _viewBinding!!

    private val mViewModel: LibraryViewModel by viewModels()

    private var mAdapter: LibraryAdapter? = null

    var m = MediaPlayer()

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
        Timber.d("onViewCreated()")

        setupRecyclerView()

        initViewModelObservers()

        mViewModel.fetchTranslations(false)
    }

    override fun onDestroy() {
        Timber.e("onDestroy()")
        mAdapter?.clearList()
        super.onDestroy()
    }

    override fun onDestroyView() {
        Timber.e("onDestroyView()")

        mViewModel.resetCurrentIndex()
        mViewModel.resetHasNextIndex()

        _viewBinding = null
        super.onDestroyView()
    }


    private fun setupRecyclerView() {
        var loading: Boolean = true
        var pastVisiblesItems: Int
        var visibleItemCount: Int
        var totalItemCount: Int


        val mLayoutManager = LinearLayoutManager(activity)
        binding.rvLibrary.layoutManager = mLayoutManager
        binding.rvLibrary.itemAnimator = DefaultItemAnimator()

        // Pagination
        binding.rvLibrary.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (dy > 0) { //check for scroll down
                    visibleItemCount = mLayoutManager.childCount;
                    totalItemCount = mLayoutManager.itemCount;
                    pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition()

                    if (loading) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            loading = false
                            Timber.d("Last Item Wow !")

                            // Do pagination.. i.e. fetch new data

                            loading = true
                        }
                    }
                }
            }

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
        mViewModel.getTranslations().observe(viewLifecycleOwner) {
            Timber.d("$it")
            hideProgressBar()
            showRecyclerView()
            bindRecyclerView(it)
        }
    }

    private fun bindRecyclerView(translations: List<Translation>) {

        setupRecyclerView()

        if (null == mAdapter)
            mAdapter = LibraryAdapter(this)
        binding.rvLibrary.adapter = mAdapter

        mAdapter?.addAll(translations)
        // mAdapter?.notifyDataSetChanged()
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

    private fun playWord(view: View, wordFilename: String) {
        Timber.d("playWord() | $wordFilename")

        val seekBar = view.findViewById<SeekBar>(R.id.seekBar)
        val mHandler: Handler = Handler(Looper.getMainLooper())

        /*

        var updateSongTime = Thread(object : Runnable {
            override fun run() {
                try {

                    val getCurrent = m.currentPosition
                    *//*startTimeText?.setText(String.format("%d:%d",
                        TimeUnit.MILLISECONDS.toMinutes(getCurrent?.toLong() as Long),
                        TimeUnit.MILLISECONDS.toSeconds(getCurrent?.toLong()) -
                                TimeUnit.MINUTES.toSeconds(
                                    TimeUnit.MILLISECONDS.toMinutes(getCurrent?.toLong()))))*//*
                    seekBar?.progress = getCurrent
                    Handler(Looper.getMainLooper()).postDelayed(this, 1000)
                } catch (exception: Exception) {
                    exception.printStackTrace()
                }
            }
        })
        updateSongTime.start()*/

        try {

            if (m.isPlaying) {
                m.stop()
                m.release()
                m = MediaPlayer()
            }

            val descriptor: AssetFileDescriptor =
                requireActivity().getAssets().openFd("audio/$wordFilename.mp3")

            m.setDataSource(descriptor.fileDescriptor, descriptor.startOffset, descriptor.length)
            descriptor.close()

            m.prepare()
            // m.setVolume(0.5f, 0.5f)
            m.setLooping(false)

            // MediaPlayer listener
            m.setOnPreparedListener {
                seekBar.visibility = View.VISIBLE
                seekBar.setMax(m.duration / 1000); // where mFileDuration is mMediaPlayer.getDuration();

                mHandler.postDelayed({
                    seekBar.setProgress(m.currentPosition)
                }, 500)

            }

            m.setOnCompletionListener {
                Timber.d("song is finished")
                m.reset()
                seekBar.progress = 0
                seekBar.visibility = View.GONE
            }

            m.start()

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onLibraryItemClicked(view: View, item: Translation) {
        Timber.d("clicked on $item")

        when (item.fang) {
            "kisin",
            "atun",
            "esua" -> {
                // Should play song
                playWord(view, item.fang)
            }
            else -> {
                Timber.e("Else branch")
            }
        }
    }
}