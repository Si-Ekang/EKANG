package com.siekang.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.siekang.R
import com.siekang.databinding.FragmentHomeBinding
import com.siekang.ui.quiz.QuizActivity

class HomeFragment : Fragment(), View.OnClickListener {

    private var _viewBinding: FragmentHomeBinding? = null
    private val binding get() = _viewBinding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _viewBinding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnStartQuizz.setOnClickListener(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _viewBinding = null
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_start_quizz -> {
                startActivity(Intent(requireActivity(), QuizActivity::class.java))
            }
        }
    }
}