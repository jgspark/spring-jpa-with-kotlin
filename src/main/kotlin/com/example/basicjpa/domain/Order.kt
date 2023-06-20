package com.example.basicjpa.domain

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "orders")
data class Order(
    @Id
    @Column(name = "order_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    var member: Member,
    // cascade 의 경우 라이플사이클이 중요
    // 혹은 다른 곳에서 참조하지 않는다면 사용 가능
    // 연관관계에서 있어서 생각하기
    @OneToMany(mappedBy = "order", cascade = [CascadeType.ALL])
    val orderItems: List<OrderItem> = mutableListOf(),
    // fk 의 경우 order 와 delivery 를 둘다 두어도 된다.
    // 하지만 시스템의 설계에 있어서 많은 접근을 하는 곳이 좋다.
    // 즉, 주인이됨 (@JoinColumn)
    @OneToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "delivery_id")
    var delivery: Delivery,
    val orderDate: LocalDateTime,
    @Enumerated(EnumType.STRING)
    var orderStatus: OrderStatus
) {

    fun addMember(member: Member) {
        this.member = member
        (member.orders as MutableList).add(this)
    }

    fun addOrderItems(orderItem: OrderItem) {
        (orderItems as MutableList).add(orderItem)
        orderItem.order = this
    }

    fun addDelivery(delivery: Delivery) {
        this.delivery = delivery
        delivery.order = this
    }

    fun cancel() {

        check(this.delivery.deliveryStatus != DeliveryStatus.COMP) {
            "이미배송이 완료되서 취소가 불가합니다"
        }

        orderStatus = OrderStatus.CANCEL

        orderItems.forEach {
            it.cancel()
        }

    }

    fun getTotalPrice(): Int = orderItems.sumOf { it.getTotalPrice() }

    companion object {

        // 생성 메소드
        // 가변인자추가
        fun createOrder(member: Member, delivery: Delivery, vararg orderItems: OrderItem): Order {

            val order = Order(
                id = null,
                member = member,
                delivery = delivery,
                orderDate = LocalDateTime.now(),
                orderStatus = OrderStatus.ORDER
            )

            orderItems.forEach {
                (order.orderItems as MutableList).add(it)
            }

            return order
        }
    }
}

enum class OrderStatus {
    ORDER, CANCEL
}
