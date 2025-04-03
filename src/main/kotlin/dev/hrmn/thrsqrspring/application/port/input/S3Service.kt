package dev.hrmn.thrsqrspring.application.port.input

import dev.hrmn.thrsqrspring.adapter.input.web.dto.S3Response

interface S3Service {
    fun getS3Response(fileType: String, eventCode: String): S3Response
}