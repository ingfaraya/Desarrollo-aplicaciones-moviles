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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class VideosActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VideosScreen()
        }
    }

    @Composable
    fun VideosScreen() {
        val gradientColors = listOf(
            Color(0xFFFFFFFF),
            Color(0xFFF8F8F8)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Brush.verticalGradient(gradientColors)),
            verticalArrangement = Arrangement.SpaceBetween, // Espacio entre la lista de videos y la barra de navegación
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(10.dp))

            // Card de saludo personalizada con logo
            GreetingCard()

            Spacer(modifier = Modifier.height(20.dp))

            // Lista de videos
            VideoList()

            // Barra de navegación inferior
            BottomNavigationBar()

            Spacer(modifier = Modifier.height(60.dp)) // Espaciado adicional al final
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
                    painter = painterResource(id = R.drawable.avatar2), // Cambia al recurso adecuado
                    contentDescription = null,
                    modifier = Modifier.size(80.dp)
                )
                Text(
                    text = "Los mejores videos!!",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.padding(start = 20.dp)
                )
            }
        }
    }

    @Composable
    fun VideoList() {
        val videos = listOf(
            Canal("Asado Económico", "PYTj91aAQLk"),
            Canal("Master Parrilla", "MasterParrilla"),
            Canal("Parrillero Chileno", "ParrilleroChileno"),
            Canal("Recetas de la Parrilla", "RecetasParrilla")
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            items(videos) { video ->
                VideoItem(video) {
                    // Navegar a VideoPlayerActivity cuando se haga clic
                    val intent = Intent(this@VideosActivity, VideoPlayerActivity::class.java)
                    intent.putExtra("VIDEO_ID", video.url)
                    startActivity(intent)
                }
            }
        }
    }

    @Composable
    fun VideoItem(canal: Canal, onClick: () -> Unit) {
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
                .padding(vertical = 8.dp)
                .offset(x = shakeOffset.dp)
                .clickable {
                    vibratePhone(context)
                    shouldShake = true
                    onClick()
                },
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
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
                    painter = painterResource(id = R.drawable.avatar), // Cambia al recurso adecuado
                    contentDescription = null,
                    modifier = Modifier.size(50.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = canal.nombre,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }

    @Composable
    fun BottomNavigationBar() {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                iconRes = R.drawable.home, // Icono de home
                onClick = { startActivity(Intent(this@VideosActivity, HomeActivity::class.java)) },
                size = 70.dp // Tamaño ajustado para los botones
            )
            IconButton(
                iconRes = R.drawable.ayuda, // Icono de ayuda
                onClick = { startActivity(Intent(this@VideosActivity, HelpActivity::class.java)) },
                size = 70.dp // Tamaño ajustado para los botones
            )
            IconButton(
                iconRes = R.drawable.configuracion, // Icono de configuraciones
                onClick = { startActivity(Intent(this@VideosActivity, SettingsActivity::class.java)) },
                size = 70.dp // Tamaño ajustado para los botones
            )
        }
    }

    @Composable
    fun IconButton(iconRes: Int, onClick: () -> Unit, size: Dp) {
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

        Box(
            modifier = Modifier
                .size(size)
                .offset(x = shakeOffset.dp)
                .clickable {
                    vibratePhone(context)
                    shouldShake = true
                    onClick()
                },
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = iconRes),
                contentDescription = null,
                modifier = Modifier.size(size)
            )
        }
    }

    // Función para hacer vibrar el teléfono
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
    fun PreviewVideosScreen() {
        VideosScreen()
    }

    data class Canal(val nombre: String, val url: String)
}
