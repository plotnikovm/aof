package io.aof.dialogs

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import io.aof.advent_of_fap.R

class AboutDialog : DialogFragment() {
    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)

            val inflater = requireActivity().layoutInflater
            builder.setView(inflater.inflate(R.layout.dialog_about, null))
                .setTitle(R.string.action_about)
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}