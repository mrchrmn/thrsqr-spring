package dev.hrmn.thrsqrspring.adapter.input.web

import dev.hrmn.thrsqrspring.adapter.input.web.dto.S3Response
import dev.hrmn.thrsqrspring.application.service.S3Service
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
class S3Controller(private val s3Service: S3Service) {
    @GetMapping("/s3request")
    fun getS3Request(
        @RequestParam eventId: String,
        @RequestParam fileType: String
    ): S3Response {
        return s3Service.getS3Response(eventId, fileType)
    }
}