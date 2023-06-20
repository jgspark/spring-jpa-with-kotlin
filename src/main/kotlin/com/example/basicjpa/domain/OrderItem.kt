package com.example.basicjpa.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

@Entity
data class OrderItem(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    val id: Long?,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    val item: Item,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    var order: Order?,
    // 주문 가격
    val orderPrice: Int,
    // 주문 수량
    val count: Int
) {


    /**
     * 재고 수량을 원복
     */
    fun cancel() {
        item.addStock(count)
    }

    // 조회 메소드
    fun getTotalPrice(): Int = orderPrice * count

    companion object {

        // 생성 메소드
        fun createOrderItem(item: Item, orderPrice: Int, count: Int): OrderItem {
            val orderItem = OrderItem(id = null, item = item, order = null, orderPrice = orderPrice, count = count)
            item.removeStock(quantity = count)
            return orderItem
        }
    }

}
