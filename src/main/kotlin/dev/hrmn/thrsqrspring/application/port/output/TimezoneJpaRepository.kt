package dev.hrmn.thrsqrspring.application.port.output

import dev.hrmn.thrsqrspring.domain.model.Timezone
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TimezoneJpaRepository : JpaRepository<Timezone, String> {
    fun findByName(name: String): Timezone?
}