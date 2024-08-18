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
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
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

@Composable
fun LoginScreen(){

    val gradientColors = listOf(
        Color(0xFFFFFFFF),
        Color(0xFF00BCD4)
    )

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(gradientColors)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {

        Image(painter = painterResource(id = R.drawable.logo), contentDescription = "Imagen Login",
        modifier = Modifier.size(180.dp))

        Spacer(modifier = Modifier.height(5.dp))

        Text(text = "Bienvenido",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color.DarkGray
        )

        // EditText de Material Design
        OutlinedTextField(value = "", onValueChange = {},
            label = { Text(text = "Usuario")}
        )

        Spacer(modifier = Modifier.height(5.dp))

        OutlinedTextField(value = "", onValueChange = {},
            label = { Text(text = "Contraseña")}
        )

        Spacer(modifier = Modifier.height(5.dp))

        Button(onClick = {
            val navigate = Intent(context, HomeActivity::class.java)
            context.startActivity(navigate)
        },
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.width(280.dp),
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.White,
                containerColor = Color(0xFF0C6E7A)
            )
            ) {
            Text(text = "Iniciar Sesion")
        }

        Spacer(modifier = Modifier.height(5.dp))

        //olvide mi contraseña y Registro
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(text = "¿Olvidaste tu contraseña?",
                modifier = Modifier.clickable {
                    val navigate = Intent(context, RecuperarActivity::class.java)
                    context.startActivity(navigate)
                },
                fontWeight = FontWeight.SemiBold
            )
            Text(text = "Registrarse",
                modifier = Modifier.clickable {
                    val navigate = Intent(context, RegistroActivity::class.java)
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
    LoginScreen()
}