package com.siekang.ui.quizz

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.siekang.R
import com.siekang.data.local.model.Quiz
import com.siekang.databinding.ActivityQuizzBinding
import com.siekang.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.util.*

@ExperimentalStdlibApi
@AndroidEntryPoint
class QuizzActivity : AppCompatActivity(), View.OnClickListener, QuizzFragment.OnCorrectAnswer {

    private var _viewBinding: ActivityQuizzBinding? = null
    private val binding get() = _viewBinding!!

    private val mViewModel: QuizzViewModel by viewModels()

    // ViewPager
    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private var mViewPagerAdapter: QuizzViewPagerAdapter? = null
    private var mFragmentList: ArrayList<Fragment>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _viewBinding = ActivityQuizzBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
        setListeners()
        initViewModelObservers()

        setupQuizzList()
        initViewPager()
    }

    private fun initViews() {
        binding.progressBarQuestionProgression.max = QuizzViewModel.MAX_QUESTION_COUNT
        binding.progressBarQuestionProgression.progress = 1

        binding.tvQuestionCount.text = getString(
            R.string.question_count_status, 1, QuizzViewModel.MAX_QUESTION_COUNT
        )
    }

    private fun setListeners() {
        binding.btnValidate.setOnClickListener(this)
    }


    private fun initViewModelObservers() {
        mViewModel.isAnswerSelected().observe(this, {
            if (!binding.btnValidate.isEnabled)
                binding.btnValidate.isEnabled = !binding.btnValidate.isEnabled
        })
    }

    @ExperimentalStdlibApi
    private fun setupQuizzList() {
        mFragmentList = (mutableListOf<Fragment>() as ArrayList).apply {

            for (element: Quiz in Constants.getPreloadDtoQuizzes()) {
                this.add(QuizzFragment.newInstance(element))
            }
        }
    }

    private fun initViewPager() {
        Timber.d("initViewPager()")

        mViewPagerAdapter = QuizzViewPagerAdapter(this, mFragmentList!!)
        binding.viewPager2.adapter = mViewPagerAdapter

        binding.viewPager2.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {

                Timber.e("Update question count view")

                // Update question count view
                binding.tvQuestionCount.text = getString(
                    R.string.question_count_status,
                    position + 1,
                    QuizzViewModel.MAX_QUESTION_COUNT
                )
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                // Ignored
            }

            override fun onPageScrollStateChanged(state: Int) {
                // Ignored
            }
        })

//        binding.contentMainHeader.viewPager2.setOnTouchListener(OnTouchListener { arg0, arg1 -> true })
        // Ref : https://stackoverflow.com/questions/7814017/is-it-possible-to-disable-scrolling-on-a-viewpager
        binding.viewPager2.isUserInputEnabled = false

        binding.viewPager2.setPageTransformer { page, _ ->
            page.alpha = 0f
            page.visibility = View.VISIBLE

            // Start Animation for a short period of time
            page.animate()
                .alpha(1f).duration =
                page.resources.getInteger(android.R.integer.config_shortAnimTime).toLong()
        }
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_validate -> {
                if (null != mFragmentList) {
                    (mFragmentList!![binding.viewPager2.currentItem] as QuizzFragment).checkAnswers()
                }
            }
        }
    }

    override fun correctAnswer() {
        // Check if "current position" equal "number of elements
        if (binding.viewPager2.currentItem
            != binding.viewPager2.adapter?.itemCount?.minus(
                1
            )
        ) {
            Timber.e("onClick - Update")
            binding.viewPager2.currentItem = binding.viewPager2.currentItem + 1
            binding.progressBarQuestionProgression.progress =
                binding.progressBarQuestionProgression.progress + 1
        } else {
            // Finished
            Timber.e("onClick - Finished")
            finish()
        }
    }

    companion object {
        val TAG: String = QuizzActivity::class.java.simpleName
    }
}