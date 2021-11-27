package com.example.todoappcleanarchitecture.data.repository

import androidx.lifecycle.LiveData
import com.example.todoappcleanarchitecture.data.ToDoDao
import com.example.todoappcleanarchitecture.data.model.ToDo
import kotlinx.coroutines.flow.Flow

class ToDoRepository(private val toDoDao: ToDoDao) {

    val getAllData: Flow<List<ToDo>> = toDoDao.getAllData()

    suspend fun insertData(data: ToDo) {
        toDoDao.insertData(data)
    }

    suspend fun insertMuchData(listData: List<ToDo>) {
        toDoDao.insertMuchData(listData)
    }

    suspend fun updateData(data: ToDo) {
        toDoDao.updateData(data)
    }

    suspend fun deleteData(data: ToDo) {
        toDoDao.deleteData(data)
    }

    suspend fun deleteAll(listData: List<ToDo>) {
        toDoDao.deleteAll(listData)
    }

    fun searchDatabase(query: String): LiveData<List<ToDo>> {
        return toDoDao.searchDatabase(query)
    }

    fun sortHighToLow(): LiveData<List<ToDo>> {
        return toDoDao.sortHighToLow()
    }

    fun sortLowToHigh(): LiveData<List<ToDo>> {
        return toDoDao.sortLowToHigh()
    }
}