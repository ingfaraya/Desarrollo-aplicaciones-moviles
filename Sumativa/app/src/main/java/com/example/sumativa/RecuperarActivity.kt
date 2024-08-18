package com.example.sumativa

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class RecuperarActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RecuperarPassword()
        }
    }

    @Composable
    fun RecuperarPassword() {

        val gradientColors = listOf(
            Color(0xFFFFFFFF),
            Color(0xFF00BCD4)
        )

        var email by remember { mutableStateOf("") }

        val context = LocalContext.current

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Brush.verticalGradient(gradientColors)), // Aplica el degradado
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
                value = email,
                onValueChange = { email = it },
                label = { Text(text = "Correo Electrónico") }
            )

            Spacer(modifier = Modifier.height(5.dp))

            // Crea el botón de crear cuenta
            Button(
                onClick = { },
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.width(280.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF0C6E7A),
                    contentColor = Color.White
                )
            ){
                // Continuación del RowScope
                Text(text = "Recuperar Contraseña")
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
    fun VistaPrevia()
    {
        RecuperarPassword()
    }

}