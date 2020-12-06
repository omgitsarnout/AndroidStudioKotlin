package com.example.kotlinwhatsapprebuild2

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.chat_from_row.view.*
import kotlinx.android.synthetic.main.chat_to_row.view.*
import kotlinx.android.synthetic.main.chat_to_row.view.tv_from_row
import kotlinx.android.synthetic.main.fragment_chat.*

class ChatActivity : Fragment() {

    val adapter = GroupAdapter<ViewHolder>();
    var toUser: User? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = GroupAdapter<ViewHolder>()

        toUser = arguments?.getParcelable<User>("userItem")
        et_message.hint = "bericht voor " + toUser!!.username

        listenForMessages()

        btnSend.setOnClickListener {
            sendMessage()
        }
    }

    private fun listenForMessages() {

        rv_chat.adapter = adapter

        val fromId = FirebaseAuth.getInstance().uid
        val toId = toUser?.uid

        val ref = FirebaseDatabase.getInstance().getReference("/user-messages/$fromId/$toId")

        ref.addChildEventListener(object: ChildEventListener {
            override fun onCancelled(error: DatabaseError) {}
            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val chatMessage = snapshot.getValue(ChatMessage::class.java)
                Log.d("d", chatMessage!!.text)
                if (chatMessage.fromId == FirebaseAuth.getInstance().uid) {
                    val currentUser = MessagesFragment.currentUser;
                    adapter.add(ChatToItem(chatMessage.text, currentUser))
                } else {
                    adapter.add(ChatFromItem(chatMessage.text, toUser))
                }

            }
            override fun onChildRemoved(snapshot: DataSnapshot) {}

        })
    }

    private fun sendMessage() {
        val text = et_message.text.toString()

        val fromId = FirebaseAuth.getInstance().uid
        val user = arguments?.getParcelable<User>("userItem")
        val toId = user!!.uid;

        val reference = FirebaseDatabase.getInstance().getReference("/user-messages/$fromId/$toId").push()
        val toReference = FirebaseDatabase.getInstance().getReference("/user-messages/$toId/$fromId").push()
        val chatMessage = ChatMessage(reference.key!!, text, fromId!!, toId, System.currentTimeMillis());
        reference.setValue(chatMessage).addOnSuccessListener { Log.d("d", "Message sent") }
        toReference.setValue(chatMessage).addOnSuccessListener { Log.d("d", "ToMessage sent"); et_message.text.clear(); rv_chat.scrollToPosition(adapter.itemCount - 1) }
    }
}

class ChatFromItem(val text: String, val user: User?): Item<ViewHolder>() {
    override fun getLayout(): Int {
        return R.layout.chat_from_row
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.tv_from_row.text = text
        Picasso.get().load(user!!.profileImageUrl).into(viewHolder.itemView.iv_chat_from_row)
    }
}
class ChatToItem(val text: String, val user: User?): Item<ViewHolder>() {
    override fun getLayout(): Int {
        return R.layout.chat_to_row
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.tv_from_row.text = text
        Picasso.get().load(user!!.profileImageUrl).into(viewHolder.itemView.iv_chat_to_row)
    }
}