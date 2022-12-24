package io.aof

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import io.aof.advent_of_fap.R
import io.aof.advent_of_fap.databinding.DatabaseBinding
import io.aof.db.Db
import io.aof.db.Db.Fap.FapEntry.COLUMN_NAME_RATING
import io.aof.db.Db.Fap.FapEntry.COLUMN_NAME_TIME
import io.aof.db.Db.Fap.FapEntry.COLUMN_NAME_TIMESTAMP
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

    private fun update(view: View) {
        val dbHelper = Db.Fap.FapReaderDbHelper(requireContext())
        val db = dbHelper.readableDatabase
        val cursor = db.query(
            Db.Fap.FapEntry.TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            null
        )
        val items = mutableListOf<Triple<Long, Long, Long>>()
        with(cursor) {
            while (moveToNext()) {
                val timestamp = getLong(getColumnIndexOrThrow(COLUMN_NAME_TIMESTAMP))
                val rating = getLong(getColumnIndexOrThrow(COLUMN_NAME_RATING))
                val time = getLong(getColumnIndexOrThrow(COLUMN_NAME_TIME))
                items.add(Triple(timestamp, rating, time))
            }
        }
        cursor.close()
        db.close()

        val entries = items.map {
            val (timestamp, rating, time) = it
            val sdf = SimpleDateFormat("dd.MM.yy", Locale.getDefault())
            val netDate = Date(timestamp)
            val date = sdf.format(netDate)
            "$date: $rating/5, $time минут"
        }

        val ratings = items.map {
            val (_, rating, _) = it
            rating
        }

        val time = items.map {
            val (_, _, time) = it
            time
        }

        val meanRating = ratings.sum().toFloat() / ratings.count()
        val meanTime = time.sum().toFloat() / time.count()

        view.findViewById<TextView>(R.id.mean_rating).text = getString(R.string.mean_rating, meanRating)
        view.findViewById<TextView>(R.id.mean_time).text = getString(R.string.mean_time, meanTime)
        view.findViewById<TextView>(R.id.all_records_count).text = getString(R.string.all_records, items.count())
        view.findViewById<TextView>(R.id.all_records).text = entries.joinToString("\n")
    }
}