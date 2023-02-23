package com.example.portfolio.component_catalog.components

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.portfolio.databinding.FragmentTextBinding

class TextFragment : Fragment() {

    private lateinit var binding: FragmentTextBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTextBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.takeIf { it.containsKey(KEY_TEXT) }?.apply {
            val textView: TextView = binding.textFragmentTextView
            textView.text = getString(KEY_TEXT).toString()
        }
    }

    companion object {
        const val KEY_TEXT = "text"

        fun newInstance(title: String = "This is a Text Fragment"): TextFragment {
            val fragment = TextFragment()
            fragment.arguments = Bundle().apply {
                putString (KEY_TEXT, title)
            }
            return fragment
        }
    }
}