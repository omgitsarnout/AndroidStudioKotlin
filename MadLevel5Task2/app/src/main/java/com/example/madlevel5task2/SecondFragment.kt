package com.example.madlevel5task2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.fabSave
import kotlinx.android.synthetic.main.fragment_second.*
import kotlinx.android.synthetic.main.item_game.*
import kotlinx.android.synthetic.main.item_game.txtPlatform
import kotlinx.android.synthetic.main.item_game.txtTitle
import java.text.SimpleDateFormat

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private val viewModel: GameViewModel by viewModels()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fabSave.setOnClickListener {
            onAddGame()
        }
    }

    private fun onAddGame() {
        val gameTitle = txtTitle.text.toString();
        val gamePlatform = txtPlatform.text.toString();
        val gameDate = SimpleDateFormat("dd-MM-yyyy").parse(txtDay.text.toString() + "-" + txtMonth.text.toString() + "-" + txtYear.text.toString())

        if (gameTitle.isNotBlank()) {
            viewModel.insertGame(Game(gameTitle, gamePlatform, gameDate))
            findNavController().popBackStack()
        } else {
            Toast.makeText(
                activity,
                "invalid game", Toast.LENGTH_SHORT
            ).show()
        }
    }
}