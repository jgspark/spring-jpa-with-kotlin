package com.example.basicjpa.infra.repository

import com.example.basicjpa.domain.Member
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.transaction.annotation.Transactional

@ExtendWith(SpringExtension::class)
@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    lateinit var memberRepository: MemberRepository

    @Test
    // 테스트 케이스에 트랜잭션을 걸면, 바로 롤백 처리를 해버림
    @Transactional
    // 테스트 케이스 롤백을 막기위해서 사용
    @Rollback(false)
    @Throws(Exception::class)
    fun testMember() {

        //given
        val member = Member("테스트")

        //when
        val memberId = memberRepository.save(member)
        val findMember = memberRepository.find(memberId)

        //then
        assertEquals(findMember.id, member.id)
        assertEquals(findMember.name, member.name)
        assertEquals(findMember, member)
    }
}
