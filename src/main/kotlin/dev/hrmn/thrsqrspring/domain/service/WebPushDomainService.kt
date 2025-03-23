package dev.hrmn.thrsqrspring.domain.service

import org.springframework.context.MessageSource
import org.springframework.stereotype.Service
import java.util.*

@Service
class WebPushDomainService(
    private val messageSource: MessageSource
) {
    fun buildNotificationBody(
        language: String,
        going: Int,
        notGoing: Int,
        username: String?,
        userResponseIsThere: Boolean?,
        comment: String?
    ): String {
        val locale = Locale(language)
        val thereText = messageSource.getMessage("there", null, locale)
        val squareText = messageSource.getMessage("square", null, locale)
        val willBeText = messageSource.getMessage("willBe", null, locale)

        return buildString {
            append("$thereText: $going | $squareText: $notGoing\n\n")
            if (username != null) {
                val userResponse = if (userResponseIsThere == true) thereText.lowercase() else squareText.lowercase()
                append("$username $willBeText $userResponse")
            }
            append(if (!comment.isNullOrBlank()) ": $comment" else ".")
        }
    }
}
