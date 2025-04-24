package com.thebookstore

import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import org.springframework.context.annotation.Profile

@Component
@Profile("dev")
class DataLoader(private val bookRepository: BookRepository) : CommandLineRunner {
    override fun run(vararg args: String?) {
        if (bookRepository.count() == 0L) {
            bookRepository.saveAll(
                listOf(
                    BookEntity(title = "Dune", author = "Frank Herbert", year = 1965),
                    BookEntity(title = "1984", author = "George Orwell", year = 1949),
                    BookEntity(title = "The Hobbit", author = "J.R.R. Tolkien", year = 1937)
                )
            )
            println("Sample books added to database.")
        }
    }
}
