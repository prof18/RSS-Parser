package com.prof.rssparser.sampleandroid.util

import android.app.AlertDialog
import android.content.Context
import android.widget.EditText
import androidx.annotation.StringRes

class AlertDialogHelper(
    @StringRes private val title: Int,
    @StringRes private val positiveButton: Int,
    @StringRes private val negativeButton: Int
) {
    fun buildInputDialog(
        context: Context,
        onPositiveAction: (url: String) -> Unit
    ): AlertDialog {
        val editText = EditText(context)
        return AlertDialog.Builder(context)
            .setTitle(title)
            .setView(editText)
            .setNegativeButton(negativeButton) { dialog, _ ->
                dialog.dismiss()
            }.setPositiveButton(positiveButton) { dialog, _ ->
                onPositiveAction(editText.text?.toString().orEmpty())
                dialog.dismiss()
            }.create()
    }
}
