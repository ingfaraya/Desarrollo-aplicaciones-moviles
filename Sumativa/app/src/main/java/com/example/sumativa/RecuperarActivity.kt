package com.example.sumativa

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RecuperarActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RecuperarPasswordScreen()
        }
    }

    @Composable
    fun RecuperarPasswordScreen() {
        val gradientColors = listOf(
            Color(0xFFFFFFFF),
            Color(0xFFFFFFFF)
        )

        var email by remember { mutableStateOf("") }
        val context = LocalContext.current
        val coroutineScope = rememberCoroutineScope()

        // Estado para la animación de sacudida
        var shouldShake by remember { mutableStateOf(false) }

        // Animación de sacudida
        val shakeOffset by animateFloatAsState(
            targetValue = if (shouldShake) 10f else 0f,
            animationSpec = tween(durationMillis = 100),
            finishedListener = {
                if (shouldShake) {
                    coroutineScope.launch {
                        delay(100)
                        shouldShake = false
                    }
                }
            }
        )

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

            // Correo Electrónico
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text(text = "Correo Electrónico") },
                modifier = Modifier.padding(16.dp)
            )

            Spacer(modifier = Modifier.height(5.dp))

            // Botón de recuperar contraseña
            Button(
                onClick = {
                    vibratePhone(context) // Vibrar el dispositivo
                    shouldShake = true // Iniciar la animación de sacudida
                    // Implementar la lógica de recuperación aquí
                },
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .width(280.dp)
                    .offset(x = shakeOffset.dp), // Aplicar la animación de sacudida
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF0C6E7A),
                    contentColor = Color.White
                )
            ) {
                Text(text = "Recuperar Contraseña")
            }

            Spacer(modifier = Modifier.height(25.dp))

            // Texto para volver al inicio
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    text = "Volver",
                    modifier = Modifier.clickable {
                        val navigate = Intent(context, MainActivity::class.java)
                        context.startActivity(navigate)
                    },
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }

    // Función para hacer vibrar el teléfono
    fun vibratePhone(context: Context) {
        val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE)) // Vibración de 100ms
        } else {
            vibrator.vibrate(100) // Vibración de 100ms para versiones anteriores a Android Oreo
        }
    }

    @Preview
    @Composable
    fun VistaPrevia() {
        RecuperarPasswordScreen()
    }
}
