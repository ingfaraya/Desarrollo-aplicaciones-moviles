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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HelpActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HelpScreen()
        }
    }

    @Composable
    fun HelpScreen() {
        val gradientColors = listOf(
            Color(0xFFFFFFFF),
            Color(0xFFF8F8F8)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Brush.verticalGradient(gradientColors))
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(30.dp))

            // Card de saludo personalizada con logo
            HelpGreetingCard()

            // Contenido del tutorial
            TutorialContent()

            // Botones al final
            BottomButtons()

            Spacer(modifier = Modifier.height(30.dp)) // Separación inferior entre botones y final
        }
    }

    @Composable
    fun HelpGreetingCard() {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp), // Márgenes ajustados
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(
                contentColor = Color.White,
                containerColor = Color(0xFF9C7575)
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Image(
                    painter = painterResource(id = R.drawable.avatar2),
                    contentDescription = null,
                    modifier = Modifier.size(80.dp)
                )
                Text(
                    text = "Tutorial de Uso",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }
    }

    @Composable
    fun TutorialContent() {
        Column(
            modifier = Modifier.padding(24.dp) // Aumentar el margen de los textos
        ) {
            Text(
                text = "Bienvenido al tutorial de uso de Asados Chilenos!",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Text(
                text = "Aquí te explicaremos cómo usar la aplicación y aprovechar al máximo todas sus funcionalidades. Sigue leyendo para aprender más."
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "1. Cómo navegar en la aplicación",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Text(
                text = "Utiliza las opciones de la pantalla principal para acceder a diferentes secciones como videos, música, redes sociales y libros de recetas."
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "2. Acceso rápido a recetas",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Text(
                text = "Puedes encontrar las mejores recetas para asados desde la sección de libros. Algunos de ellos están disponibles para descargar o ver en línea."
            )
        }
    }

    @Composable
    fun BottomButtons() {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp), // Ajustes en el margen inferior
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            IconButton(
                iconRes = R.drawable.home,
                onClick = { startActivity(Intent(this@HelpActivity, HomeActivity::class.java)) }
            )

            IconButton(
                iconRes = R.drawable.ayuda,
                onClick = { startActivity(Intent(this@HelpActivity, HelpActivity::class.java)) }
            )

            IconButton(
                iconRes = R.drawable.configuracion,
                onClick = { startActivity(Intent(this@HelpActivity, SettingsActivity::class.java)) }
            )
        }
    }

    @Composable
    fun IconButton(iconRes: Int, onClick: () -> Unit) {
        val context = LocalContext.current
        val coroutineScope = rememberCoroutineScope()

        var shouldShake by remember { mutableStateOf(false) }

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

        Card(
            modifier = Modifier
                .size(80.dp)
                .offset(x = shakeOffset.dp)
                .clickable {
                    vibratePhone(context)
                    shouldShake = true
                    onClick()
                },
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.Black)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Image(
                    painter = painterResource(id = iconRes),
                    contentDescription = null
                )
            }
        }
    }

    fun vibratePhone(context: Context) {
        val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            vibrator.vibrate(100)
        }
    }

    @Preview
    @Composable
    fun PreviewHelpScreen() {
        HelpScreen()
    }
}
