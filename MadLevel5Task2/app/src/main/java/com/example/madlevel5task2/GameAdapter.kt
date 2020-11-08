package com.example.madlevel5task2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_game.view.*
import java.text.SimpleDateFormat

class GameAdapter(private val games: List<Game>) : RecyclerView.Adapter<GameAdapter.ViewHolder>(){

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun databind(game: Game) {
            /*itemView.txtTitle.text = game.name
            itemView.txtDate.text = game.releaseDate.toString()
            itemView.txtPlatform.text = game.platform*/

            itemView.txtTitle.text = game.name
            val pattern = "dd-MMMM-yyyy"
            val simpleDateFormat = SimpleDateFormat(pattern)
            itemView.txtDate.text = simpleDateFormat.format(game.releaseDate)
            itemView.txtPlatform.text = game.platform
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_game, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return games.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.databind(games[position])
    }
}