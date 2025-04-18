import jakarta.xml.bind.annotation.XmlElement
import jakarta.xml.bind.annotation.XmlRootElement
import jakarta.xml.bind.JAXBContext
import java.io.StringWriter
import java.io.StringReader
import java.io.File
import java.sql.Connection
import java.sql.DriverManager
import java.sql.PreparedStatement


@XmlRootElement(name = "book")
data class Book(
    @XmlElement(name = "title") var title: String = "",
    @XmlElement(name = "author") var author: String = "",
    @XmlElement(name = "year") var year: Int = 0
)

@XmlRootElement(name = "bookstore")
data class Bookstore(
    @XmlElement(name = "book") var books: MutableList<Book> = mutableListOf()

)

fun serializeBookstoreToXml(bookstore: Bookstore): String {
    val jaxbContext = JAXBContext.newInstance(Bookstore::class.java)
    val marshaller = jaxbContext.createMarshaller()
    marshaller.setProperty(jakarta.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT, true)

    val writer = StringWriter()
    marshaller.marshal(bookstore, writer)
    return writer.toString()
}

fun deserializeBookstoreFromXml(xml: String): Bookstore {
    val jaxbContext = JAXBContext.newInstance(Bookstore::class.java)
    val unmarshaller = jaxbContext.createUnmarshaller()
    val reader = StringReader(xml)
    return unmarshaller.unmarshal(reader) as Bookstore
}

fun insertOnlyNewBooks(connection: Connection, book: Book) {
    // Check if the book already exists
    val checkQuery = "SELECT COUNT(*) FROM books WHERE title = ? AND author = ?"
    val stmt = connection.prepareStatement(checkQuery)
    stmt.setString(1, book.title)
    stmt.setString(2, book.author)
    val resultSet = stmt.executeQuery()

    // If the book does not exist, insert it
    if (resultSet.next() && resultSet.getInt(1) == 0) {
        val insertQuery = "INSERT INTO books (title, author, year) VALUES (?, ?, ?)"
        val insertStmt = connection.prepareStatement(insertQuery)
        insertStmt.setString(1, book.title)
        insertStmt.setString(2, book.author)
        insertStmt.setInt(3, book.year)
        insertStmt.executeUpdate()
    }
}

fun insertBooksIntoDB(bookstore: Bookstore) {
    val url = "jdbc:postgresql://localhost:5432/bookstoredb"
    val user = "kasper"
    val password = "kasperthegremlin"

    val connection: Connection = DriverManager.getConnection(url, user, password)

    /*
    val insertQuery = "INSERT INTO books (title, author, year) VALUES (?, ?, ?)"
    val statement: PreparedStatement = connection.prepareStatement(insertQuery)

    for (book in bookstore.books) {
        statement.setString(1, book.title)
        statement.setString(2, book.author)
        statement.setInt(3, book.year)
        statement.addBatch()
    }
    */
    bookstore.books.forEach { book ->
        insertOnlyNewBooks(connection, book)
    }

    //statement.executeBatch()
    println("Books inserted into the database successfully!")

    //statement.close()
    connection.close()
}


fun main() {
    val bookstore = Bookstore(
        books = mutableListOf(
            Book("1984", "George Orwell", 1949),
            Book("To Kill a Mockingbird", "Harper Lee", 1960),
            Book("The Great Gatsby", "F. Scott Fitzgerald", 1925),
            Book("Pride and Prejudice", "Jane Austen", 1813),
            Book("Brave New World", "Aldous Huxley", 1932),
            Book("Moby-Dick", "Herman Melville", 1851),
            Book("The Catcher in the Rye", "J.D. Salinger", 1951),
            Book("The Hobbit", "J.R.R. Tolkien", 1937),
            Book("The Lion, the Witch and the Wardrobe", "C.S. Lewis", 1950),
        )
    )

    val xmlOutput = serializeBookstoreToXml(bookstore)
    println("Serialized XML:\n$xmlOutput")

    val file = File("bookstore.xml")
    file.writeText(xmlOutput)
    println("\nXML written to bookstore.xml")

    val xmlFromFile = file.readText()

    val deserializedBookstore = deserializeBookstoreFromXml(xmlOutput)
    println("\nDeserialized Bookstore:")
    deserializedBookstore.books.forEach { println("${it.title} by ${it.author} (${it.year})") }
    insertBooksIntoDB(bookstore)
}
