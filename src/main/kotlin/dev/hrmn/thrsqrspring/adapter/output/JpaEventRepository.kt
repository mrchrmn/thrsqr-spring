package dev.hrmn.thrsqrspring.adapter.output

import dev.hrmn.thrsqrspring.domain.model.Event
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface JpaEventRepository : JpaRepository<Event, Long>