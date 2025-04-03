package dev.hrmn.thrsqrspring.domain.service

import dev.hrmn.thrsqrspring.domain.model.Event
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class WebmanifestDomainService(
    private val logoDomainService: LogoDomainService,
    @Value("\${s3.bucketName}")
    private val bucketName: String,
    @Value("\${logo.defaultUrl}")
    private val defaultLogoUrl: String
) {
    fun generateManifest(event: Event): String {
        return """
        {
          "name": "${event.title}",
          "short_name": "${event.title}",
          "icons": [
            {
              "src": "${
            if (event.logoURL!!.startsWith("https")) logoDomainService.createResizedLogoURL(
                bucketName,
                event.code,
                144
            ) else defaultLogoUrl
        }",
              "sizes": "144x144",
              "type": "image/png"
            },
            {
              "src": "${
            if (event.logoURL!!.startsWith("https")) logoDomainService.createResizedLogoURL(
                bucketName,
                event.code,
                192
            ) else defaultLogoUrl
        }",
              "sizes": "192x192",
              "type": "image/png"
            },
            {
              "src": "${
            if (event.logoURL!!.startsWith("https")) logoDomainService.createResizedLogoURL(
                bucketName,
                event.code,
                256
            ) else defaultLogoUrl
        }",
              "sizes": "256x256",
              "type": "image/png"
            },
            {
              "src": "${
            if (event.logoURL!!.startsWith("https")) logoDomainService.createResizedLogoURL(
                bucketName,
                event.code,
                512
            ) else defaultLogoUrl
        }",
              "sizes": "512x512",
              "type": "image/png"
            }
          ],
          "theme_color": "#FFFEE4",
          "background_color": "#FFFEE4",
          "display": "standalone",
          "start_url": "/event/${event.code}",
          "scope": "/event/${event.code}",
          "description": "ThrSqr - Your RSVP tracker..."
        }
        """.trimIndent()
    }
}