package com.amaurote.dto

import com.fasterxml.jackson.annotation.JsonInclude
import java.time.Instant

@JsonInclude(JsonInclude.Include.NON_EMPTY)
data class ReviewDTO(
    val text: String?,
    val reviewerUsername: String?,
    val dateCreated: Instant?,
    val dateUpdated: Instant?
)