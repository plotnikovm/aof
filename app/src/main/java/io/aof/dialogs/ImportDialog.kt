package io.aof.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.DialogFragment
import io.aof.advent_of_fap.R
import io.aof.db.Db
import io.aof.db.Import.Companion.importQrJson
import io.github.g00fy2.quickie.QRResult
import io.github.g00fy2.quickie.ScanQRCode

class ImportDialog : DialogFragment() {
    // todo: fix
    private val scanQrCodeLauncher = registerForActivityResult(ScanQRCode()) { result ->

        when (result) {
            is QRResult.QRSuccess -> {
                val value = result.content.rawValue
                val items = importQrJson(value)

                requireContext().deleteDatabase(Db.Fap.DATABASE_NAME)

                val dbHelper = Db.Fap.FapReaderDbHelper(requireContext())
                val db = dbHelper.writableDatabase

                if (items != null) {
                    for (item in items.iterator()) {
                        val values = ContentValues().apply {
                            put(Db.Fap.FapEntry.COLUMN_NAME_TIMESTAMP, item.timestamp)
                            put(Db.Fap.FapEntry.COLUMN_NAME_RATING, item.rating)
                            put(Db.Fap.FapEntry.COLUMN_NAME_TIME, item.time)
                        }

                        db?.insert(Db.Fap.FapEntry.TABLE_NAME, null, values)
                    }
                }

                db.close()
                dbHelper.close()
            }
            else -> {}
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)

            val inflater = requireActivity().layoutInflater
            val view: View = inflater.inflate(R.layout.dialog_import, null)
            builder.setView(view)
                .setPositiveButton(R.string.reset) { _, _ ->
                    Log.println(Log.INFO, "qr result", "wow")

                    scanQrCodeLauncher.launch(null)
                }
                .setNegativeButton(R.string.exit) { dialog, _ ->
                    dialog.cancel()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}