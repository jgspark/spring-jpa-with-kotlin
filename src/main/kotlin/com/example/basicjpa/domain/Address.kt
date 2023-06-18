package com.example.basicjpa.domain

import jakarta.persistence.Embeddable

@Embeddable
data class Address(
    val city: String?,
    val street: String?,
    val zipcode: String?
) {

    constructor() : this(null, null, null)
}
