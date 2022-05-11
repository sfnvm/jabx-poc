package com.sfnvm.example.jabxpoc.exception;

import com.sfnvm.example.jabxpoc.xml.enums.ErrorCode;
import lombok.Getter;

public class JAXBValidationException extends Exception {
    @Getter
    private ErrorCode errorCode;

    public JAXBValidationException(String message) {
        super(message);
    }

    public JAXBValidationException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
