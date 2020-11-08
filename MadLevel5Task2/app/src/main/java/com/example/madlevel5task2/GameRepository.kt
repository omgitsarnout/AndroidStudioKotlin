package com.example.madlevel5task2

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class GameRepository(context: Context) {

    private var gameDao: GameDao

    init {
        val reminderRoomDatabase = GameRoomDatabase.getDatabase(context)
        gameDao = reminderRoomDatabase!!.reminderDao()
    }

    fun getAllGames(): LiveData<List<Game>> {
        return gameDao.getAllGames() ?: MutableLiveData(emptyList())
    }

    suspend fun insertGame(game: Game) {
        gameDao.insertGame(game)
    }

    suspend fun deleteGame(game: Game) {
        gameDao.deleteGame(game)
    }

    suspend fun updateGame(game: Game) {
        gameDao.updateGame(game)
    }

    suspend fun deleteAllGames() {
        gameDao.deleteAllGames()
    }
}