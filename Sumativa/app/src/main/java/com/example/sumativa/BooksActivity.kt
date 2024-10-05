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

class BooksActivity<AppDatabase> : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BooksScreen()
        }
    }

    @Composable
    fun BooksScreen() {
        val gradientColors = listOf(
            Color(0xFFFFFFFF),
            Color(0xFFF8F8F8)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Brush.verticalGradient(gradientColors)),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally // Centrar horizontalmente los elementos
        ) {
            Spacer(modifier = Modifier.height(30.dp))  // Separación inicial superior

            // Tarjeta de saludo
            GreetingCard()

            Spacer(modifier = Modifier.height(20.dp))

            // Lista de libros
            BookList()

            // Botones inferiores
            BottomButtons()

            Spacer(modifier = Modifier.height(16.dp)) // Separación inferior para la barra de botones
        }
    }

    @Composable
    fun GreetingCard() {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),  // Asegurar que la tarjeta tenga espacio lateral
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
                    modifier = Modifier.size(80.dp)  // Tamaño de imagen consistente
                )
                Text(
                    text = "Libros de Recetas para Asados",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.padding(start = 20.dp)
                )
            }
        }
    }

    @Composable
    fun BookList() {
        val books = listOf(
            Book("La guía máxima de la carne", "https://progcarne.com/storage/app/media/sabores/edicion-xii.pdf"),
            Book("Manual de Cortes", "https://www.inac.uy/innovaportal/file/6726/1/manual_abasto_low.pdf"),
            Book("Manual del Parrillero Criollo", "https://todorecurso.files.wordpress.com/2010/04/manual-del-parrillero-criollo-litart.pdf"),
            Book("Toda La Carne en el Asador", "https://velocidadcuchara.com/descargas/recetarios/pdf/toda-la-carne-en-el-asador.pdf")
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)  // Consistencia en la separación lateral
        ) {
            items(books) { book ->
                BookItem(book) {
                    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(book.url))
                    startActivity(browserIntent)
                }
            }
        }
    }

    @Composable
    fun BookItem(book: Book, onClick: () -> Unit) {
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
                .padding(vertical = 8.dp) // Espaciado entre elementos
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
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = null,
                    modifier = Modifier.size(50.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = book.nombre,
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
            val context = LocalContext.current

            IconButton(
                iconRes = R.drawable.home,  // Reemplaza con el icono de casa
                onClick = { startActivity(Intent(context, HomeActivity::class.java)) }
            )

            IconButton(
                iconRes = R.drawable.ayuda,  // Reemplaza con el icono de ayuda
                onClick = { startActivity(Intent(context, HelpActivity::class.java)) }
            )

            IconButton(
                iconRes = R.drawable.configuracion,  // Reemplaza con el icono de configuraciones
                onClick = { startActivity(Intent(context, SettingsActivity::class.java)) }
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
            finishedListener = {
                shouldShake = false
            }
        )

        Image(
            painter = painterResource(id = iconRes),
            contentDescription = null,
            modifier = Modifier
                .size(80.dp)  // Tamaño fijo de los botones
                .offset(x = shakeOffset.dp)
                .clickable {
                    vibratePhone(context)
                    shouldShake = true
                    onClick()
                }
        )
    }

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
    fun PreviewBooksScreen() {
        BooksScreen()
    }

    data class Book(val nombre: String, val url: String)
}
