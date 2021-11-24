package com.siekang.ui.quiz

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textview.MaterialTextView
import com.siekang.core.views.ToggleButtonGroupTableLayout
import com.siekang.data.local.model.Quiz
import com.siekang.databinding.FragmentQuizBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber


@AndroidEntryPoint
class QuizFragment : Fragment(), View.OnClickListener {

    companion object {

        private const val EXTRA_QUIZ = "EXTRA_QUIZ"

        fun newInstance(quiz: Quiz): QuizFragment {
            val args = Bundle()
            args.putParcelable(EXTRA_QUIZ, quiz)
            val fragment = QuizFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private var _viewBinding: FragmentQuizBinding? = null
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
        // fun onWrongAnswer() // for stats purpose (feature to develop later)
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
            item = extras.getParcelable(EXTRA_QUIZ)!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _viewBinding = FragmentQuizBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initVariables()

        tbGroup = binding.tbGroup

        binding.quiz = item
        binding.ivQuizPicture.setImageResource(item.quizImage)

        initViewModelObservers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _viewBinding = null
    }

    private fun initVariables() {
        mViewModel.isAnswerSelected(false)
        if (null != selectedAnswer) selectedAnswer = null
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

        if (null == selectedAnswer) {
            Timber.e("No answer has been selected")
            Toast.makeText(requireActivity(), "Please select an answer", Toast.LENGTH_LONG).show()
            return
        }

        if (item.correctAnswer != selectedAnswer) {
            // Not correct
            Timber.e("wrong answer")
            Toast.makeText(requireActivity(), "Wrong answer", Toast.LENGTH_LONG).show()
            return
        } else {
            listener?.correctAnswer()
            // reset variables
            initVariables()
        }
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            /*R.id.btn_start_quiz -> {
                startActivity(Intent(requireActivity(), QuizActivity::class.java))
            }*/
        }
    }
}