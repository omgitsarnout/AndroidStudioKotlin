package com.example.kotlinwhatsapprebuild2

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.parcel.Parcelize
import kotlinx.android.synthetic.main.fragment_newmessage.*
import kotlinx.android.synthetic.main.user_row_new_message.view.*


class fragment_newmessage : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        (activity as AppCompatActivity?)!!.supportActionBar!!.title = "Nieuw bericht"
        return inflater.inflate(R.layout.fragment_newmessage, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter = GroupAdapter<ViewHolder>()
        rvNewMessage.adapter = adapter

        getUsers()
    }

    private fun getUsers() {
        val ref = FirebaseDatabase.getInstance().getReference("/users")
        ref.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val adapter = GroupAdapter<ViewHolder>()

                snapshot.children.forEach {
                    Log.d("NewMessage", it.toString())
                    val user = it.getValue(User::class.java)
                    if (user != null) adapter.add(UserItem(user))
                }

                adapter.setOnItemClickListener { item, view ->

                    val userItem = item as UserItem
                    val userBundle = bundleOf(Pair("userItem", userItem.user))

                    findNavController().navigate(R.id.action_fragment_newmessage_to_chatActivity, userBundle)
                }

                rvNewMessage.adapter = adapter
            }
            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

}

class UserItem(val user: User): Item<ViewHolder>() {
    override fun getLayout(): Int {
        return R.layout.user_row_new_message
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.tv_username.text = user.username
        Picasso.get().load(user.profileImageUrl).into(viewHolder.itemView.ivProfileImage)
    }

}