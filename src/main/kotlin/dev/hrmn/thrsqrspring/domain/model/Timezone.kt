package dev.hrmn.thrsqrspring.domain.model

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.Immutable

@Entity
@Immutable
@Table(name = "pg_timezone_names", schema = "pg_catalog")
data class Timezone(
    @Id
    val name: String,
    val abbrev: String,
    val utcOffset: String,
)
