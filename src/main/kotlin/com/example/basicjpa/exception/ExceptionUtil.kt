package com.example.basicjpa.exception


class NotEnoughStockException(override val message: String?) : RuntimeException(message) {

}
