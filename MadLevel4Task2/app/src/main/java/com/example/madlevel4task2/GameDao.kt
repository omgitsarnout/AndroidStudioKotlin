package com.example.madlevel4task2

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface GameDao {

    @Query("SELECT * FROM game_table")
    suspend fun getAllProducts(): List<Game>

    @Insert
    suspend fun insertProduct(product: Game)

    @Delete
    suspend fun deleteProduct(product: Game)

    @Query("DELETE FROM game_table")
    suspend fun deleteAllProducts()

}