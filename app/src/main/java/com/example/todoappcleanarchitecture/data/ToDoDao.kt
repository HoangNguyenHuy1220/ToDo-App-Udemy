package com.example.todoappcleanarchitecture.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.todoappcleanarchitecture.data.model.ToDo
import kotlinx.coroutines.flow.Flow

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
    fun getAllData(): Flow<List<ToDo>>

    @Query("SELECT * FROM todo_table WHERE title LIKE :query")
    fun searchDatabase(query: String): LiveData<List<ToDo>>

    @Query("SELECT * FROM todo_table ORDER BY CASE " +
            "WHEN priority LIKE 'H%' THEN 1 " +
            "WHEN priority LIKE 'M%' THEN 2 " +
            "WHEN priority LIKE 'L%' THEN 3 END")
    fun sortHighToLow(): LiveData<List<ToDo>>

    @Query("SELECT * FROM todo_table ORDER BY CASE " +
            "WHEN priority LIKE 'L%' THEN 1 " +
            "WHEN priority LIKE 'M%' THEN 2 " +
            "WHEN priority LIKE 'H%' THEN 3 END")
    fun sortLowToHigh(): LiveData<List<ToDo>>
}