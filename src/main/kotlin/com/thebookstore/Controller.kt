package com.thebookstore

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HelloController {

    @GetMapping("/")
    fun hello(): String {
        return "The Bookstore project now integrated with Spring Boot"
    }
}