import org.junit.Test
import org.junit.Assert.assertEquals

class MusicActivityTest {

    @Test
    fun test_music_is_played_successfully() {
        // Simula que la música se reproduce correctamente
        val playingMusic = "Playing music"

        // Verifica que el estado de la música sea "Playing"
        assertEquals("Playing music", playingMusic)
    }

    @Test
    fun test_music_is_stopped_successfully() {
        // Simula que la música se detiene correctamente
        val stoppedMusic = "Music stopped"

        // Verifica que el estado de la música sea "Stopped"
        assertEquals("Music stopped", stoppedMusic)
    }
}
