package com.munidigital.bc2201.challenge2

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.munidigital.bc2201.challenge2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: ChatAdapter
    private lateinit var binding: ActivityMainBinding
    private lateinit var chatViewModel: ChatViewModel
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        chatViewModel=ViewModelProvider(this).get(ChatViewModel::class.java)
        viewModel=ViewModelProvider(this).get(MainViewModel::class.java)

        binding.chatRecycler.layoutManager = LinearLayoutManager(this)
        adapter = ChatAdapter(this)
        binding.chatRecycler.adapter = adapter

        chatViewModel.chatMessageListLiveData.observe(this){
                chatMessageList ->
            adapter.submitList(chatMessageList)
            binding.chatRecycler.scrollToPosition(chatMessageList.size - 1)
            binding.chatEmptyView.visibility = if (chatMessageList.isEmpty()) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }

        setupSendMessageLayout(binding)
        logOut()
    }

    private fun setupSendMessageLayout(binding: ActivityMainBinding) {
        binding.sendMessageButton.setOnClickListener {
            val message = binding.messageEdit.text.toString()
            if (message.isEmpty()) {
                Toast.makeText(this, getString(R.string.message_empty),
                    Toast.LENGTH_SHORT).show()
            } else {
                val chatMessage = ChatMessage(System.currentTimeMillis(), message, true)
                chatViewModel.addMessage(chatMessage)
                chatViewModel.createResponse()
                binding.messageEdit.setText("")
            }
        }
    }

    private fun logOut(){
        val toolbar_binding=findViewById<ImageView>(R.id.iv_logout)
        toolbar_binding.setOnClickListener{
            onPause()
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.logout()
        startActivity(Intent(this,LoginActivity::class.java))
    }

}