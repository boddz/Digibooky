package com.teamawsome.domain.member;

public class NotUniqueException extends RuntimeException {
    public NotUniqueException(String message) {
        super(message);
    }
}
