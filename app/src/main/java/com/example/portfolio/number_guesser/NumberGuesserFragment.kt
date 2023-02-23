package com.example.portfolio.number_guesser

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.portfolio.R
import com.example.portfolio.databinding.FragmentNumberGuesserBinding
import kotlinx.coroutines.launch

class NumberGuesserFragment : Fragment() {
    private lateinit var binding: FragmentNumberGuesserBinding
    private val viewModel: NumberGuesserViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNumberGuesserBinding.inflate(layoutInflater)

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.ngState.collect {
                    updateUi(it)
                    makeCodeToast(it.code, it.attempts)
                }
            }
        }

        binding.sendNumberButton.setOnClickListener {
            viewModel.guessNumber(binding.numberPicker.value)
        }

        binding.tryAgainButton.setOnClickListener {
            viewModel.restart()
            binding.tryAgainButton.visibility = View.INVISIBLE
            binding.sendNumberButton.isEnabled = true
            binding.numberPicker.value = viewModel.ngState.value.minNumber
        }

        return binding.root
    }

    private fun updateUi(ngState: NumberGuesserState) {
            binding.numberPicker.minValue = ngState.minNumber
            binding.numberPicker.maxValue = ngState.maxNumber

            if (ngState.hit) {
                binding.numberGuesserInfo.text = getString(R.string.number_is) + " " + ngState.chosenNumber
                binding.sendNumberButton.isEnabled = false
                binding.tryAgainButton.visibility = View.VISIBLE
            }
            else {
                binding.numberGuesserInfo.text = getString(R.string.number_is_between) + " " + ngState.minNumber + " " + getString(
                    R.string.and
                ) + " " + ngState.maxNumber
            }
    }

    private fun makeCodeToast(code: Int, attempts: Int = -1) {
        when (code) {
            NumberGuesserViewModel.MISS_CODE -> missToast()
            NumberGuesserViewModel.ENDED_CODE -> hitToast(attempts)
            NumberGuesserViewModel.STARTED_CODE -> startToast()
        }
    }

    private fun startToast() {
        Toast.makeText(activity, getString(R.string.ng_welcome_toast), Toast.LENGTH_SHORT).show()
    }

    private fun missToast(){
        Toast.makeText(activity, getString(R.string.ng_miss_toast), Toast.LENGTH_SHORT / 2).show()
    }

    private fun hitToast(attempts: Int){
        Toast.makeText(activity, getString(R.string.ng_hit_toast) + " " + attempts + " " + getString(
            R.string.attempts
        ), Toast.LENGTH_SHORT).show()
    }

    companion object {
        fun newInstance() = NumberGuesserFragment()
    }
}