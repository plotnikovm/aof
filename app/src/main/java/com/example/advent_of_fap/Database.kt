package com.example.advent_of_fap

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.advent_of_fap.databinding.DatabaseBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class Database : Fragment() {

    private var _binding: DatabaseBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {

        _binding = DatabaseBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_Database_to_Tutorial)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}