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

        binding.root.findViewById<TextView>(R.id.strike).text = items.joinToString(",")
        return binding.root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}