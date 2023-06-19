package com.example.basicjpa.service

import com.example.basicjpa.domain.Member
import com.example.basicjpa.repository.MemberRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class MemberService(
    private val memberRepository: MemberRepository
) {

    // 회원 가입
    // class level 에서 중첩해서 사용해도 기본적으로 method level 에서 사용시 우선건을 가지게 된다.
    @Transactional
    fun join(member: Member): Long {
        validateDuplicateMember(member)
        return memberRepository.save(member)
    }

    private fun validateDuplicateMember(member: Member) {
        // 성능상의 문제도 있기 때문에 username 과 같은 값은 유니크 값으로 잡는 것이 선호
        val findByNames = memberRepository.findByName(member.name)
        check(findByNames.isEmpty()) {
            "이미 존재 하는 멤버 입니다"
        }
    }

    // 회원 전체 조회
    fun findMembers() = memberRepository.findAll()

    // 회원 단건 조회
    fun findOne(id: Long) = memberRepository.findOne(id)
}
