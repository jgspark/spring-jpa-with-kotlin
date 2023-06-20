package com.example.basicjpa.repository

import com.example.basicjpa.domain.Item
import jakarta.persistence.EntityManager
import org.springframework.stereotype.Repository

@Repository
class ItemRepository(
    private val em: EntityManager
) {


    fun save(item: Item) {
        if (item.id == null) {
            // 신규등록
            em.persist(item)
        } else {
            // 업데이트
            em.merge(item)
        }
    }

    fun findOne(id: Long): Item = em.find(Item::class.java, id)

    fun findAll(): List<Item> =
        em.createQuery("select i from Item i", Item::class.java)
            .resultList
}
