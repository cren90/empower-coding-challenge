package com.cren90.empower.empowerchallenge.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.cren90.empower.empowerchallenge.databinding.FragmentMainBinding

class StandardCardFragment : Fragment() {

    companion object {

        private const val TAB_INDEX = "tab_index"

        @JvmStatic
        fun newInstance(tabIndex: Int): StandardCardFragment {
            return StandardCardFragment().apply {
                arguments = Bundle().apply {
                    putInt(TAB_INDEX, tabIndex)
                }
            }
        }
    }

    private lateinit var pageViewModel: RateCardViewModel
    private var binding: FragmentMainBinding? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageViewModel = ViewModelProvider(this).get(RateCardViewModel::class.java).apply {
            setIndex(arguments?.getInt(TAB_INDEX) ?: 1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMainBinding.inflate(inflater, container, false)

        return binding?.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}