package com.example.madlevel4task1

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product_table")
data class Product (
    @ColumnInfo(name = "quantity")
    val quantity: Long,
    @ColumnInfo(name = "name")
    val name: String,
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long? = null
)