package com.example.sumativa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider

class RegistroActivity : ComponentActivity() {
    private lateinit var usuariosViewModel: UsuariosViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            // Inicialización del ViewModel usando ViewModelProvider
            usuariosViewModel = ViewModelProvider(this).get(UsuariosViewModel::class.java)

            RegistroScreen(usuariosViewModel)
        }
    }
}

