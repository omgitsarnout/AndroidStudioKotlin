package com.example.kotlinwhatsapprebuild2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_messages.*


/**
 * A simple [Fragment] subclass.
 * Use the [MessagesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MessagesFragment : Fragment() {

    companion object {
        var currentUser: User? = null;
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fetchCurrentUser()

        checkIfUserLoggedIn()

        /*val act = activity as AppCompatActivity?
        if (act!!.supportActionBar != null) {
            //val toolbar: Toolbar = act!!.supportActionBar!!.customView as Toolbar
        }*/
    }

    private fun fetchCurrentUser() {
        val uid = FirebaseAuth.getInstance().uid
        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")
        ref.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onCancelled(error: DatabaseError) {}
            override fun onDataChange(snapshot: DataSnapshot) {
                currentUser = snapshot.getValue(User::class.java);
            }
        } )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        fab_newmessage.setOnClickListener {
            findNavController().navigate(R.id.action_messagesFragment_to_fragment_newmessage)
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