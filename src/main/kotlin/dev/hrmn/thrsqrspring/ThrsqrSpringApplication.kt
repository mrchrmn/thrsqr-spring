package dev.hrmn.thrsqrspring

import io.github.cdimascio.dotenv.dotenv
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ThrsqrSpringApplication

fun main(args: Array<String>) {
    dotenv()
    runApplication<ThrsqrSpringApplication>(*args)
}
