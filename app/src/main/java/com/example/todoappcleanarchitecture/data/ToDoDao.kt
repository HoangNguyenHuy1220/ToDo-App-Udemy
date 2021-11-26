package com.example.todoappcleanarchitecture.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.todoappcleanarchitecture.data.model.ToDo

@Dao
interface ToDoDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertData(data: ToDo)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMuchData(listData: List<ToDo>)

    @Update
    suspend fun updateData(data: ToDo)

    @Delete
    suspend fun deleteData(data: ToDo)

    @Delete
    suspend fun deleteAll(listData: List<ToDo>)

    @Query("SELECT * FROM todo_table ORDER BY id ASC")
    fun getAllData(): LiveData<List<ToDo>>

    /*@Query("DELETE FROM todo_table")
    suspend fun deleteAll()*/
}