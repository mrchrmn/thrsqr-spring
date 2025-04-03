package dev.hrmn.thrsqrspring

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession

@SpringBootApplication
@EnableJdbcHttpSession
class ThrsqrSpringApplication

fun main(args: Array<String>) {
    runApplication<ThrsqrSpringApplication>(*args)
}
