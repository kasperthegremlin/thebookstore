import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Column

@Entity
data class BookEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,  // ID for primary key

    @Column(nullable = false)
    val title: String = "",

    @Column(nullable = false)
    val author: String = "",

    @Column(nullable = false)
    val year: Int = 0
)
