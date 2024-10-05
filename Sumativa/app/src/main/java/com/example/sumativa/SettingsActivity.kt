package com.example.sumativa

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SettingsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SettingsScreen()
        }
    }

    @Composable
    fun SettingsScreen() {
        val gradientColors = listOf(
            Color(0xFFFFFFFF),
            Color(0xFFF8F8F8)
        )
        val sharedPreferences = getSharedPreferences("Settings", MODE_PRIVATE)
        var isVibrationEnabled by remember { mutableStateOf(sharedPreferences.getBoolean("vibration", true)) }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Brush.verticalGradient(gradientColors)),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(30.dp))

            // Título
            GreetingCard()

            // Configuración de vibración
            VibrationSettings(isVibrationEnabled) { isChecked ->
                isVibrationEnabled = isChecked
                val editor: SharedPreferences.Editor = sharedPreferences.edit()
                editor.putBoolean("vibration", isChecked)
                editor.apply()
            }

            // Botones de navegación inferior
            BottomNavigationBar()
            Spacer(modifier = Modifier.height(50.dp))
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
                Text(
                    text = "Configuraciones de la Aplicación",
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.White
                )
            }
        }
    }

    @Composable
    fun VibrationSettings(isVibrationEnabled: Boolean, onCheckedChange: (Boolean) -> Unit) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = if (isVibrationEnabled) "Vibración activada" else "Vibración desactivada",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            Switch(
                checked = isVibrationEnabled,
                onCheckedChange = onCheckedChange
            )
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
                onClick = { startActivity(Intent(this@SettingsActivity, HomeActivity::class.java)) },
                size = 70.dp
            )
            IconButton(
                iconRes = R.drawable.ayuda,
                onClick = { startActivity(Intent(this@SettingsActivity, HelpActivity::class.java)) },
                size = 70.dp
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

    @Composable
    @Preview
    fun PreviewSettingsScreen() {
        SettingsScreen()
    }
}
