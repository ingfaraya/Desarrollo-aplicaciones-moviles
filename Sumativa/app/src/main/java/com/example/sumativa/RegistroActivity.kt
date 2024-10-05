package com.example.sumativa

import RegistroScreen
import UsuariosViewModel
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider

class RegistroActivity : ComponentActivity() {
    private lateinit var usuariosViewModel: UsuariosViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            // Inicialización del ViewModel
            usuariosViewModel = ViewModelProvider(this).get(UsuariosViewModel::class.java)

            // Pasar ViewModel a RegistroScreen
            RegistroScreen(usuariosViewModel = usuariosViewModel)
        }
    }
}
