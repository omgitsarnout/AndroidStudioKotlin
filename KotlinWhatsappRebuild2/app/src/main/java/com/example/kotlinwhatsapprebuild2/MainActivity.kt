package com.example.kotlinwhatsapprebuild2

import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.navigation.findNavController
import com.google.firebase.auth.FirebaseAuth
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        FirebaseAuth.getInstance().signOut()
        try {
            try {
                findNavController(R.id.nav_host_fragment).navigate(R.id.action_messagesFragment_to_SecondFragment)
            } catch (e: Exception) {
                try {
                    findNavController(R.id.nav_host_fragment).navigate(R.id.action_fragment_newmessage_to_SecondFragment)
                } catch (e: Exception) {
                    findNavController(R.id.nav_host_fragment).navigate(R.id.action_chatActivity_to_SecondFragment)
                }
            }
        } catch (e: Exception) {
            Toast.makeText(this, "Je bent al uitgelogd", Toast.LENGTH_SHORT).show()
        }

        return true
    }
}