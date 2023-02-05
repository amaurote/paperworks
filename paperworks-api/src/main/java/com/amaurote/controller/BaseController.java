package com.amaurote.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public interface BaseController {

    default <T> ResponseEntity<T> ok() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    default <T> ResponseEntity<T> ok(T payload) {
        return new ResponseEntity<>(payload, HttpStatus.OK);
    }

    default <T> ResponseEntity<T> badRequest() {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    default <T> ResponseEntity<T> badRequest(T payload) {
        return new ResponseEntity<>(payload, HttpStatus.BAD_REQUEST);
    }

    default <T> ResponseEntity<T> notFound() {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    default <T> ResponseEntity<T> notFound(T payload) {
        return new ResponseEntity<>(payload, HttpStatus.NOT_FOUND);
    }

    default <T> ResponseEntity<T> noContent() {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
