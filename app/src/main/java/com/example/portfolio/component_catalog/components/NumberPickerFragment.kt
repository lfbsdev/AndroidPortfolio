package com.example.portfolio.component_catalog.components

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import com.example.portfolio.databinding.FragmentNumberPickerBinding

class NumberPickerFragment : Fragment() {

    private lateinit var binding: FragmentNumberPickerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNumberPickerBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.takeIf { it.containsKey(KEY_MIN) && it.containsKey(KEY_MAX) }?.apply {
            val numberPicker: NumberPicker = binding.numberPicker
            numberPicker.maxValue = getInt(KEY_MAX)
            numberPicker.minValue = getInt(KEY_MIN)
        }
    }

    companion object {
        const val KEY_MIN = "min"
        const val KEY_MAX = "max"

        fun newInstance(min: Int = 0, max: Int = 10): NumberPickerFragment {
            val fragment = NumberPickerFragment()
            fragment.arguments = Bundle().apply {
                putInt(KEY_MIN, min)
                putInt(KEY_MAX, max)
            }
            return fragment
        }
    }
}