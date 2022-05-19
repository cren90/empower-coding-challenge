package com.cren90.empower.empowerchallenge.ratecard.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.cren90.empower.empowerchallenge.databinding.FragmentMainBinding
import com.cren90.empower.empowerchallenge.ratecard.viewmodel.RateCardViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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

    private val viewModel: RateCardViewModel by viewModels()
    private var binding: FragmentMainBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMainBinding.inflate(inflater, container, false)

        viewModel.setIndex(arguments?.getInt(TAB_INDEX) ?: 1)

        binding?.lifecycleOwner = viewLifecycleOwner
        binding?.vm = viewModel

        return binding?.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}