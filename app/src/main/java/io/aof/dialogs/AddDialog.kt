package io.aof.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.ContentValues
import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import com.google.android.material.slider.Slider
import io.aof.advent_of_fap.R
import io.aof.db.Db.Fap.FapEntry
import io.aof.db.Db.Fap.FapReaderDbHelper
import kotlin.math.roundToInt

class AddDialog : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)

            val inflater = requireActivity().layoutInflater
            val view: View = inflater.inflate(R.layout.dialog_add, null)

            builder.setView(view)
                .setPositiveButton(R.string.add) { _, _ ->
                    val dbHelper = FapReaderDbHelper(requireContext())

                    val db = dbHelper.writableDatabase

                    val timestamp = System.currentTimeMillis()

                    val rating: Slider = view.findViewById(R.id.rating_slider)
                    val time: Slider = view.findViewById(R.id.time_slider)

                    val ratingValue = rating.value.roundToInt()
                    val timeValue = time.value.roundToInt()

                    val values = ContentValues().apply {
                        put(FapEntry.COLUMN_NAME_TIMESTAMP, timestamp)
                        put(FapEntry.COLUMN_NAME_RATING, ratingValue)
                        put(FapEntry.COLUMN_NAME_TIME, timeValue)
                    }

                    db?.insert(FapEntry.TABLE_NAME, null, values)
                    db.close()
                    dbHelper.close()
                }
                .setNegativeButton(R.string.cancel) { dialog, _ ->
                    dialog.cancel()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}