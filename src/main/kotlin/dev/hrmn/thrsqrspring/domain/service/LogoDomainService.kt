package dev.hrmn.thrsqrspring.domain.service

import com.fasterxml.jackson.databind.ObjectMapper
import dev.hrmn.thrsqrspring.domain.model.Event
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class LogoDomainService(
    @Value("\${s3.bucketName}")
    private val bucketName: String,
    @Value("\${logo.defaultUrl}")
    private val defaultLogoUrl: String
) {
    fun getIcons(event: Event): Map<Int, String> {
        val logoURL = getLogoUrl(event)

        val sizes = listOf(144, 192, 256, 512)
        val icons = sizes.associateWith { defaultLogoUrl }.toMutableMap()

        if (logoURL.startsWith("https")) {
            icons.keys.forEach { key ->
                icons[key] = createResizedLogoURL(bucketName, event.code, key)
            }
        }

        return icons
    }

    fun getLogoUrl(event: Event): String {
        val logoURL = event.logoURL

        return if (!logoURL.isNullOrBlank() && logoURL.startsWith("https")) {
            createResizedLogoURL(bucketName, event.code, 500)
        } else {
            defaultLogoUrl
        }
    }

    fun createResizedLogoURL(bucket: String, code: String, size: Int): String {
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