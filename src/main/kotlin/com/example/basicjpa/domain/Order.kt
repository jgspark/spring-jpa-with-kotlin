package com.example.basicjpa.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "orders")
data class Order(
    @Id
    @Column(name = "order_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    val member: Member,
    @OneToMany(mappedBy = "order")
    val orderItems: List<OrderItem> = listOf(),

    // fk 의 경우 order 와 delivery 를 둘다 두어도 된다.
    // 하지만 시스템의 설계에 있어서 많은 접근을 하는 곳이 좋다.
    // 즉, 주인이됨 (@JoinColumn)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_id")
    val delivery: Delivery,
    val orderDate: LocalDateTime,
    @Enumerated(EnumType.STRING)
    val orderStatus: OrderStatus
) {

}

enum class OrderStatus {
    ORDER, CANCEL
}
