package org.soippo.exceptions;

import org.soippo.utils.ErrorCode;

public class NotUniqueEmailException extends UserValidationException {
    public NotUniqueEmailException(String message) {
        super(message);
        setErrorCode(ErrorCode.EMAIL_ALREADY_IN_USE);
    }
}
