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
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeActivity : ComponentActivity() {
    class Persona(nombre: String, avatar: Int)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HomeScreen()
        }
    }

    @Composable
    fun HomeScreen() {
        val gradientColors = listOf(
            Color(0xFFFFFFFF),
            Color(0xFFF8F8F8)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Brush.verticalGradient(gradientColors)),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(30.dp))

            // Card de saludo personalizada
            GreetingCard()

            Spacer(modifier = Modifier.height(30.dp))

            // Lista de opciones de navegación
            NavigationList()

            // Botones al final
            BottomButtons()

            Spacer(modifier = Modifier.height(30.dp))
        }
    }

    @Composable
    fun GreetingCard() {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
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
                    text = "Bienvenido parrillero!!",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }
    }

    @Composable
    fun NavigationList() {
        val options = listOf(
            "Mejores Videos de Asados",
            "Mejores Playlists de Música para Asados",
            "Mejores Redes Sociales para Asados",
            "Mejores Libros de Recetas para Asados"
        )
        val icons = listOf(
            R.drawable.logo,
            R.drawable.logo,
            R.drawable.logo,
            R.drawable.logo
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            options.forEachIndexed { index, option ->
                NavigationCard(option, icons[index]) {
                    when (index) {
                        0 -> startActivity(Intent(this@HomeActivity, VideosActivity::class.java))
                        1 -> startActivity(Intent(this@HomeActivity, MusicActivity::class.java))
                        2 -> startActivity(Intent(this@HomeActivity, SocialMediaActivity::class.java))
                        3 -> startActivity(Intent(this@HomeActivity, BooksActivity::class.java))
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }

    @Composable
    fun NavigationCard(option: String, icon: Int, onClick: () -> Unit) {
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
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
                .offset(x = shakeOffset.dp)
                .clickable {
                    vibratePhone(context)
                    shouldShake = true
                    onClick()
                },
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.Black)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Image(
                    painter = painterResource(id = icon),
                    contentDescription = null,
                    modifier = Modifier.size(50.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = option,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }

    @Composable
    fun BottomButtons() {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            IconButton(
                iconRes = R.drawable.home,
                onClick = { startActivity(Intent(this@HomeActivity, HomeActivity::class.java)) }
            )
            IconButton(
                iconRes = R.drawable.ayuda,
                onClick = { startActivity(Intent(this@HomeActivity, HelpActivity::class.java)) }
            )
            IconButton(
                iconRes = R.drawable.configuracion,
                onClick = { startActivity(Intent(this@HomeActivity, SettingsActivity::class.java)) }
            )
        }
    }

    @Composable
    fun IconButton(iconRes: Int, onClick: () -> Unit) {
        Card(
            modifier = Modifier
                .size(80.dp)
                .clickable { onClick() },
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
    fun VistaPrevia() {
        HomeScreen()
    }
}
