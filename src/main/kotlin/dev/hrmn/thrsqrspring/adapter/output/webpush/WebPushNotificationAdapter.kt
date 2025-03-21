package dev.hrmn.thrsqrspring.adapter.output.webpush

import com.fasterxml.jackson.databind.ObjectMapper
import dev.hrmn.thrsqrspring.application.port.output.WebPushNotificationPort
import dev.hrmn.thrsqrspring.domain.model.Event
import dev.hrmn.thrsqrspring.domain.model.Participant
import dev.hrmn.thrsqrspring.domain.model.Response
import dev.hrmn.thrsqrspring.domain.model.Subscription
import nl.martijndwars.webpush.Notification
import nl.martijndwars.webpush.PushService
import org.bouncycastle.jce.provider.BouncyCastleProvider
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.security.Security
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@Component
class WebPushNotificationAdapter(
    private val objectMapper: ObjectMapper
) : WebPushNotificationPort {

    private val logger = LoggerFactory.getLogger(javaClass)
    private val executor: ExecutorService = Executors.newFixedThreadPool(4)
    private val pushService by lazy { initPushService() }

    @Value("\${vapid.public.key}")
    private lateinit var publicKey: String

    @Value("\${vapid.private.key}")
    private lateinit var privateKey: String

    @Value("\${vapid.subject}")
    private lateinit var subject: String

    init {
        if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
            Security.addProvider(BouncyCastleProvider())
        }
    }

    private fun initPushService(): PushService {
        return PushService(publicKey, privateKey, subject)
    }

    override fun sendResponseUpdateNotification(
        event: Event,
        participant: Participant?,
        response: Response?,
        subscriptions: List<Subscription>,
        goingCount: Int,
        notGoingCount: Int
    ) {
        if (subscriptions.isEmpty()) {
            return
        }

        executor.submit {
            try {
                subscriptions.forEach { subscription ->
                    val payload = createPayload(
                        event = event,
                        participant = participant,
                        response = response,
                        going = goingCount,
                        notGoing = notGoingCount,
                        language = subscription.language ?: "en"
                    )
                    sendNotification(subscription, payload)
                }
            } catch (e: Exception) {
                logger.error("Failed to send push notification", e)
            }
        }
    }

    private fun createPayload(
        event: Event,
        participant: Participant?,
        response: Response?,
        going: Int,
        notGoing: Int,
        language: String
    ): String {
        val notificationData = mapOf(
            "title" to event.title,
            "eventId" to event.code,
            "iconURL" to "/images/icon-192.png",
            "going" to going,
            "notGoing" to notGoing,
            "language" to language,
            "username" to participant?.username,
            "there" to response?.there,
            "comment" to response?.comment
        )

        return objectMapper.writeValueAsString(notificationData)
    }

    private fun sendNotification(subscription: Subscription, payload: String) {
        try {
            val notification = Notification(
                subscription.endpoint,
                subscription.p256dh,
                subscription.auth,
                payload.toByteArray()
            )

            pushService.send(notification)
        } catch (e: Exception) {
            logger.error("Failed to send notification to ${subscription.endpoint}", e)
        }
    }
}