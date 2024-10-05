import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.example.sumativa.LoginActivity

class UsuariosViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()

    // Verifica si el usuario ya existe
    fun existeUsuario(usuario: String, onResult: (Boolean) -> Unit) {
        db.collection("usuarios").whereEqualTo("usuario", usuario).get()
            .addOnSuccessListener { documents ->
                onResult(!documents.isEmpty) // Si la colección no está vacía, el usuario existe
            }
            .addOnFailureListener {
                onResult(false) // En caso de error, asumimos que el usuario no existe
            }
    }

    // Registra un nuevo usuario en Firestore
    fun registrarUsuarioEnFirestore(
        nombre: String, telefono: String, usuario: String, password: String, context: Context
    ) {
        existeUsuario(usuario) { existe ->
            if (!existe) {
                // Si el usuario no existe, lo registramos
                val nuevoUsuario = hashMapOf(
                    "nombre" to nombre,
                    "telefono" to telefono,
                    "usuario" to usuario,
                    "password" to password
                )

                db.collection("usuarios").add(nuevoUsuario)
                    .addOnSuccessListener {
                        Toast.makeText(context, "Usuario registrado exitosamente", Toast.LENGTH_LONG).show()
                        // Redirigir al Login
                        val intent = Intent(context, LoginActivity::class.java)
                        context.startActivity(intent)
                    }
                    .addOnFailureListener {
                        Toast.makeText(context, "Error al registrar el usuario", Toast.LENGTH_LONG).show()
                    }
            } else {
                // Si el usuario ya existe, mostrar un mensaje
                Toast.makeText(context, "El usuario ya existe, elija otro", Toast.LENGTH_LONG).show()
            }
        }
    }
}
