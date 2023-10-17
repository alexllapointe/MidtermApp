package edu.iu.alex.midtermapp

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class DeleteDialogFragment(private val onConfirmDelete: () -> Unit) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(activity)
            .setTitle("Delete Score")
            .setMessage("Are you sure you want to delete this score?")
            .setPositiveButton("Yes") { _, _ ->
                onConfirmDelete()
                dismiss()
            }
            .setNegativeButton("No") { _, _ ->
                dismiss()
            }
            .create()
    }
}
