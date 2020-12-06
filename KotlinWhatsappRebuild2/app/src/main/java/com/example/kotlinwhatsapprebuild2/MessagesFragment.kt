package com.example.kotlinwhatsapprebuild2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.fragment_messages.*
import kotlinx.android.synthetic.main.recent_message_row.view.*


/**
 * A simple [Fragment] subclass.
 * Use the [MessagesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MessagesFragment : Fragment() {

    val adapter = GroupAdapter<ViewHolder>()

    companion object {
        var currentUser: User? = null;
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fetchCurrentUser()
        checkIfUserLoggedIn()

    }

    val recentMessagesHashMap = HashMap<String, ChatMessage?>()

    private fun listenRecentMessages() {
        rv_recent_messages.adapter = adapter
        val fromId = FirebaseAuth.getInstance().uid
        val ref = FirebaseDatabase.getInstance().getReference("/latest-messages/$fromId")
        ref.addChildEventListener(object: ChildEventListener {
            override fun onCancelled(error: DatabaseError) {}
            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                val chatMessage = snapshot.getValue(ChatMessage::class.java)
                recentMessagesHashMap[snapshot.key!!] = chatMessage
                refreshRvRecentMessages()
            }
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val chatMessage = snapshot.getValue(ChatMessage::class.java)
                recentMessagesHashMap[snapshot.key!!] = chatMessage
                refreshRvRecentMessages()
            }
            override fun onChildRemoved(snapshot: DataSnapshot) {}

        })
    }

    private fun refreshRvRecentMessages() {
        adapter.clear()
        recentMessagesHashMap.values.forEach{
            adapter.add(LatestMessageRow(it))
        }
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
        listenRecentMessages()
        rv_recent_messages.adapter = adapter
        rv_recent_messages.addItemDecoration(DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL))

        adapter.setOnItemClickListener { item, view ->
            val latestMessageRow = item as LatestMessageRow
            val userBundle = bundleOf(Pair("userItem", latestMessageRow.chatPartnerUser))

            findNavController().navigate(R.id.action_messagesFragment_to_chatActivity, userBundle)
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

    class LatestMessageRow(val chatMessage: ChatMessage?): Item<ViewHolder>() {
        var chatPartnerUser: User? = null

        override fun getLayout(): Int {
            return R.layout.recent_message_row
        }

        override fun bind(viewHolder: ViewHolder, position: Int) {
            viewHolder.itemView.tv_recent_text.text = chatMessage!!.text

            val chatPartnerId: String
            if (chatMessage.fromId == FirebaseAuth.getInstance().uid) {
                chatPartnerId = chatMessage.toId
            } else {
                chatPartnerId = chatMessage.fromId
            }

            val ref = FirebaseDatabase.getInstance().getReference("/users/$chatPartnerId")
            ref.addListenerForSingleValueEvent(object: ValueEventListener {
                override fun onCancelled(error: DatabaseError) {}
                override fun onDataChange(snapshot: DataSnapshot) {
                    chatPartnerUser = snapshot.getValue(User::class.java)
                    viewHolder.itemView.tv_recent_name.text = chatPartnerUser!!.username
                    Picasso.get().load(chatPartnerUser?.profileImageUrl).into(viewHolder.itemView.iv_profile_picture)
                }
            })


        }

    }
}

