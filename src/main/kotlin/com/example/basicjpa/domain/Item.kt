package com.example.basicjpa.domain

import jakarta.persistence.Column
import jakarta.persistence.DiscriminatorColumn
import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Inheritance
import jakarta.persistence.InheritanceType
import jakarta.persistence.ManyToMany

@Entity
// 단일 테이블 전략으로 설계
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
// 객체별 타입 설정
@DiscriminatorColumn(name = "dtype")
abstract class Item(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    open val id: Long,
    open val name: String,
    open val price: Int,
    open val stockQuantity: Int,
    @ManyToMany(mappedBy = "items")
    open val categories: List<Category> = listOf()
)

@Entity
@DiscriminatorValue("A")
data class Album(
    override val id: Long,
    override val name: String,
    override val price: Int,
    override val stockQuantity: Int,
    override val categories: List<Category>
) : Item(id, name, price, stockQuantity, categories)

@Entity
@DiscriminatorValue("B")
data class Book(
    override val id: Long,
    override val name: String,
    override val price: Int,
    override val stockQuantity: Int,
    override val categories: List<Category>
) : Item(id, name, price, stockQuantity, categories)

@Entity
@DiscriminatorValue("M")
data class Movie(
    override val id: Long,
    override val name: String,
    override val price: Int,
    override val stockQuantity: Int,
    override val categories: List<Category>
) : Item(id, name, price, stockQuantity, categories)
