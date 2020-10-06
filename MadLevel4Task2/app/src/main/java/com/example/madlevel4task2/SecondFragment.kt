package com.example.madlevel4task2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_second.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private lateinit var gameRepository: GameRepository
    private val mainScope = CoroutineScope(Dispatchers.Main)

    private val games = arrayListOf<Game>()
    private val shoppingListAdapter = GameAdapter(games)

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        gameRepository = GameRepository(requireContext())
        getGameListFromDatabase()

        initRv()
    }

    private fun initRv() {
        rvGames.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        rvGames.adapter = shoppingListAdapter
        rvGames.setHasFixedSize(true)
        rvGames.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
    }

    private fun removeAllGames() {
        mainScope.launch {
            withContext(Dispatchers.IO) {
                gameRepository.deleteAllGames()
            }
            getGameListFromDatabase()
        }
    }

    public fun getGameListFromDatabase() {
        mainScope.launch {
            val shoppingList = withContext(Dispatchers.IO) {
                gameRepository.getAllGames()
            }
            games.clear()
            games.addAll(shoppingList)
            shoppingListAdapter.notifyDataSetChanged()
        }
    }
}