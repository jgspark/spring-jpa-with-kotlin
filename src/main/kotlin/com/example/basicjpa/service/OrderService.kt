package com.example.basicjpa.service

import com.example.basicjpa.domain.Delivery
import com.example.basicjpa.domain.DeliveryStatus
import com.example.basicjpa.domain.Order
import com.example.basicjpa.domain.OrderItem
import com.example.basicjpa.repository.ItemRepository
import com.example.basicjpa.repository.MemberRepository
import com.example.basicjpa.repository.OrderRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.lang.RuntimeException

@Service
@Transactional(readOnly = true)
class OrderService(
    private val orderRepository: OrderRepository,
    private val memberRepository: MemberRepository,
    private val itemRepository: ItemRepository
) {

    // 주문
    @Transactional
    fun order(memberId: Long, itemId: Long, count: Int): Long {

        // 엔티티조회
        val findMember = memberRepository.findOne(memberId)
        val findItem = itemRepository.findOne(itemId)

        // 배송정보생성
        val delivery = Delivery(null, null, findMember.address, DeliveryStatus.READY)

        // 주문상품생성
        val orderItem = OrderItem.createOrderItem(findItem, findItem.price, count)

        // 주문생성
        val order = Order.createOrder(findMember, delivery, orderItem)

        orderRepository.save(order)

        return order.id ?: throw RuntimeException("id is null")
    }

    // 취소
    @Transactional
    fun cancel(orderId: Long) {
        // 엔티티조회
        val findOrder = orderRepository.findOne(orderId)

        // 주문취소
        findOrder.cancel()
    }

    // 검색
}
