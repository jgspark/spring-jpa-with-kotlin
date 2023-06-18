package com.example.basicjpa

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class HelloJpaApplication

fun main(args: Array<String>) {
    runApplication<HelloJpaApplication>(*args)
}
