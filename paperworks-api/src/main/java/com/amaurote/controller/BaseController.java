package com.amaurote.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public interface BaseController {

    default <T> ResponseEntity<T> response(HttpStatus status) {
        return new ResponseEntity<>(status);
    }

    default <T> ResponseEntity<T> response(HttpStatus status, T payload) {
        return new ResponseEntity<>(payload, status);
    }

    default ResponseEntity<Void> ok() {
        return response(HttpStatus.OK);
    }

    default <T> ResponseEntity<T> ok(T payload) {
        return response(HttpStatus.OK, payload);
    }

    default <T> ResponseEntity<T> badRequest() {
        return response(HttpStatus.BAD_REQUEST);
    }

    default <T> ResponseEntity<T> badRequest(T payload) {
        return response(HttpStatus.BAD_REQUEST, payload);
    }

}
