package com.example

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.advent_of_fap.R
import com.example.advent_of_fap.databinding.TutorialBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class Tutorial : Fragment() {

    private var _binding: TutorialBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = TutorialBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_Tutorial_to_Database)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}