package com.example.todoappcleanarchitecture.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todoappcleanarchitecture.R
import com.example.todoappcleanarchitecture.data.model.ToDo
import com.example.todoappcleanarchitecture.databinding.RowLayoutBinding

class ToDoAdapter(private val onClicked: (ToDo) -> Unit)
    : ListAdapter<ToDo, ToDoAdapter.ViewHolder>(DiffCallback()){

    class ViewHolder(private val binding: RowLayoutBinding): RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: ToDo) {
            binding.apply {
                data = item
                executePendingBindings()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate<RowLayoutBinding>(
            LayoutInflater.from(parent.context), R.layout.row_layout, parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.onBind(item)
        holder.itemView.setOnClickListener { onClicked(item) }
    }
}

class DiffCallback: DiffUtil.ItemCallback<ToDo>() {
    override fun areItemsTheSame(oldItem: ToDo, newItem: ToDo): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ToDo, newItem: ToDo): Boolean {
        return oldItem == newItem
    }

}
