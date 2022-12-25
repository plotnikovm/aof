package io.aof

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import io.aof.advent_of_fap.R
import io.aof.advent_of_fap.databinding.DatabaseBinding
import io.aof.db.Export.Companion.getItems
import java.text.SimpleDateFormat
import java.util.*

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
        update(binding.root)
        return binding.root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @SuppressLint("StringFormatMatches")
    private fun update(view: View) {
        val items = getItems(requireContext())
        var rating: Long = 0
        var time: Long = 0
        val entries = items.map {
            val sdf = SimpleDateFormat("dd.MM.yy", Locale.getDefault())
            val netDate = Date(it.timestamp)
            val date = sdf.format(netDate)
            rating += it.rating
            time += it.time
            getString(R.string.entry_format, date, it.rating, it.time)
        }

        val count = items.count()
        val meanRating = rating.toFloat() / count
        val meanTime = time.toFloat() / count
        val result = getString(R.string.statistics, count, meanRating, meanTime)
        view.findViewById<TextView>(R.id.statistics).text = result
        view.findViewById<TextView>(R.id.all_records).text = entries.joinToString("\n")
    }
}