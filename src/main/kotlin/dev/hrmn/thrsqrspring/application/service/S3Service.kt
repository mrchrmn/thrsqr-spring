package dev.hrmn.thrsqrspring.application.service

import dev.hrmn.thrsqrspring.adapter.input.web.dto.S3Response
import dev.hrmn.thrsqrspring.adapter.output.storage.S3Adapter
import dev.hrmn.thrsqrspring.application.port.input.S3Service
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class S3Service(
    private val s3Adapter: S3Adapter,
    @Value("\${s3.bucketName}")
    private val bucketName: String
) : S3Service {

    override fun getS3Response(fileType: String, eventCode: String): S3Response {
        return S3Response(
            s3request = s3Adapter.generatePresignedURL(fileType, eventCode),
            url = "https://${bucketName}.s3.amazonaws.com/logos/${eventCode}-logo",
        )

    }
}