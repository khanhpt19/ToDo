package com.khanhpt.todo.screen.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import com.khanhpt.todo.R
import com.khanhpt.todo.data.model.Task
import kotlinx.android.synthetic.main.dialog_add_new_task.*

class AddNewTaskDialog(context: Context) : Dialog(context) {
    private var taskListener: ((Task) -> Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_add_new_task)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val title = textViewNewTitle.text
        val description = textViewNewDescription.text
        buttonOk.setOnClickListener {
            when {
                title.isNullOrEmpty() -> {
                    textViewNewTitle.error = context.getString(R.string.please_fill_this_text)
                }
                description.isNullOrEmpty() -> {
                    textViewNewDescription.error = context.getString(R.string.please_fill_this_text)
                }
                else -> {
                    taskListener?.invoke(
                        Task(
                            title = title.toString(),
                            description = description.toString()
                        )
                    )
                    dismiss()
                }

            }
        }
        buttonCancel.setOnClickListener {
            dismiss()
        }
    }

    companion object {
        fun newInstance(
            context: Context,
            taskListener: (Task) -> Unit
        ) = AddNewTaskDialog(context).apply {
            this.taskListener = taskListener
        }
    }
}
