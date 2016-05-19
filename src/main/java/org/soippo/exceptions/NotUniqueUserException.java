package org.soippo.exceptions;

import org.soippo.utils.ErrorCode;

public class NotUniqueUserException extends UserValidationException {
    public NotUniqueUserException(String message) {
        super(message);
        setErrorCode(ErrorCode.USER_ALREADY_EXISTS);
    }
}
