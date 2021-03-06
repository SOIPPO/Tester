package org.soippo.exceptions;

import org.soippo.utils.ErrorCode;

public class UserValidationException extends  Exception {
    private ErrorCode errorCode;

    UserValidationException(String message) {
        super(message);
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
