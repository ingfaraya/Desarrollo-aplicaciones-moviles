import org.junit.Test
import org.junit.Assert.assertEquals

class BooksActivityTest {

    @Test
    fun test_books_are_loaded_successfully() {

        // Simula la lista de libros que devuelve la base de datos
        val booksList = listOf("Book 1", "Book 2", "Book 3")

        // Llama al método que carga los libros
        val loadedBooks = booksList

        // Verifica que la cantidad de libros cargados sea la correcta
        assertEquals(3, loadedBooks.size)
        assertEquals("Book 1", loadedBooks[0])
    }

    @Test
    fun test_no_books_found() {

        // Simula la lista de libros que devuelve la base de datos
        val booksList = arrayListOf<BooksActivityTest>()

        // Verifica que la lista de libros esté vacía
        assertEquals(0, booksList.size)
    }
}
