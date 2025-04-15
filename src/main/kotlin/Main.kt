import jakarta.xml.bind.annotation.XmlElement
import jakarta.xml.bind.annotation.XmlRootElement
import jakarta.xml.bind.JAXBContext
import java.io.StringWriter
import java.io.StringReader
import java.io.File

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
            Book("The Hobbit", "J.R.R. Tolkien", 1937)
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
}
