package com.example.madlevel2task1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.madlevel2task1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val places = arrayListOf<Place>()
    private val placesAdapter = PlaceAdapter(places)
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
    }

    private fun initViews() {
        // Initialize the recycler view with a linear layout manager, adapter
        binding.rvPlaces.layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
        binding.rvPlaces.adapter = placesAdapter

        for (i in Place.PLACE_NAMES.indices) {
            places.add(Place(Place.PLACE_NAMES[i], Place.PLACE_RES_DRAWABLE_IDS[i]))
        }
        placesAdapter.notifyDataSetChanged()
    }
}