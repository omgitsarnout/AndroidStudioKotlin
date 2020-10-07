package com.example.madlevel4task2

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.findNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    private lateinit var gameRepository: GameRepository
    private val mainScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        navController = findNavController(R.id.nav_host_fragment)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)

        gameRepository = GameRepository(this)

        var menuItem: MenuItem = menu.findItem(R.id.btnDeleteAllGames);
        menuItem.setVisible(false)

        navController.addOnDestinationChangedListener { _,       destination, _ ->
            var menuItem: MenuItem = menu.findItem(R.id.btnHistory);
            if (destination.id in arrayOf(R.id.SecondFragment)) {
                menuItem.setVisible(false)
            } else {
                menuItem.setVisible(true)
            }
        }

        navController.addOnDestinationChangedListener { _,       destination, _ ->
            var menuItem: MenuItem = menu.findItem(R.id.btnDeleteAllGames);
            if (destination.id in arrayOf(R.id.SecondFragment)) {
                menuItem.setVisible(true)
            } else {
                menuItem.setVisible(false)
            }
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.btnHistory) {
            navController.navigate(
                R.id.action_FirstFragment_to_SecondFragment
            )
        }
        if (id == R.id.btnDeleteAllGames) {
            removeAllProducts()
        }

        var menuItem: MenuItem = item;
        menuItem.setVisible(false)
        return super.onOptionsItemSelected(item);
    }

    var fm: FragmentManager = supportFragmentManager

    private fun removeAllProducts() {
        mainScope.launch {
            withContext(Dispatchers.IO) {
                gameRepository.deleteAllGames()

                /*(fm.findFragmentById(R.id.SecondFragment) as SecondFragment?)?.let {
                    it.getGameListFromDatabase()
                }*/

                /*val fragment = fm.findFragmentById(R.id.SecondFragment) as SecondFragment
                fragment.getGameListFromDatabase()*/

            }
        }
    }
}