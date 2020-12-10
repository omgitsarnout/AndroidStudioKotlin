package com.example.madlevel6task2

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_movie.view.*
import java.lang.NullPointerException

class MovieAdapter (private val movies: List<Result>, private val onClick: (Result) -> Unit) :
    RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context

        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return movies.size;
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener { onClick(movies[adapterPosition]) }
        }

        var teller = 0;
        fun bind(movie: Result) {
            teller++;
            var imgUrl = "https://image.tmdb.org/t/p/w500" + movie.posterImageUrl
            Glide.with(context).load(imgUrl).into(itemView.ivMovie);
            itemView.tvNumber.text = movie.number.toString()
            Log.d("d", movie.title);
        }
    }
}