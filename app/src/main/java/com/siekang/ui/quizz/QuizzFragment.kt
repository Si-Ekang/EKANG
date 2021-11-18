package com.siekang.ui.quizz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.siekang.core.views.ToggleButtonGroupTableLayout
import com.siekang.data.local.model.Quizz
import com.siekang.databinding.FragmentQuizzBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class QuizzFragment : Fragment(), View.OnClickListener {

    companion object {

        private const val EXTRA_QUIZZ = "EXTRA_QUIZZ"

        fun newInstance(quizz: Quizz): QuizzFragment {
            val args = Bundle()
            args.putParcelable(EXTRA_QUIZZ, quizz)
            val fragment = QuizzFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private var _viewBinding: FragmentQuizzBinding? = null
    private val binding get() = _viewBinding!!
    private lateinit var tbGroup: ToggleButtonGroupTableLayout

    private val mViewModel: QuizzViewModel by activityViewModels()
    private var selectedAnswer: String? = null

    private lateinit var item: Quizz

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

        binding.quizz = item
        binding.ivQuizzPicture.setImageResource(item.quizzImage)

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

            selectedAnswer =
                (requireActivity().findViewById<RadioButton>(binding.tbGroup.getCheckedRadioButtonId())).text.toString()


            Timber.e("selected answer : $selectedAnswer")

            mViewModel.isAnswerSelected(true)
        })
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            /*R.id.btn_start_quizz -> {
                startActivity(Intent(requireActivity(), QuizzActivity::class.java))
            }*/
        }
    }
}