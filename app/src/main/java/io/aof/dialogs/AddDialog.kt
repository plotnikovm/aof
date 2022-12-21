package io.aof.dialogs

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.ContentValues
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.google.android.material.slider.Slider
import io.aof.advent_of_fap.R
import io.aof.db.Db.Fap.FapEntry
import io.aof.db.Db.Fap.FapReaderDbHelper
import kotlin.math.roundToInt

class AddDialog : DialogFragment() {
    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)

            // Get the layout inflater
            val inflater = requireActivity().layoutInflater

            // Inflate and set the layout for the dialog
            // Pass null as the parent view because its going in the dialog layout
            builder.setView(inflater.inflate(R.layout.dialog_add, null))
                .setPositiveButton(R.string.add) { _, _ ->
                    val dbHelper = FapReaderDbHelper(requireContext())

                    val db = dbHelper.writableDatabase

                    // https://developer.android.com/develop/ui/views/components/dialogs
                    // https://developer.android.com/training/data-storage/sqlite#kotlin
                    val timestamp = System.currentTimeMillis()

                    val rating = view?.findViewById<Slider>(R.id.rating)
                    val time = view?.findViewById<Slider>(R.id.time)

                    val ratingValue = rating?.value?.roundToInt()
                    val timeValue = time?.value?.roundToInt()

                    val values = ContentValues().apply {
                        put(FapEntry.COLUMN_NAME_TIMESTAMP, timestamp)
                        put(FapEntry.COLUMN_NAME_RATING, ratingValue)
                        put(FapEntry.COLUMN_NAME_TIME, timeValue)
                    }


                    db?.insert(FapEntry.TABLE_NAME, null, values)
                    db.close()
                }
                .setNegativeButton(R.string.cancel) { dialog, _ ->
                    dialog.cancel()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}