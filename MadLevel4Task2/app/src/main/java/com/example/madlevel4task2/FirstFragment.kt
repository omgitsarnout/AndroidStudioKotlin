package com.example.madlevel4task2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_first.*
import kotlinx.android.synthetic.main.game.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private lateinit var gameRepository: GameRepository
    private val mainScope = CoroutineScope(Dispatchers.Main)

    private val games = arrayListOf<Game>()
    private val shoppingListAdapter = GameAdapter(games)


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        gameRepository = GameRepository(requireContext())


        btnRock.setOnClickListener {
            playGame(1);
        }
        btnPaper.setOnClickListener {
            playGame(2);
        }
        btnScisor.setOnClickListener {
            playGame(3);
        }

    }

    fun playGame(playerHand: Int) {
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        val currentDate = sdf.format(Date())
        val computerHand: Int = Random.nextInt(1,3)

        addGame(computerHand, playerHand, currentDate.toString())

        if (computerHand == 3 && playerHand == 1) txtResult.text = "You win"
        else if (computerHand == 3 && playerHand == 2) txtResult.text = "Computer wins"
        else if (computerHand == 3 && playerHand == 3) txtResult.text = "Draw"
        else if (computerHand == 2 && playerHand == 1) txtResult.text = "Computer wins"
        else if (computerHand == 2 && playerHand == 2) txtResult.text = "Draw"
        else if (computerHand == 2 && playerHand == 3) txtResult.text = "You win"
        else if (computerHand == 1 && playerHand == 1) txtResult.text = "Draw"
        else if (computerHand == 1 && playerHand == 2) txtResult.text = "You win"
        else if (computerHand == 1 && playerHand == 3) txtResult.text = "Computer wins"

        if (computerHand == 1) imgComputer.setImageResource(R.drawable.rock)
        else if (computerHand == 2) imgComputer.setImageResource(R.drawable.paper)
        else if (computerHand == 3) imgComputer.setImageResource(R.drawable.scissors)

        if (playerHand == 1) imgYou.setImageResource(R.drawable.rock)
        else if (playerHand == 2) imgYou.setImageResource(R.drawable.paper)
        else if (playerHand == 3) imgYou.setImageResource(R.drawable.scissors)
    }

    fun addGame(computerHand: Int, playerHand: Int, date: String) {
        mainScope.launch {
            val game = Game(
                computerHand = computerHand,
                playerHand = playerHand,
                time = date
            )

            withContext(Dispatchers.IO) {
                gameRepository.insertGame(game)
            }

            /*getGameListFromDatabase()*/
        }
    }

    /*private fun getGameListFromDatabase() {
        mainScope.launch {
            val shoppingList = withContext(Dispatchers.IO) {
                gameRepository.getAllProducts()
            }
            this@SecondFragment.products.clear()
            this@ShoppingListFragment.products.addAll(shoppingList)
            this@ShoppingListFragment.shoppingListAdapter.notifyDataSetChanged()
        }
    }*/
}