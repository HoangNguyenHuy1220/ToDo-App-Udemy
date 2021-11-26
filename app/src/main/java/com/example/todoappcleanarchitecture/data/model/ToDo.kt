package com.example.todoappcleanarchitecture.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.versionedparcelable.ParcelField
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "todo_table")
@Parcelize
data class ToDo (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val priority: Priority,
    val description: String
): Parcelable