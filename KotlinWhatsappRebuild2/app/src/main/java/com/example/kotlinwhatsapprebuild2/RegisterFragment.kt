package com.example.kotlinwhatsapprebuild2

import android.app.Activity
import android.content.Intent
import android.content.Intent.ACTION_PICK
import android.graphics.ImageDecoder
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.fragment_register.*
import java.util.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class RegisterFragment : Fragment() {

    var ProfilePhotoUri: Uri? = null

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Registreer"
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvHebJeNogGeenAccount.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

        btnLogin.setOnClickListener {
            this.doRegister()
        }

        btnUploadFoto.setOnClickListener {
            val intent = Intent(ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 0)
        }
    }

    /*
    function ran after image was selected.
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && data != null &&requestCode == 0) {
            ProfilePhotoUri = data.data
            val bitmap = MediaStore.Images.Media.getBitmap(
                activity?.contentResolver,
                ProfilePhotoUri
            )
            val bitmapDrawable = BitmapDrawable(bitmap)
            btnUploadFoto.setBackgroundDrawable(bitmapDrawable);
        }
    }

    fun doRegister() {
        val email = etEmail.text.toString();
        val password = etWachtwoord.text.toString();

        if (email.isEmpty() || password.isEmpty()) {
            this.view?.let {
                Snackbar.make(it, "Vul alle velden in", Snackbar.LENGTH_SHORT)
                    .show()
            }
            return
        }

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Log.d("main", "account creation success")
                    uploadImage()
                    return@addOnCompleteListener
                }
                else {
                    Log.w("main", "createUserWithEmail:failure", it.exception)
                }
            }
    }

    fun uploadImage() {
        val filename = UUID.randomUUID().toString();
        val ref = FirebaseStorage.getInstance().getReference("/images/$filename")

        ProfilePhotoUri?.let { ref.putFile(it).addOnSuccessListener {
            ref.downloadUrl.addOnSuccessListener {

                saveUser(it.toString());
            }
        }
        }
    }

    fun saveUser(imgUrl: String) {
        val uid = FirebaseAuth.getInstance().uid
        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")

        val user = User(uid.toString(), etGebruikersnaam.text.toString(), imgUrl)

        ref.setValue(user).addOnSuccessListener {
            //success
            Log.d("main", "yess!!!!!!!")
            findNavController().navigate(R.id.action_FirstFragment_to_messagesFragment)
        }
    }


}