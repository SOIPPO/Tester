package org.soippo.exceptions;

import org.soippo.utils.ErrorCode;

public class NotUniqueEmailException extends UserValidationException {
    public NotUniqueEmailException() {
        super(ErrorCode.EMAIL_ALREADY_IN_USE.toString());
        setErrorCode(ErrorCode.EMAIL_ALREADY_IN_USE);
    }
}
