package com.example.madlevel6task2

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_second.*
import kotlinx.android.synthetic.main.fragment_second.view.*
import kotlinx.android.synthetic.main.item_movie.view.*

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var movie: Result? = arguments?.getParcelable<Result>("movie")
        super.onViewCreated(view, savedInstanceState)
        Glide.with(this).load("https://image.tmdb.org/t/p/w500" + movie?.backdropImageUrl).into(view.ivBackDrop);
        Glide.with(this).load("https://image.tmdb.org/t/p/w500" + movie?.posterImageUrl).into(view.ivPoster);
        tvTitle.text = movie?.title;
        tvDate.text = movie?.releaseDate;
        tvScore.text = movie?.rating;
        tvOverview.text = movie?.overview;
    }
}