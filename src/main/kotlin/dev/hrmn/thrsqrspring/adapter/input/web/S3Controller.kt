package dev.hrmn.thrsqrspring.adapter.input.web

import dev.hrmn.thrsqrspring.adapter.input.web.dto.S3Response
import dev.hrmn.thrsqrspring.application.port.input.S3Controller
import dev.hrmn.thrsqrspring.application.service.S3Service
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
class S3Controller(private val s3Service: S3Service) : S3Controller {
    @GetMapping("/s3request")
    override fun getS3Request(
        @RequestParam fileType: String,
        @RequestParam eventCode: String
    ): ResponseEntity<S3Response> {
        return ResponseEntity.ok(s3Service.getS3Response(fileType, eventCode))
    }
}