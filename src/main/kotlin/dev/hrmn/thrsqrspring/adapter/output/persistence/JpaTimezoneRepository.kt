package dev.hrmn.thrsqrspring.adapter.output.persistence

import dev.hrmn.thrsqrspring.domain.model.Timezone
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface JpaTimezoneRepository : JpaRepository<Timezone, String>{
    fun findByName(name: String): Timezone?
}