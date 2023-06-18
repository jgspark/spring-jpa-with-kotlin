package com.example.basicjpa.domain

import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany

@Entity
data class Member(
    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    val name: String,
    @Embedded
    val address: Address,
    // 연관관계의 주인이 아니다.
    // 명시적선언 (mappedBy)
    @OneToMany(mappedBy = "member")
    val orders: List<Order> = listOf()
) {

    constructor(name: String) : this(0L, name, Address())
}
