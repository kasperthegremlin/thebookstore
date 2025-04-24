package com.thebookstore

import jakarta.persistence.Entity
import jakarta.persistence.OneToMany
import jakarta.persistence.CascadeType
import jakarta.persistence.FetchType
import jakarta.persistence.Id

@Entity
data class BookstoreEntity(
    @Id
    val id: Long = 0,  // You can give it a fixed value or auto-generate

    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val books: List<BookEntity> = listOf()
)
