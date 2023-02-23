package com.example.portfolio.component_catalog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.portfolio.about.AboutFragment
import com.example.portfolio.component_catalog.ComponentCatalogFragment.Companion.components
import com.example.portfolio.component_catalog.components.EmailFragment
import com.example.portfolio.component_catalog.components.NumberPickerFragment
import com.example.portfolio.component_catalog.components.TextFragment
import com.example.portfolio.databinding.ComponentCatalogBinding
import com.google.android.material.snackbar.Snackbar


class ComponentCatalogFragment : Fragment() {
    private lateinit var binding: ComponentCatalogBinding

    private lateinit var ccAdapter : ComponentCatalogAdapter
    private lateinit var viewPager: ViewPager2

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ComponentCatalogBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        ccAdapter = ComponentCatalogAdapter(this)
        viewPager = binding.pager
        viewPager.adapter = ccAdapter

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "${components[viewPager.currentItem].description}\nSkills Used: ${components[viewPager.currentItem].skillsUsed}", Snackbar.LENGTH_LONG).show()
        }
    }

    fun goHome(){
        viewPager.currentItem
        viewPager.setCurrentItem(0, true)
    }

    companion object {
        val components = listOf(
            Component(
                "Text",
                TextFragment.newInstance(),
                "A simple text fragment.",
                "TextView"
            ),
            Component(
                "Number Picker",
                NumberPickerFragment.newInstance(1, 6),
                "A number picker.",
                "NumberPicker"
            ),
            Component(
                "About",
                AboutFragment.newInstance(AboutFragment.johnDoeProfile),
                "'About Me' page of someone",
                "Compose, Material"
            ),
            Component(
                "Email",
                EmailFragment.newInstance(),
                "Button that emails dummyemail@gmail",
                "Intent"
            )
        )
    }
}

class ComponentCatalogAdapter(fragment : Fragment): FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = components.size

    override fun createFragment(position: Int): Fragment {
        return components[position].fragment
    }
}