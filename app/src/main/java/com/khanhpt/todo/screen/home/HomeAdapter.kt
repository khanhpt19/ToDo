package com.khanhpt.todo.screen.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import com.khanhpt.todo.R
import com.khanhpt.todo.data.model.Task
import com.khanhpt.todo.databinding.ItemTaskBinding
import com.khanhpt.todo.utils.BaseRecyclerAdapter

class HomeAdapter(
    private val callBack: (Task) -> Unit
) : BaseRecyclerAdapter<Task>(callBack = object : DiffUtil.ItemCallback<Task>() {
    override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean =
        oldItem.title == newItem.title
                && oldItem.description == newItem.description
}) {
    override fun createBinding(parent: ViewGroup, viewType: Int?): ViewDataBinding =
        DataBindingUtil.inflate<ItemTaskBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_task,
            parent,
            false
        )

    override fun bind(binding: ViewDataBinding, item: Task) {
        if (binding is ItemTaskBinding) {
            binding.apply {
                task = item
                buttonRemoveTask.setOnClickListener {
                    callBack.invoke(item)
                }
            }
        }
    }
}
