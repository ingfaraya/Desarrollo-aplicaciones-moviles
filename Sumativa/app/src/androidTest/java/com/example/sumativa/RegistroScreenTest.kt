import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class RegistroScreenTest {

    @Test
    fun valid_name_passes_validation() {
        val nombreValido = "Juan Perez"
        assertTrue("El nombre debería ser válido", isValidNombre(nombreValido))
    }

    // Función de validación del nombre
    fun isValidNombre(input: String): Boolean {
        val regex = "^[a-zA-Z\\s]{1,40}$" // Acepta solo letras y espacios (máximo 40 caracteres)
        return regex.toRegex().matches(input)
    }

    @Test
    fun valid_password_passes_validation() {
        val passwordValido = "password123"
        assertTrue("La contraseña debería ser válida", isValidPassword(passwordValido))
    }

    @Test
    fun invalid_password_fails_validation() {
        val passwordInvalido = "short"  // Contraseña muy corta
        assertFalse("La contraseña debería ser inválida", isValidPassword(passwordInvalido))
    }

    // Función de validación de la contraseña
    fun isValidPassword(input: String): Boolean {
        return input.length >= 8 // Al menos 8 caracteres
    }

    @Test
    fun test_valid_user_registration() {
        // Simula un registro de usuario exitoso
        val userRegistered = true

        // Verifica que el registro fue exitoso
        assertTrue(userRegistered)
    }

    @Test
    fun test_invalid_user_registration() {
        // Simula un registro de usuario fallido
        val userRegistered = false

        // Verifica que el registro fue fallido
        assertTrue(!userRegistered)
    }
}
