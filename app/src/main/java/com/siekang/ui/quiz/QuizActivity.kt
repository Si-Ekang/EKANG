package com.siekang.ui.quiz

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.view.animation.DecelerateInterpolator
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.siekang.R
import com.siekang.core.utils.CompatibilityManager
import com.siekang.data.local.model.Quiz
import com.siekang.databinding.ActivityQuizBinding
import com.siekang.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.util.*

@ExperimentalStdlibApi
@AndroidEntryPoint
class QuizActivity : AppCompatActivity(), View.OnClickListener, QuizFragment.OnCorrectAnswer {

    private var _viewBinding: ActivityQuizBinding? = null
    private val binding get() = _viewBinding!!

    private val mViewModel: QuizViewModel by viewModels()

    // ViewPager
    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private var mViewPagerAdapter: QuizViewPagerAdapter? = null
    private var mFragmentList: ArrayList<Fragment>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _viewBinding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
        setListeners()
        initViewModelObservers()

        setupQuizzList()
        initViewPager()

        mViewModel.getQuestions()
    }

    private fun initViews() {
        /*binding.progressBarQuestionProgression.max = QuizViewModel.MAX_QUESTION_COUNT
        binding.progressBarQuestionProgression.progress = 1*/

        binding.progressBarQuestionProgression.max =
            QuizViewModel.MAX_QUESTION_COUNT * PROGRESS_MULTIPLIER
        binding.progressBarQuestionProgression.progress = 1 * PROGRESS_MULTIPLIER

        binding.tvQuestionCount.text = getString(
            R.string.question_count_status, 1, QuizViewModel.MAX_QUESTION_COUNT
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
                this.add(QuizFragment.newInstance(element))
            }
        }
    }

    private fun initViewPager() {
        Timber.d("initViewPager()")

        mViewPagerAdapter = QuizViewPagerAdapter(this, mFragmentList!!)
        binding.viewPager2.adapter = mViewPagerAdapter

        binding.viewPager2.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {

                Timber.e("Update question count view")

                // Update question count view
                binding.tvQuestionCount.text = getString(
                    R.string.question_count_status,
                    position + 1,
                    QuizViewModel.MAX_QUESTION_COUNT
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
                    (mFragmentList!![binding.viewPager2.currentItem] as QuizFragment).checkAnswers()
                }
            }
        }
    }

    @SuppressLint("NewApi")
    override fun correctAnswer() {
        // Check if "current position" equal "number of elements
        if (binding.viewPager2.currentItem
            != binding.viewPager2.adapter?.itemCount?.minus(
                1
            )
        ) {
            Timber.e("onClick - Update")

            // Change viewpager current view
            binding.viewPager2.currentItem = binding.viewPager2.currentItem + 1

            // Apply update animation to porgress bar
            /*binding.progressBarQuestionProgression.progress =
                binding.progressBarQuestionProgression.progress + 1*/

            /* Source : https://stackoverflow.com/questions/8035682/animate-progressbar-update-in-android */
            if (!CompatibilityManager.isNougat()) {
                ObjectAnimator
                    .ofInt(
                        binding.progressBarQuestionProgression,
                        "progress",
                        // From value
                        binding.progressBarQuestionProgression.progress,
                        // To value
                        binding.progressBarQuestionProgression.progress + (1 * PROGRESS_MULTIPLIER)
                    ).apply {
                        duration = 700L
                        interpolator = DecelerateInterpolator()
                    }.start()
            } else {
                // Android API 24 (N) required to use this implementation
                binding.progressBarQuestionProgression.setProgress(
                    binding.progressBarQuestionProgression.progress + (1 * PROGRESS_MULTIPLIER),
                    true
                )
            }

        } else {
            // Finished
            Timber.e("onClick - Finished")
            finish()
        }
    }

    companion object {
        val TAG: String = QuizActivity::class.java.simpleName

        const val PROGRESS_MULTIPLIER: Int = 10000
    }
}