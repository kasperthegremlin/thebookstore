package com.thebookstore

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import com.thebookstore.BookstoreRepository

@RestController
@RequestMapping("/bookstore")
class BookstoreController @Autowired constructor(
    private val bookstoreRepository: BookstoreRepository
) {

    @GetMapping
    fun getBookstore(): BookstoreEntity {
        return bookstoreRepository.findById(1L).orElseThrow { RuntimeException("Bookstore not found") }
    }
}