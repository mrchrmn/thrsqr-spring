package dev.hrmn.thrsqrspring.application.util

import dev.hrmn.thrsqrspring.adapter.output.persistence.EventJpaAdapter
import dev.hrmn.thrsqrspring.adapter.output.persistence.dto.ResponseDto
import dev.hrmn.thrsqrspring.domain.model.Event
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.time.LocalTime

class EventUtilsTest {

    private val eventJpaAdapter = mockk<EventJpaAdapter>()

    @Test
    fun `generateEventCode should generate unique code`() {
        every { eventJpaAdapter.findByCode(any()) } returns null

        val code = EventUtils.generateEventCode(eventJpaAdapter)

        assertEquals(4, code.length)
        assertTrue(code.all { it.isLetterOrDigit() })
    }

    @Test
    fun `getIcons should return default icons`() {
        val event = Event(
            code = "test",
            title = "Test Event",
            dayOfWeek = 1,
            eventTime = LocalTime.now(),
            timeZone = "UTC",
            info = "",
            logoURL = null
        )
        val icons = EventUtils.getIcons(event)

        assertEquals(4, icons.size)
        assertTrue(icons.values.all { it == EventUtils.DEFAULT_LOGO_URL })
    }

    @Test
    fun `getLogoUrl should return default logo URL if logoURL is null or blank`() {
        val event = Event(
            code = "test",
            title = "Test Event",
            dayOfWeek = 1,
            eventTime = LocalTime.now(),
            timeZone = "UTC",
            info = "",
            logoURL = null
        )
        val logoURL = EventUtils.getLogoUrl(event)

        assertEquals(EventUtils.DEFAULT_LOGO_URL, logoURL)
    }

    @Test
    fun `capitaliseUsernames should capitalise usernames`() {
        val responses = listOf(
            ResponseDto(id = 1, username = "john", comment = "", there = true),
            ResponseDto(id = 2, username = "doe", comment = "", there = true)
        )
        EventUtils.capitaliseUsernames(responses)

        assertEquals("John", responses[0].username)
        assertEquals("Doe", responses[1].username)
    }
}