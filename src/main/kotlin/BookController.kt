import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.beans.factory.annotation.Autowired

@RestController
@RequestMapping("/books")
class BookController @Autowired constructor(private val bookRepository: BookRepository) {

    // Fetch all books from the database
    @GetMapping
    fun getAllBooks(): List<Book> {
        return bookRepository.findAll()
    }
}
