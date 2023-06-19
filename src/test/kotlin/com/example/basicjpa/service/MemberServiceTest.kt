package com.example.basicjpa.service

import com.example.basicjpa.domain.Member
import com.example.basicjpa.repository.MemberRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.fail
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.transaction.annotation.Transactional

@ExtendWith(SpringExtension::class)
@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    lateinit var memberService: MemberService

    @Autowired
    lateinit var memberRepository: MemberRepository

    @Test
    @Throws(Exception::class)
    fun `회원가입`() {

        //given
        val member = Member("kim")

        //when
        val saveId = memberService.join(member)

        //then
        assertEquals(member, memberRepository.findOne(saveId))
    }

    @Test
    @Throws(Exception::class)
    fun `중복회원예외`() {

        //given
        val member1 = Member("kim1")
        val member2 = Member("kim1")

        //when

        val e = org.junit.jupiter.api.assertThrows<IllegalStateException> {
            memberService.join(member1)
            memberService.join(member2)
        }

        //then
        assertEquals(e.message, "이미 존재 하는 멤버 입니다")
    }
}
