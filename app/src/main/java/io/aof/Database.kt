package io.aof

import android.os.Bundle
import android.provider.BaseColumns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import io.aof.advent_of_fap.R
import io.aof.advent_of_fap.databinding.DatabaseBinding
import io.aof.db.Db

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
        val dbHelper = Db.FapReaderContract.FapReaderDbHelper(requireContext())
        val db = dbHelper.readableDatabase
        val cursor = db.query(
            Db.FapReaderContract.FapEntry.TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            null
        )
        val itemIds = mutableListOf<Long>()
        with(cursor) {
            while (moveToNext()) {
                val itemId = getLong(getColumnIndexOrThrow(BaseColumns._ID))
                itemIds.add(itemId)
            }
        }
        cursor.close()
        db.close()

        binding.root.findViewById<TextView>(R.id.strike).text = itemIds.joinToString(",")
        return binding.root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}