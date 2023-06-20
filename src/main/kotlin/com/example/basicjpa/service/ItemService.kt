package com.example.basicjpa.service

import com.example.basicjpa.domain.Item
import com.example.basicjpa.repository.ItemRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class ItemService(
    private val itemRepository: ItemRepository
) {

    @Transactional
    fun saveItem(item: Item) = itemRepository.save(item)

    fun findItems(): List<Item> = itemRepository.findAll()

    fun findOne(id: Long): Item = itemRepository.findOne(id)
}
