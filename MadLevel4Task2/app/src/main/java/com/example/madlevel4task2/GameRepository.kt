package com.example.madlevel4task2

import android.content.Context

class GameRepository (context: Context) {

    private val gameDao: GameDao

    init {
        val database = GameListRoomDatabase.getDatabase(context)
        gameDao = database!!.gameDao()
    }

    suspend fun getAllGames(): List<Game> {
        return gameDao.getAllProducts()
    }

    suspend fun insertGame(product: Game) {
        gameDao.insertProduct(product)
    }

    suspend fun deleteGame(product: Game) {
        gameDao.deleteProduct(product)
    }

    suspend fun deleteAllGames() {
        gameDao.deleteAllProducts()
    }

}