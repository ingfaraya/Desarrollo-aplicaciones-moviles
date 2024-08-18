package com.example.sumativa

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider

@Composable
fun RegistroScreen(usuariosViewModel: UsuariosViewModel) {

    var nombre by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var usuario by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val gradientColors = listOf(
        Color(0xFFFFFFFF),
        Color(0xFF00BCD4)
    )

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(gradientColors)), // Aplica el degradado vertical
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "",
            modifier = Modifier.size(180.dp)
        )
        Spacer(modifier = Modifier.height(5.dp))

        // Nombre
        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text(text = "Nombre") }
        )

        Spacer(modifier = Modifier.height(5.dp))

        // Teléfono
        OutlinedTextField(
            value = telefono,
            onValueChange = { telefono = it },
            label = { Text(text = "Teléfono") }
        )

        Spacer(modifier = Modifier.height(5.dp))

        // Usuario
        OutlinedTextField(
            value = usuario,
            onValueChange = { usuario = it },
            label = { Text(text = "Usuario") }
        )

        Spacer(modifier = Modifier.height(5.dp))

        // Contraseña
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(text = "Contraseña") },
            visualTransformation = PasswordVisualTransformation()
        )

        // creamos el botón de crear cuenta
        Button(
            onClick = {
                // Añadir usuario al ViewModel
                usuariosViewModel.addUser(Usuarios(nombre, telefono, usuario, password))
            },
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.width(280.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF0C6E7A),
                contentColor = Color.White
            )
        ) {
            Text(text = "Crear cuenta")
        }

        Spacer(modifier = Modifier.height(25.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = "Volver",
                modifier = Modifier.clickable() {
                    val navigate = Intent(context, MainActivity::class.java)
                    context.startActivity(navigate)
                },
                fontWeight = FontWeight.SemiBold
            )
        }


    }
}

@Preview
@Composable
fun VistaPreviaRegistro() {
    val usuariosViewModel = UsuariosViewModel() // Simulación directa
    RegistroScreen(usuariosViewModel)
}
