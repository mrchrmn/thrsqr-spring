package dev.hrmn.thrsqrspring.adapter.output.storage

import dev.hrmn.thrsqrspring.application.port.output.S3Port
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import software.amazon.awssdk.services.s3.presigner.S3Presigner
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest
import java.time.Duration

@Component
class S3Adapter(
    private val s3Presigner: S3Presigner,
    @Value("\${s3.bucketName}")
    private val bucketName: String
) : S3Port {

    override fun generatePresignedURL(fileType: String, eventCode: String): String {
        val objectRequest = PutObjectRequest.builder()
            .bucket(bucketName)
            .key("logos/${eventCode}-logo")
            .contentType(fileType)
            .build()

        val presignRequest = PutObjectPresignRequest.builder()
            .signatureDuration(Duration.ofMinutes(10))
            .putObjectRequest(objectRequest)
            .build()

        val presignedRequest = s3Presigner.presignPutObject(presignRequest)

        return presignedRequest.url().toString()
    }
}