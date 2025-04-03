package dev.hrmn.thrsqrspring.application.port.output

interface S3Port {
    fun generatePresignedURL(fileType: String, eventCode: String): String
}