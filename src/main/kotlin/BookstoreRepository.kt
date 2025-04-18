import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BookstoreRepository : JpaRepository<Bookstore, Long> {
    // Custom queries can be added here if needed
}
