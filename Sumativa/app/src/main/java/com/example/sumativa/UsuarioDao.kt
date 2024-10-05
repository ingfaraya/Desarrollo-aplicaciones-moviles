import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.sumativa.Usuarios

@Dao
interface UsuarioDao {
    @Insert
    suspend fun insertarUsuario(usuario: Usuario)

    @Query("SELECT * FROM usuarios WHERE usuario = :usuario AND password = :password")
    suspend fun login(usuario: String, password: String): Usuario?
    fun insertUser(usuarios: Usuarios)
}
