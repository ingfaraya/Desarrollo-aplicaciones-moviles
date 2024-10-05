package com.example.sumativa

import android.content.Context
import android.content.Intent
import android.net.Uri
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MusicActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MusicScreen()
        }
    }

    @Composable
    fun MusicScreen() {
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

            // Card de saludo personalizada con logo
            GreetingCard()

            Spacer(modifier = Modifier.height(30.dp))

            // Lista de playlist de Spotify
            PlaylistList()

            // Botones inferiores
            BottomNavigationBar()

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
                    painter = painterResource(id = R.drawable.avatar2), // Cambia al recurso adecuado
                    contentDescription = null,
                    modifier = Modifier.size(80.dp)
                )
                Text(
                    text = "Mejores cumbias chilenas",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }
    }

    @Composable
    fun PlaylistList() {
        val playlists = listOf(
            Playlist("Cumbias Chilenas De La Wena", "https://open.spotify.com/playlist/2oahIXAhtphu99ityiNeMc?si=10eedb7e389c4d9a"),
            Playlist("Cumbias Para Bailar y Asados", "https://open.spotify.com/playlist/4vBWuDK88sHJ80cmoAy0fP?si=0686bf383a864569"),
            Playlist("Cumbias Inolvidables", "https://open.spotify.com/playlist/3EtHrJCaG2tnXgHqqBzs13?si=7f399e44b0d94da1"),
            Playlist("Fiestas Bailables", "https://open.spotify.com/playlist/4f8gAmIFrKH7lVsqvpVLt4?si=65055dae43734031")
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            items(playlists) { playlist ->
                PlaylistItem(playlist)
            }
        }
    }

    @Composable
    fun PlaylistItem(playlist: Playlist) {
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
                .padding(8.dp)
                .offset(x = shakeOffset.dp)
                .clickable {
                    vibratePhone(context)
                    shouldShake = true
                    openPlaylistInSpotify(context, playlist.url)
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
                    painter = painterResource(id = R.drawable.logo), // Cambia al recurso adecuado
                    contentDescription = null,
                    modifier = Modifier.size(50.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = playlist.nombre,
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
                iconRes = R.drawable.home,
                onClick = { startActivity(Intent(this@MusicActivity, HomeActivity::class.java)) }
            )
            IconButton(
                iconRes = R.drawable.ayuda,
                onClick = { startActivity(Intent(this@MusicActivity, HelpActivity::class.java)) }
            )
            IconButton(
                iconRes = R.drawable.configuracion,
                onClick = { startActivity(Intent(this@MusicActivity, SettingsActivity::class.java)) }
            )
        }
    }

    @Composable
    fun IconButton(iconRes: Int, onClick: () -> Unit) {
        val context = LocalContext.current
        var shouldShake by remember { mutableStateOf(false) }
        val shakeOffset by animateFloatAsState(
            targetValue = if (shouldShake) 10f else 0f,
            animationSpec = tween(durationMillis = 100),
            finishedListener = { shouldShake = false }
        )

        Box(
            modifier = Modifier
                .size(80.dp)
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
                modifier = Modifier.size(80.dp)
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

    // Función para abrir la playlist en Spotify
    fun openPlaylistInSpotify(context: Context, url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        intent.putExtra(Intent.EXTRA_REFERRER, Uri.parse("android-app://${context.packageName}"))
        context.startActivity(intent)
    }

    @Preview
    @Composable
    fun PreviewMusicScreen() {
        MusicScreen()
    }

    data class Playlist(val nombre: String, val url: String)
}
