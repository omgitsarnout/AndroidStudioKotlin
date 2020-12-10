package com.example.madlevel6task2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_first.*


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {
    private val movies = arrayListOf<Result>()
    private lateinit var moviesAdapter: MovieAdapter

    private val viewModel: MoviesViewModel by viewModels()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnSubmit.setOnClickListener {
            viewModel.getPopularMovies(etYear.text.toString())
        }
        moviesAdapter = MovieAdapter(movies, ::onMovieClick)
        rvMovies.adapter = moviesAdapter
        rvMovies.layoutManager = GridLayoutManager(this.context, 2)



        observeMovies()

    }

    private fun onMovieClick(movie: Result) {
        val bundle = bundleOf("movie" to movie)
        findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment, bundle)
    }

    private fun observeMovies() {
        viewModel.movies.observe(viewLifecycleOwner, Observer {
            movies.clear()
            movies.addAll(ArrayList(it.results))
            var teller = 1;
            for (movie in movies) {
                movie.number = teller;
                teller++;
            }
            moviesAdapter.notifyDataSetChanged()
        })

        // Observe the error message.
        viewModel.errorText.observe(viewLifecycleOwner, Observer {
            Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
        })
    }
}