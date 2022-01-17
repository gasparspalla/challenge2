package com.munidigital.bc2201.challenge2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.munidigital.bc2201.challenge2.databinding.ActivityLoginBinding
import java.util.*

class LoginActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding=ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel=ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.state.observe(this) { state ->
            Log.d("STATE",state.toString())
            val value_Toast=viewModel.checkState(state)
            viewToast(value_Toast)
            if (state.session_result){
                startActivity()
            }

        }

        binding.btnLogin.setOnClickListener {
            if (viewModel.state.value?.user == null) {
                val mail = binding.etUsr.text.toString()
                val pass = binding.etPassword.text.toString()
                val enviado = viewModel.login(mail, pass)
                if (!enviado) Toast.makeText(
                    this,
                    "Ingrese usuario y contraseña",
                    Toast.LENGTH_SHORT
                ).show()
            }


        }

    }

    private fun startActivity() {
        startActivity(Intent(this, MainActivity::class.java))
    }


    fun viewToast(toast:String){
        when{
            (toast=="Logged")->Toast.makeText(this, "Logged!", Toast.LENGTH_SHORT).show()
            (toast=="Loged out")->Toast.makeText(this, "Loged out.", Toast.LENGTH_SHORT).show()
            (toast=="Invalid user credentials")->Toast.makeText(this, "Invalid user credentials.", Toast.LENGTH_SHORT).show()
        }
    }


    override fun onPause() {
        super.onPause()
        finish()
    }
}


