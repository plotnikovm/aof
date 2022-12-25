package io.aof.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import io.aof.advent_of_fap.R
import io.aof.db.Export.Companion.exportQr
import io.aof.db.Export.Companion.getItems

class ExportDialog : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)

            val inflater = requireActivity().layoutInflater
            val view: View = inflater.inflate(R.layout.dialog_export, null)
            val items = getItems(requireContext())
            val bmp = exportQr(items)
            view.findViewById<ImageView>(R.id.qr_code).setImageBitmap(bmp)
            view.findViewById<TextView>(R.id.export_state).text =
                getString(R.string.export_state_generated)
            builder.setView(view)
                .setNegativeButton(R.string.exit) { dialog, _ ->
                    dialog.cancel()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}