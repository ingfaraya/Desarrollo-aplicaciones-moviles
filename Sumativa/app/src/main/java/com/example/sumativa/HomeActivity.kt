package com.example.sumativa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sumativa.ui.theme.SumativaTheme

class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ListaUsuarios()
        }
    }

    @Composable
    fun ListaUsuarios(){

        val gradientColors = listOf(
            Color(0xFFFFFFFF),
            Color(0xFF0BDCD4)
        ) // Define tus colores de degradado

        data class Persona(val nombre: String, val avatar: Int)
        val personas = listOf(
            Persona("Juan Perez", R.drawable.avatar2),
            Persona("Ana López", R.drawable.avatar2),
            Persona("Carlos Ruiz", R.drawable.avatar2),
            Persona("Marta Sánchez", R.drawable.avatar2),
            Persona("Pedro Jiménez", R.drawable.avatar2),
            Persona("Luisa Fernández", R.drawable.avatar2)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Brush.verticalGradient(gradientColors)), // Aplica el degradado vertical
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Spacer(modifier = Modifier.height(30.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.cardColors(
                    contentColor = Color.White,
                    containerColor = Color(0xFF0C6E7A)
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
                        contentDescription = "",
                        modifier = Modifier.size(80.dp)
                    )
                    Text(
                        text = "Hola Fernando Araya",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier.padding(20.dp)
                    )
                }

            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.LightGray),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Estos son los parrilleros de cumpleaños hoy:",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.padding(20.dp, 20.dp)
                )

                LazyColumn {
                    items(personas) { persona ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp, 5.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                            shape = RoundedCornerShape(8.dp), // Esquinas redondeadas
                            colors = CardDefaults.cardColors(
                                containerColor = Color.Black,
                                contentColor = Color.White
                            )
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Start
                            ) {
                                Image(
                                    painter = painterResource(R.drawable.avatar),
                                    contentDescription = "Logo",
                                    modifier = Modifier.size(50.dp)
                                )
                                Text(
                                    text = persona.nombre,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    modifier = Modifier.padding(10.dp)
                                )
                            }

                        }
                    }
                }
                // Color de fondo
            }

        }


    }

    @Preview
    @Composable
    fun VistaPreviaClientes(){
        ListaUsuarios()
    }
}