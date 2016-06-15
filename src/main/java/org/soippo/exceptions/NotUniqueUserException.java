package org.soippo.exceptions;

import org.soippo.utils.ErrorCode;

public class NotUniqueUserException extends UserValidationException {
    public NotUniqueUserException() {
        super(ErrorCode.USER_ALREADY_EXISTS.toString());
        setErrorCode(ErrorCode.USER_ALREADY_EXISTS);
    }
}
