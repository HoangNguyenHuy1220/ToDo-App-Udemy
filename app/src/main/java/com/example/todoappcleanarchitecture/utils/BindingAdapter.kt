package com.example.todoappcleanarchitecture.utils

import android.view.View
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todoappcleanarchitecture.R
import com.example.todoappcleanarchitecture.adapter.ToDoAdapter
import com.example.todoappcleanarchitecture.data.model.Priority
import com.example.todoappcleanarchitecture.data.model.ToDo

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, listData: List<ToDo>?) {
    listData?.let {
        val adapter = recyclerView.adapter as ToDoAdapter
        adapter.submitList(it)
    }
}

@BindingAdapter("priority")
fun bindPriorityColor(cardView: CardView, priority: Priority) {

    cardView.setCardBackgroundColor(
        when (priority) {
            Priority.HIGH -> ContextCompat.getColor(
                    cardView.context,
                    R.color.red)
            Priority.MEDIUM -> ContextCompat.getColor(
                cardView.context,
                R.color.yellow
            )
            else -> ContextCompat.getColor(
                cardView.context,
                R.color.green)
        }
    )
}

@BindingAdapter("priority")
fun bindPriority(spinner: Spinner, priority: Priority) {
    spinner.setSelection(
        when (priority) {
            Priority.HIGH -> 0
            Priority.MEDIUM -> 1
            else -> 2
        }
    )
}

@BindingAdapter("imageStatus")
fun bindImageStatus(imageView: ImageView, listData: List<ToDo>?) {
    listData?.let {
        imageView.visibility =
            if (listData.isEmpty()) View.VISIBLE
            else View.GONE
    }
}

@BindingAdapter("textStatus")
fun bindTextStatus(textView: TextView, listData: List<ToDo>?) {
    listData?.let {
        textView.visibility =
            if (listData.isEmpty()) View.VISIBLE
            else View.GONE
    }
}

@BindingAdapter("selectedItemColor")
fun bindSelectedItemColor(spinner: Spinner, listener: AdapterView.OnItemSelectedListener) {
    spinner.onItemSelectedListener = listener
}
