package com.khanhpt.todo.utils

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import java.util.concurrent.Executors

abstract class BaseRecyclerAdapter<T>(
    callBack: DiffUtil.ItemCallback<T>
) : ListAdapter<T, BaseViewHolder<ViewDataBinding>>(
    AsyncDifferConfig.Builder<T>(callBack)
        .setBackgroundThreadExecutor(Executors.newSingleThreadExecutor())
        .build()
) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<ViewDataBinding> {
        return BaseViewHolder(
            createBinding(
                parent = parent,
                viewType = viewType
            )
        )
    }

    override fun onBindViewHolder(holder: BaseViewHolder<ViewDataBinding>, position: Int) {
        bind(holder.binding, getItem(position))
        bindPosition(holder.binding, getItem(position), position)
        holder.binding.executePendingBindings()
    }

    protected abstract fun createBinding(parent: ViewGroup, viewType: Int? = 0): ViewDataBinding

    protected abstract fun bind(binding: ViewDataBinding, item: T)
    open fun bindPosition(binding: ViewDataBinding, item: T, position: Int) {

    }

    override fun submitList(list: List<T>?) {
        super.submitList(if (list != null) ArrayList(list) else null)
    }

}

open class BaseViewHolder<out T : ViewDataBinding> constructor(val binding: T) :
    RecyclerView.ViewHolder(binding.root)
