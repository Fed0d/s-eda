package com.example.s_eda_app.activities

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.s_eda_app.db.DBHelper

class UpdateMenuDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setMessage("Вы точно хотите выбрать меню на завтра? (это удалит сегодняшнее меню)")
                .setPositiveButton("Да") { dialog, id ->
                    val db= DBHelper(requireContext(),null)
                    db.deleteDishes()
                    startActivity(Intent(context, DishChoiceActivity::class.java))
                }
                .setNegativeButton("Отмена") { dialog, id ->

                }

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}