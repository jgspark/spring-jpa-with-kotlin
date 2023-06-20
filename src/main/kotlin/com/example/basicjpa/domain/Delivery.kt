package com.example.basicjpa.domain

import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToOne

@Entity
data class Delivery(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "delivery_id")
    val id: Long?,
    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    var order: Order?,
    @Embedded
    val address: Address,
    @Enumerated(EnumType.STRING)
    val deliveryStatus: DeliveryStatus
)

enum class DeliveryStatus {
    READY, COMP
}
