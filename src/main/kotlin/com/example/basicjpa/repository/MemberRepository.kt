package com.example.basicjpa.repository

import com.example.basicjpa.domain.Member
import jakarta.persistence.EntityManager
import org.springframework.stereotype.Repository

/*
todo:
  1. 회원 등록 <br/>
  2. 회원 목록 조회 <br/>
 */
@Repository
class MemberRepository(
    val em: EntityManager
) {

    fun save(member: Member): Long {
        em.persist(member)
        return member.id
    }

    fun findOne(id: Long): Member = em.find(Member::class.java, id)

    fun findAll(): List<Member> =
        em.createQuery("select m from Member m", Member::class.java)
            .resultList

    fun findByName(name: String): List<Member> =
        em.createQuery("select m from Member m where m.name =:name", Member::class.java)
            .setParameter("name", name)
            .resultList
}
