package com.example.basicjpa.service

import com.example.basicjpa.domain.Address
import com.example.basicjpa.domain.Album
import com.example.basicjpa.domain.Book
import com.example.basicjpa.domain.Item
import com.example.basicjpa.domain.Member
import com.example.basicjpa.domain.OrderStatus
import com.example.basicjpa.exception.NotEnoughStockException
import com.example.basicjpa.repository.OrderRepository
import jakarta.persistence.EntityManager
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.transaction.annotation.Transactional
import java.lang.management.LockInfo

@ExtendWith(SpringExtension::class)
@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired
    lateinit var em: EntityManager

    @Autowired
    lateinit var orderService: OrderService

    @Autowired
    lateinit var orderRepository: OrderRepository

    @Test
    @Throws(Exception::class)
    fun `상품주문`() {

        //given

        val member = member()

        val item = book()

        val orderCount = 2

        //when
        // todo : check item id why is null .. ?
        val orderId = orderService.order(member.id!!, item.id ?: 1, orderCount)

        //then

        val findOne = orderRepository.findOne(orderId)

        assertEquals(OrderStatus.ORDER, findOne.orderStatus, "상품주문시 status 값은 ORDER 이다.")
        assertEquals(1, findOne.orderItems.size, "주문한 상품의 수가 정확해야 한다.")
        assertEquals(10000 * orderCount, findOne.getTotalPrice(), "상품주문시 총 가격은 price * 주문수량이다.")
        assertEquals(8, item.stockQuantity, "주문한 수량의 재고가 $orderCount 만큼 감소해야한다.")
    }

    private fun book(): Book {
        val item = Book(null, "JPA 책", 10000, 10)

        em.persist(item)
        return item
    }

    private fun member(): Member {
        val member = Member("kim1", Address("서울시", "강가", "123-12"))

        em.persist(member)
        return member
    }

    @Test
    @Throws(Exception::class)
    fun `상품 주문 초과 수량`() {

        //given

        val member = member()
        val book = book()

        val orderCount = 11

        //when

        val e = org.junit.jupiter.api.assertThrows<NotEnoughStockException> {
            orderService.order(member.id!!, book.id ?: 1, orderCount)
        }

        //then
        assertEquals(e.message, "need more stock")
    }

    @Test
    @Throws(Exception::class)
    fun `상품취소`() {

        //given
        val member = member()
        val book = book()
        val orderCount = 2
        val orderId = orderService.order(member.id!!, book.id ?: 1, orderCount)

        //when
        orderService.cancel(orderId)

        //then
        val findOne = orderRepository.findOne(orderId)

        assertEquals(OrderStatus.CANCEL, findOne.orderStatus, "주문취소 상태는 CANCEL 이다.")
        assertEquals(10, book.stockQuantity, "주문이 취소되면 취소 갯수만큼 증가해야 한다.")
    }

    @Test
    @Throws(Exception::class)
    fun `상품주문_재고수량추가`() {

        //given

        //when

        //then
    }
}
