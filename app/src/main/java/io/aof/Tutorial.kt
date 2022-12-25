package io.aof

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import io.aof.advent_of_fap.databinding.TutorialBinding
import io.aof.db.Export.Companion.getItems

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
            val items = getItems(requireContext())

            if (items.isNotEmpty()) {
                findNavController().navigate(io.aof.advent_of_fap.R.id.action_Tutorial_to_Database)
            } else {
                findNavController().navigate(io.aof.advent_of_fap.R.id.action_Tutorial_to_No_Records)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
