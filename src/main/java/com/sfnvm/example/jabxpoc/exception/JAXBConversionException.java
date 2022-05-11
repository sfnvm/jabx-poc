package com.sfnvm.example.jabxpoc.exception;

public class JAXBConversionException extends RuntimeException {
    public JAXBConversionException(String message) {
        super(message);
    }

    public JAXBConversionException(Throwable cause) {
        super(cause);
    }
}
