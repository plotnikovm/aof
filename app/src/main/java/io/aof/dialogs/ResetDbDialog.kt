package io.aof.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import io.aof.advent_of_fap.R
import io.aof.db.Db

class ResetDbDialog : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater

            val view: View = inflater.inflate(R.layout.dialog_db_reset, null)
            builder.setView(view)
                .setPositiveButton(R.string.reset) { _, _ ->
                    context?.deleteDatabase(Db.Fap.DATABASE_NAME)
                }
                .setNegativeButton(R.string.cancel) { dialog, _ ->
                    dialog.cancel()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}