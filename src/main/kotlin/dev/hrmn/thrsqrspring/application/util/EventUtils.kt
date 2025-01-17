package dev.hrmn.thrsqrspring.application.util

import com.fasterxml.jackson.databind.ObjectMapper
import dev.hrmn.thrsqrspring.adapter.output.persistence.EventRepository
import dev.hrmn.thrsqrspring.domain.dto.ResponseDto
import dev.hrmn.thrsqrspring.domain.model.Event
import org.springframework.util.StringUtils.capitalize

object EventUtils {
    const val DEFAULT_LOGO_URL = "/images/thrsqrlogo-250.png"
    val BUCKET = System.getenv("S3_BUCKET_NAME")

    fun generateEventCode(eventRepository: EventRepository): String {
        val alphabet = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
        return generateSequence {
            buildString {
                repeat(4) {
                    append(alphabet.random())
                }
            }
        }.first { eventRepository.findByCode(it) == null }
    }

    fun getIcons(event: Event): Map<Int, String> {
        val logoURL = getLogoUrl(event)

        val sizes = listOf(144,192,256,512)
        val icons = sizes.associateWith { DEFAULT_LOGO_URL }.toMutableMap()

        if (logoURL.startsWith("https")) {
            icons.keys.forEach { key ->
                icons[key] = getResizedLogoURL(BUCKET, event.code, key)
            }
        }

        return icons
    }

    fun getLogoUrl(event: Event): String {
        val logoURL = event.logoURL

        return if (!logoURL.isNullOrBlank() && logoURL.startsWith("https")) {
            getResizedLogoURL(BUCKET, event.code, 500)
        } else {
            DEFAULT_LOGO_URL
        }
    }

    fun capitaliseUsernames(responses: List<ResponseDto>) {
        responses.forEach { it.username = capitalize(it.username) }
    }

    private fun getResizedLogoURL(bucket: String, code: String, size: Int): String {
        val reqBody = mapOf(
            "bucket" to bucket,
            "key" to "logos/$code-logo",
            "edits" to mapOf(
                "resize" to mapOf(
                    "width" to size,
                    "height" to size,
                    "fit" to "cover"
                )
            )
        )

        val json = ObjectMapper().writeValueAsString(reqBody)
        val base64 = java.util.Base64.getEncoder().encodeToString(json.toByteArray())

        return "https://d2x3ofzbg5tm3i.cloudfront.net/$base64"
    }
}