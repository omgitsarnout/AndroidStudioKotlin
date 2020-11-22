package com.example.kotlinwhatsapprebuild2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth


/**
 * A simple [Fragment] subclass.
 * Use the [MessagesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MessagesFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkIfUserLoggedIn()

        val act = activity as AppCompatActivity?
        if (act!!.supportActionBar != null) {
            val toolbar: Toolbar = act!!.supportActionBar!!.customView as Toolbar
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_messages, container, false)
    }

    fun checkIfUserLoggedIn(){
        if (FirebaseAuth.getInstance().uid == null) {
            findNavController().navigate(R.id.action_messagesFragment_to_SecondFragment)
        }
    }
}