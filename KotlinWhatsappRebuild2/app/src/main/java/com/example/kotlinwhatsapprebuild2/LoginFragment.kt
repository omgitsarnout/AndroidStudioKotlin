package com.example.kotlinwhatsapprebuild2

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_login.btnLogin
import kotlinx.android.synthetic.main.fragment_login.etEmail
import kotlinx.android.synthetic.main.fragment_login.etWachtwoord
import kotlinx.android.synthetic.main.fragment_login.tvHebJeNogGeenAccount
import kotlinx.android.synthetic.main.fragment_register.*

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class LoginFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Login"
        return inflater.inflate(R.layout.fragment_login, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        btnLogin.setOnClickListener {
            doLogin()
        }

        tvHebJeNogGeenAccount.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
    }

    private fun doLogin() {
        val email = etEmail.text.toString();
        val password = etWachtwoord.text.toString();

        if (email.isEmpty() || password.isEmpty()) {
            this.view?.let {
                Snackbar.make(it, "Vul alle velden in", Snackbar.LENGTH_SHORT)
                    .show()
            }
            return
        }

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener() {
            if (it.isSuccessful) {
                this.view?.let { it1 ->
                    Snackbar.make(it1, "Welcome", Snackbar.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_SecondFragment_to_messagesFragment)
                }
                return@addOnCompleteListener
            }
            else {
                this.view?.let { it1 ->
                    Snackbar.make(it1, "Wrong credentials", Snackbar.LENGTH_SHORT).show()
                }
            }
        }

    }
}