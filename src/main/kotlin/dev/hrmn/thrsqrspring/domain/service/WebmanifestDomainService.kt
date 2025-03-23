package dev.hrmn.thrsqrspring.domain.service

import dev.hrmn.thrsqrspring.domain.model.Event
import org.springframework.stereotype.Service

@Service
class WebmanifestDomainService(private val logoDomainService: LogoDomainService) {
    companion object {
        const val DEFAULT_LOGO_URL = "/images/thrsqrlogo-250.png"
        val BUCKET = System.getenv("S3_BUCKET_NAME")
    }

    fun generateManifest(event: Event): String {
        return """
        {
          "name": "${event.title}",
          "short_name": "${event.title}",
          "icons": [
            {
              "src": "${
            if (event.logoURL!!.startsWith("https")) logoDomainService.createResizedLogoURL(
                BUCKET,
                event.code,
                144
            ) else DEFAULT_LOGO_URL
        }",
              "sizes": "144x144",
              "type": "image/png"
            },
            {
              "src": "${
            if (event.logoURL!!.startsWith("https")) logoDomainService.createResizedLogoURL(
                BUCKET,
                event.code,
                192
            ) else DEFAULT_LOGO_URL
        }",
              "sizes": "192x192",
              "type": "image/png"
            },
            {
              "src": "${
            if (event.logoURL!!.startsWith("https")) logoDomainService.createResizedLogoURL(
                BUCKET,
                event.code,
                256
            ) else DEFAULT_LOGO_URL
        }",
              "sizes": "256x256",
              "type": "image/png"
            },
            {
              "src": "${
            if (event.logoURL!!.startsWith("https")) logoDomainService.createResizedLogoURL(
                BUCKET,
                event.code,
                512
            ) else DEFAULT_LOGO_URL
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