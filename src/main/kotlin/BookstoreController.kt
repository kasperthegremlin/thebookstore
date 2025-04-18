import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.beans.factory.annotation.Autowired

@RestController
@RequestMapping("/bookstore")
class BookstoreController @Autowired constructor(private val bookstoreRepository: BookstoreRepository) {

    @GetMapping
    fun getBookstore(): Bookstore {
        return bookstoreRepository.findById(1L).orElseThrow { RuntimeException("Bookstore not found") }
    }
}
