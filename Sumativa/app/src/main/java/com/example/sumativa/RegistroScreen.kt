import UsuariosViewModel
import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.widget.Toast
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.regex.Pattern

@Composable
fun RegistroScreen(usuariosViewModel: UsuariosViewModel) {
    var nombre by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var usuario by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current

    var shouldShake by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

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

    // Gradiente de fondo
    val gradientColors = listOf(
        Color(0xFFFFFFFF),
        Color(0xFFFFFFFF)
    )

    // Funciones de validación
    fun isValidNombre(input: String): Boolean {
        val regex = "^[a-zA-Z\\s]{1,40}$"
        return Pattern.matches(regex, input)
    }

    fun isValidTelefono(input: String): Boolean {
        val regex = "^[0-9]{8}$"
        return Pattern.matches(regex, input)
    }

    fun isValidUsuario(input: String): Boolean {
        val regex = "^[a-zA-Z_]{10}$"
        return Pattern.matches(regex, input)
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(gradientColors)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Inputs de nombre, teléfono, usuario y contraseña con validación
        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre") },
            modifier = Modifier
                .padding(16.dp)
                .offset(x = shakeOffset.dp),
            isError = !isValidNombre(nombre) && nombre.isNotEmpty()
        )
        if (!isValidNombre(nombre) && nombre.isNotEmpty()) {
            Text(
                text = "Nombre inválido (máximo 40 caracteres, solo letras y espacios)",
                color = Color.Red,
                fontSize = 12.sp
            )
        }

        OutlinedTextField(
            value = telefono,
            onValueChange = { telefono = it },
            label = { Text("Teléfono") },
            modifier = Modifier
                .padding(16.dp)
                .offset(x = shakeOffset.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            isError = !isValidTelefono(telefono) && telefono.isNotEmpty()
        )
        if (!isValidTelefono(telefono) && telefono.isNotEmpty()) {
            Text(
                text = "Teléfono inválido (deben ser exactamente 8 dígitos)",
                color = Color.Red,
                fontSize = 12.sp
            )
        }

        OutlinedTextField(
            value = usuario,
            onValueChange = { usuario = it },
            label = { Text("Usuario") },
            modifier = Modifier
                .padding(16.dp)
                .offset(x = shakeOffset.dp),
            isError = !isValidUsuario(usuario) && usuario.isNotEmpty()
        )
        if (!isValidUsuario(usuario) && usuario.isNotEmpty()) {
            Text(
                text = "Usuario inválido (exactamente 10 caracteres, solo letras y _)",
                color = Color.Red,
                fontSize = 12.sp
            )
        }

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            modifier = Modifier
                .padding(16.dp)
                .offset(x = shakeOffset.dp),
            visualTransformation = PasswordVisualTransformation()
        )

        // Botón de registro
        Button(
            onClick = {
                vibratePhone(context) // Vibrar al hacer clic
                if (nombre.isNotEmpty() && telefono.isNotEmpty() && usuario.isNotEmpty() && password.isNotEmpty()) {
                    if (isValidNombre(nombre) && isValidTelefono(telefono) && isValidUsuario(usuario)) {
                        usuariosViewModel.registrarUsuarioEnFirestore(
                            nombre, telefono, usuario, password, context
                        )
                    } else {
                        shouldShake = true // Sacudir si hay un error en la validación
                        Toast.makeText(context, "Por favor, corrija los campos inválidos", Toast.LENGTH_LONG).show()
                    }
                } else {
                    shouldShake = true // Sacudir si hay campos vacíos
                    Toast.makeText(context, "Por favor, complete todos los campos", Toast.LENGTH_LONG).show()
                }
            },
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Crear cuenta", fontSize = 18.sp)
        }
    }
}
