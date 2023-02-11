package com.amaurote.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

open class BaseControllerKT {

    open fun <T> ok(): ResponseEntity<T> = ResponseEntity(HttpStatus.OK)

    open fun <T> ok(payload: T): ResponseEntity<T> = ResponseEntity(payload, HttpStatus.OK)

    open fun <T> badRequest(): ResponseEntity<T> = ResponseEntity(HttpStatus.BAD_REQUEST)

    open fun <T> badRequest(payload: T): ResponseEntity<T> = ResponseEntity(payload, HttpStatus.BAD_REQUEST)

    open fun <T> notFound(): ResponseEntity<T> = ResponseEntity(HttpStatus.NOT_FOUND)

    open fun <T> notFound(payload: T): ResponseEntity<T> = ResponseEntity(payload, HttpStatus.NOT_FOUND)

    open fun <T> noContent(): ResponseEntity<T> = ResponseEntity(HttpStatus.NO_CONTENT)

}
