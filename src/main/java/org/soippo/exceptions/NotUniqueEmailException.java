package org.soippo.exceptions;

public class NotUniqueEmailException extends Exception {
    public NotUniqueEmailException(String message) {
        super(message);
    }
}
