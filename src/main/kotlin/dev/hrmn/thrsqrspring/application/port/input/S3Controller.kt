package dev.hrmn.thrsqrspring.application.port.input

import dev.hrmn.thrsqrspring.adapter.input.web.dto.S3Response
import org.springframework.web.bind.annotation.RequestParam

interface S3Controller {
    fun getS3Request(@RequestParam fileType: String, @RequestParam eventCode: String): S3Response
}