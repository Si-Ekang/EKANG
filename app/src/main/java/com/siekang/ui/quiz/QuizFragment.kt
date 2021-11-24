package com.siekang.ui.quiz

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textview.MaterialTextView
import com.siekang.core.views.ToggleButtonGroupTableLayout
import com.siekang.data.local.model.Quiz
import com.siekang.databinding.FragmentQuizzBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber


@AndroidEntryPoint
class QuizFragment : Fragment(), View.OnClickListener {

    companion object {

        private const val EXTRA_QUIZZ = "EXTRA_QUIZZ"

        fun newInstance(quizz: Quiz): QuizFragment {
            val args = Bundle()
            args.putParcelable(EXTRA_QUIZZ, quizz)
            val fragment = QuizFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private var _viewBinding: FragmentQuizzBinding? = null
    private val binding get() = _viewBinding!!
    private lateinit var tbGroup: ToggleButtonGroupTableLayout

    private val mViewModel: QuizViewModel by activityViewModels()
    private var selectedAnswer: String? = null

    private lateinit var item: Quiz

    /**
     * passing data between fragments
     */
    interface OnCorrectAnswer {
        fun correctAnswer()
    }

    private var listener: OnCorrectAnswer? = null

    @ExperimentalStdlibApi
    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is OnCorrectAnswer) {
            listener = context
        } else {
            throw ClassCastException("$context must implement ${QuizActivity.TAG}.OnCorrectAnswer");
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val extras = arguments

        if (extras == null) {
            Timber.e("bundle is null - check the data you are trying to pass through please !")
        } else {
            item = extras.getParcelable(EXTRA_QUIZZ)!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _viewBinding = FragmentQuizzBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tbGroup = binding.tbGroup

        binding.quiz = item
        binding.ivQuizPicture.setImageResource(item.quizImage)

        initViewModelObservers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _viewBinding = null
    }


    private fun initViewModelObservers() {
        tbGroup.getRadioButtonLiveData().observe(viewLifecycleOwner, {
            Timber.e("tbGroup.getRadioButtonLiveData()")

            Timber.d("${binding.tbGroup.getCheckedRadioButtonId()}")

            /*selectedAnswer =
                (requireActivity()
                    .findViewById<RadioButton>(binding.tbGroup.getCheckedRadioButtonId()))
                    .text
                    .toString()*/

            val cardView =
                (requireActivity().findViewById<MaterialCardView>(
                    binding.tbGroup.getCheckedRadioButtonId()
                ))

            val textView = cardView.getChildAt(0) as MaterialTextView

            selectedAnswer = textView.text.toString()

            Timber.e("selected answer : $selectedAnswer")

            mViewModel.isAnswerSelected(true)
        })
    }

    fun checkAnswers() {
        Timber.e("checkAnswers()")

        if (item.correctAnswer != selectedAnswer) {
            // Not correct
            Timber.e("wrong answer")
            return
        } else {
            listener?.correctAnswer()
        }
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            /*R.id.btn_start_quizz -> {
                startActivity(Intent(requireActivity(), QuizzActivity::class.java))
            }*/
        }
    }
}