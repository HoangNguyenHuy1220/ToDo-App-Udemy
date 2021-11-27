package com.example.todoappcleanarchitecture.data.viewmodel

import android.view.View
import android.widget.AdapterView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.*
import com.example.todoappcleanarchitecture.R
import com.example.todoappcleanarchitecture.data.model.Priority
import com.example.todoappcleanarchitecture.data.model.ToDo
import com.example.todoappcleanarchitecture.data.repository.ToDoRepository
import kotlinx.coroutines.launch

class ToDoViewModel(private val repository: ToDoRepository): ViewModel() {

    private var _allData = repository.getAllData.asLiveData()
    val allData: LiveData<List<ToDo>>
        get() = _allData


    private val _listener: AdapterView.OnItemSelectedListener = object :
        AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View, position: Int, id: Long) {
            when (position) {
                0 -> {
                    (parent?.getChildAt(0) as TextView).setTextColor(
                        ContextCompat.getColor(view.context, R.color.red)
                    )
                }
                1 -> {
                    (parent?.getChildAt(0) as TextView).setTextColor(
                        ContextCompat.getColor(view.context, R.color.yellow)
                    )
                }
                else -> {
                    (parent?.getChildAt(0) as TextView).setTextColor(
                        ContextCompat.getColor(view.context, R.color.green)
                    )
                }
            }
        }

        override fun onNothingSelected(p0: AdapterView<*>?) {}

    }
    val listener: AdapterView.OnItemSelectedListener
        get() = _listener

    fun insert(note: ToDo) {
        viewModelScope.launch {
            repository.insertData(note)
        }
    }

    private fun update(note: ToDo) {
        viewModelScope.launch {
            repository.updateData(note)
        }
    }

    fun deleteData(note: ToDo) {
        viewModelScope.launch {
            repository.deleteData(note)
        }
    }

    fun verifyData(title: String, description: String): Boolean {
        return (title.isNotEmpty() && description.isNotEmpty())
    }

    private fun parsePriority(priority: String): Priority {
        return when (priority) {
            "High Priority" -> Priority.HIGH
            "Medium Priority" -> Priority.MEDIUM
            else -> Priority.LOW
        }
    }

    fun insertData(title: String, priority: String, description: String) {
        val note = ToDo(
            title = title,
            priority = parsePriority(priority),
            description = description
        )
        insert(note)
    }

    fun updateData(id: Int, title: String, priority: String, description: String) {
        val note = ToDo(id, title, parsePriority(priority), description)
        update(note)
    }

    fun insertMuchData(listData: List<ToDo>) {
        viewModelScope.launch {
            repository.insertMuchData(listData)
        }
    }

    fun deleteAllData(listData: List<ToDo>) {
        viewModelScope.launch {
            repository.deleteAll(listData)
        }
    }

    fun searchDatabase(query: String):LiveData<List<ToDo>> {
        return repository.searchDatabase(query)
    }

    fun sortHighToLow(): LiveData<List<ToDo>> {
        return repository.sortHighToLow()
    }

    fun sortLowToHigh(): LiveData<List<ToDo>> {
        return repository.sortLowToHigh()
    }
}