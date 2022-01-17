package com.munidigital.bc2201.challenge2

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.munidigital.bc2201.challenge2.MainViewModel.State
import com.munidigital.bc2201.challenge2.databinding.ActivitySplashBinding

class SplashActivity:AppCompatActivity() {
    private val SPLASH_DURATION:Long=2000

    private lateinit var binding:ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val viewModel= ViewModelProvider(this).get(MainViewModel::class.java)

        getAppVersion()

        val run= Runnable {
            viewModel.state.observe(this) { state ->
                if (state.session_result)startActivity(Intent(this, MainActivity::class.java))
                else startActivity(Intent(this, LoginActivity::class.java))
            }
            finish()
        }

        Handler(Looper.getMainLooper()).postDelayed(run, SPLASH_DURATION)
    }

    private fun getAppVersion() {
        try {
            // Obtiene el numero de version y lo carga el textview de abajo a la derecha
            val version = this.packageManager.getPackageInfo(this.packageName, 0).versionName
            binding.tvVersioname.text = version
        } catch (e: PackageManager.NameNotFoundException) {
            // Log por si falla
            e.printStackTrace()
        }
    }

}