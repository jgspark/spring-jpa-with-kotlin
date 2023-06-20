package com.example.basicjpa.repository

import com.example.basicjpa.domain.Order
import jakarta.persistence.EntityManager
import org.springframework.stereotype.Repository

@Repository
class OrderRepository(
    private val em: EntityManager
) {

    fun save(order: Order) {
        em.persist(order)
    }

    fun findOne(id: Long): Order = em.find(Order::class.java, id)

    fun findAll(): List<Order> = em.createQuery("select o from Order o", Order::class.java)
        .resultList

}
