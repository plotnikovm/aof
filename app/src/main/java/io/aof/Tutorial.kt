package io.aof

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import io.aof.advent_of_fap.R
import io.aof.advent_of_fap.databinding.TutorialBinding
import io.aof.db.Export.Companion.getItems
import java.util.*

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
        val congratulations = view.findViewById<TextView>(R.id.congratulations)
        val date = Date(System.currentTimeMillis())

        @Suppress("DEPRECATION")
        when (date.month) {
            11 -> congratulations.text = getString(R.string.congratulations_december)
            10 -> congratulations.text = getString(R.string.congratulations_november)
            else -> congratulations.text = getString(R.string.congratulations)
        }

        binding.buttonFirst.setOnClickListener {
            val items = getItems(requireContext())

            if (items.isNotEmpty()) {
                findNavController().navigate(R.id.action_Tutorial_to_Database)
            } else {
                findNavController().navigate(R.id.action_Tutorial_to_No_Records)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
