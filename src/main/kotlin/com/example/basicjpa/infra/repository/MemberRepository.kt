package com.example.basicjpa.infra.repository

import com.example.basicjpa.domain.Member
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.stereotype.Repository

@Repository
class MemberRepository(
    @PersistenceContext
    val em: EntityManager
) {

    fun save(member: Member): Long {
        em.persist(member)
        return member.id
    }

    fun find(id: Long): Member {
        return em.find(Member::class.java, id)
    }
}
