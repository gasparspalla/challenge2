package com.munidigital.bc2201.challenge2

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*

class ChatViewModel:ViewModel() {
    private var _chatMessageListLiveData = MutableLiveData<MutableList<ChatMessage>>()
    val chatMessageListLiveData: LiveData<MutableList<ChatMessage>>
        get() = _chatMessageListLiveData

    private val responses = arrayOf("Si", "Pregunta de nuevo", "No", "Es muy probable", "No lo creo",
        "Tal vez", "No se 😓")

//    private var handler: Handler = Handler()

    init {
        _chatMessageListLiveData.value = mutableListOf()
    }

    fun addMessage(chatMessage: ChatMessage) {
        val mutableList = _chatMessageListLiveData.value!!
        mutableList.add(chatMessage)
        _chatMessageListLiveData.value = mutableList
    }

    fun createResponse() {
        val runnable = Runnable {
            val random = Random().nextInt(responses.size)

            val response = responses[random]
            val chatMessage = ChatMessage(System.currentTimeMillis(), response, false)
            val mutableList = _chatMessageListLiveData.value!!
            mutableList.add(chatMessage)
            _chatMessageListLiveData.value = mutableList
        }

        val handler=Handler()
        handler.postDelayed(runnable, 2000)
    }
}