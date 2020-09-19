package com.example.madlevel1task1
import com.example.madlevel1task1.databinding.ActivityHigherLowerBinding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_higher_lower.*

private lateinit var binding: ActivityHigherLowerBinding
private var currentThrow: Int = 2
private var lastThrow: Int = 2

class HigherLowerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHigherLowerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() {
        btnLower.setOnClickListener {
            rollDice()
            onLowerClick()
        }
        btnEquals.setOnClickListener {
            rollDice()
            onEqualClick()
        }
        btnHigher.setOnClickListener {
            rollDice()
            onHigherClick()
        }
        updateUI()
    }

    /**
     * Replaces the previous dice value with the current one and replaces the current dice with a new dice
     * with a random number between 1 and 6 (inclusive).
     */
    private fun rollDice() {
        lastThrow = currentThrow
        currentThrow = (1..6).random()
        updateUI()
    }

    private fun updateUI() {
        if (currentThrow == 1) {
            binding.imageView.setImageResource(R.drawable.dice1)
        }
        else if (currentThrow == 2) {
            binding.imageView.setImageResource(R.drawable.dice2)
        }
        else if (currentThrow == 3) {
            binding.imageView.setImageResource(R.drawable.dice3)
        }
        else if (currentThrow == 4) {
            binding.imageView.setImageResource(R.drawable.dice4)
        }
        else if (currentThrow == 5) {
            binding.imageView.setImageResource(R.drawable.dice5)
        }
        else if (currentThrow == 6) {
            binding.imageView.setImageResource(R.drawable.dice6)
        }

        binding.textView2.text = getString(R.string.last_throw, lastThrow)

    }

    /**
     * Calls [rollDice] and checks if the answer is correct.
     */
    private fun onHigherClick() {
        rollDice()
        if (currentThrow > lastThrow) {
            onAnswerCorrect()
        } else {
            onAnswerIncorrect()
        }
    }

    /**
     * Calls [rollDice] and checks if the answer is correct.
     */
    private fun onLowerClick() {
        if (currentThrow < lastThrow) {
            onAnswerCorrect()
        } else {
            onAnswerIncorrect()
        }
    }

    /**
     * Calls [rollDice] and checks if the answer is correct.
     */
    private fun onEqualClick() {
        if (currentThrow == lastThrow) {
            onAnswerCorrect()
        } else {
            onAnswerIncorrect()
        }
    }

    /**
     * Displays a successful Toast message.
     */
    private fun onAnswerCorrect() {
        Toast.makeText(this, getString(R.string.correct), Toast.LENGTH_LONG).show()
    }

    /**
     * Displays an incorrect Toast message.
     */
    private fun onAnswerIncorrect() {
        Toast.makeText(this, getString(R.string.incorrect), Toast.LENGTH_LONG).show()
    }
}