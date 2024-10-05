import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.widget.Toast
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sumativa.HomeActivity
import com.example.sumativa.R
import com.example.sumativa.RegistroActivity
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun LoginScreen() {
    var usuario by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current
    val firestore = FirebaseFirestore.getInstance()
    val coroutineScope = rememberCoroutineScope()

    val gradientColors = listOf(
        Color(0xFFFFFFFF),
        Color(0xFFFFFFFF)
    )

    // Estados para animación de sacudida
    var shouldShakeLogin by remember { mutableStateOf(false) }
    var shouldShakeRecover by remember { mutableStateOf(false) }
    var shouldShakeRegister by remember { mutableStateOf(false) }

    // Animación de sacudida
    val shakeOffsetLogin by animateFloatAsState(
        targetValue = if (shouldShakeLogin) 10f else 0f,
        animationSpec = tween(durationMillis = 100),
        finishedListener = {
            if (shouldShakeLogin) {
                coroutineScope.launch {
                    delay(100)
                    shouldShakeLogin = false
                }
            }
        }
    )

    val shakeOffsetRecover by animateFloatAsState(
        targetValue = if (shouldShakeRecover) 10f else 0f,
        animationSpec = tween(durationMillis = 100),
        finishedListener = {
            if (shouldShakeRecover) {
                coroutineScope.launch {
                    delay(100)
                    shouldShakeRecover = false
                }
            }
        }
    )

    val shakeOffsetRegister by animateFloatAsState(
        targetValue = if (shouldShakeRegister) 10f else 0f,
        animationSpec = tween(durationMillis = 100),
        finishedListener = {
            if (shouldShakeRegister) {
                coroutineScope.launch {
                    delay(100)
                    shouldShakeRegister = false
                }
            }
        }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(gradientColors)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo), // Cambia por tu logo
            contentDescription = "Logo",
            modifier = Modifier.size(180.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Usuario
        OutlinedTextField(
            value = usuario,
            onValueChange = { usuario = it },
            label = { Text("Usuario") },
            modifier = Modifier.padding(16.dp)
        )

        // Contraseña
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            modifier = Modifier.padding(16.dp),
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Botón de login
        Button(
            onClick = {
                vibratePhone(context)
                shouldShakeLogin = true

                if (usuario.isNotEmpty() && password.isNotEmpty()) {
                    // Consultar en Firestore la colección "usuarios"
                    firestore.collection("usuarios")
                        .whereEqualTo("usuario", usuario)
                        .whereEqualTo("password", password)
                        .get()
                        .addOnSuccessListener { documents ->
                            if (!documents.isEmpty) {
                                Toast.makeText(context, "Inicio de sesión exitoso", Toast.LENGTH_LONG).show()
                                val intent = Intent(context, HomeActivity::class.java)
                                context.startActivity(intent)
                            } else {
                                Toast.makeText(context, "Usuario o contraseña incorrectos", Toast.LENGTH_LONG).show()
                            }
                        }
                        .addOnFailureListener { exception ->
                            Toast.makeText(context, "Error al autenticar: ${exception.message}", Toast.LENGTH_LONG).show()
                        }
                } else {
                    Toast.makeText(context, "Por favor, ingrese usuario y contraseña", Toast.LENGTH_LONG).show()
                }
            },
            modifier = Modifier
                .padding(16.dp)
                .offset(x = shakeOffsetLogin.dp) // Animación de sacudida
        ) {
            Text("Ingresar", fontSize = 18.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Sección de enlaces para "Recuperar Contraseña" y "Registrar"
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            // Botón para recuperar contraseña
            TextButton(
                onClick = {
                    vibratePhone(context)
                    shouldShakeRecover = true
                    // Aquí puedes implementar la lógica para recuperar contraseña
                },
                modifier = Modifier.offset(x = shakeOffsetRecover.dp) // Animación de sacudida
            ) {
                Text("¿Olvidaste tu contraseña?", fontSize = 14.sp)
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Botón para registrar nuevo usuario
            TextButton(
                onClick = {
                    vibratePhone(context)
                    shouldShakeRegister = true
                    val intent = Intent(context, RegistroActivity::class.java)
                    context.startActivity(intent)
                },
                modifier = Modifier.offset(x = shakeOffsetRegister.dp) // Animación de sacudida
            ) {
                Text("Registrar", fontSize = 14.sp)
            }
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

@Preview(showBackground = true)
@Composable
fun PreviewLoginScreen() {
    LoginScreen()
}
