package com.munidigital.bc2201.challenge2

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.text.BoringLayout
import android.util.Log
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.*

class MainViewModel: ViewModel() {
    private var auth: FirebaseAuth = Firebase.auth


    private val _state = MutableLiveData(State(false, null,false))
    val state : LiveData<State>
    get() = _state



    data class State(
        val loginError: Boolean,
        val user: FirebaseUser?,
        val session_result:Boolean
    )


    init {
        val user = auth.currentUser
        if (user != null){
            _state.value = (State(
                loginError = false,
                user = user,
                session_result = true
            ))
        }
    }


    fun login(mail: String, pass: String) : Boolean {
        if (mail.isNotBlank() && pass.isNotBlank()) {
            Log.d("login", "sending")
            auth.signInWithEmailAndPassword(mail, pass)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Logueo correcto, cambio el estado
                        _state.value = State(
                            false,
                            auth.currentUser,
                            session_result = true
                        )
                        Log.d("login", "success")
                    } else {
                        // Logueo incorrecto, cambio el estado
                        _state.value = State(
                            true,
                            null,
                            session_result = false
                        )
                        Log.d("login", "failure", task.exception)
                    }
                }
            return true
        } else {
            return false
        }
    }



    fun logout() {
        auth.signOut()
        _state.value = State(
            false,
            null,
            false
        )
    }


    fun checkState(state: State):String {
        var toast=""
        when {
            (!state.loginError && state.user != null && state.session_result) -> {
                onLogged(state.user)
                 toast="Logged"
            }
            (!state.loginError && state.user == null && !state.session_result) -> { // Usuario sin loguear o recien deslogueado
                toast="Loged out"
            }
            (state.loginError) -> { // El usuario intentó iniciar sesión y falló
                toast="Invalid user credentials"
            }
        }
        return toast
    }



    private fun onLogged(user: FirebaseUser) {
        user.apply {
            email?.let { Log.d("login", it) }
            isEmailVerified.let { Log.d("login", it.toString()) }
            uid.let { Log.d("login", it) }
        }
    }

}