package com.example.madlevel4task2

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "game_table")
data class Game (
    @ColumnInfo(name = "computerHand")
    val computerHand: Int,
    @ColumnInfo(name = "playerHand")
    val playerHand: Int,
    @ColumnInfo(name = "time")
    val time: String,
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long? = null
)