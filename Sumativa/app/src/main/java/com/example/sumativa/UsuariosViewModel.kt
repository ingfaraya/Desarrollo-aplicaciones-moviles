package com.example.sumativa

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class UsuariosViewModel : ViewModel() {
    var listaUsuarios = mutableStateListOf<Usuarios>()

    fun addUser(usuario: Usuarios) {
        listaUsuarios.add(usuario)
    }

    fun removeUser(usuario: Usuarios) {
        listaUsuarios.remove(usuario)
    }
}
