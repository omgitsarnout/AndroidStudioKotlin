package com.example.madlevel4task2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_first.view.*
import kotlinx.android.synthetic.main.game.view.*
import java.lang.Exception
import java.lang.NullPointerException

class GameAdapter (private val games: List<Game>) : RecyclerView.Adapter<GameAdapter.ViewHolder>(){


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun databind(game: Game) {
            itemView.tvDate.text = game.time
            itemView.tvWinner.text = whoWins(game.computerHand, game.playerHand)
            if (game.computerHand == 1) itemView.ivHandComputer.setImageResource(R.drawable.rock)
            else if (game.computerHand == 2) itemView.ivHandComputer.setImageResource(R.drawable.paper)
            else if (game.computerHand == 3) itemView.ivHandComputer.setImageResource(R.drawable.scissors)

            if (game.playerHand == 1) itemView.ivHandPlayer.setImageResource(R.drawable.rock)
            else if (game.playerHand == 2) itemView.ivHandPlayer.setImageResource(R.drawable.paper)
            else if (game.playerHand == 3) itemView.ivHandPlayer.setImageResource(R.drawable.scissors)
            //itemView.tvQuantity.text = product.quantity.toString()
        }
    }

    private fun whoWins(computerHand: Int, playerHand: Int): String{
        if (computerHand == 3 && playerHand == 1) return "You win"
        else if (computerHand == 3 && playerHand == 2) return "Computer wins"
        else if (computerHand == 3 && playerHand == 3) return "Draw"
        else if (computerHand == 2 && playerHand == 1) return "Computer wins"
        else if (computerHand == 2 && playerHand == 2) return "Draw"
        else if (computerHand == 2 && playerHand == 3) return "You win"
        else if (computerHand == 1 && playerHand == 1) return "Draw"
        else if (computerHand == 1 && playerHand == 2) return "You win"
        else if (computerHand == 1 && playerHand == 3) return "Computer wins"
        return "Error"
    }

    /**
     * Creates and returns a ViewHolder object, inflating a standard layout called simple_list_item_1.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.game, parent, false)
        )
    }

    /**
     * Returns the size of the list
     */
    override fun getItemCount(): Int {
        return games.size
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.databind(games[position])
    }
}