package com.aluracursos.conversormoneda.services;

public class ExchangeRateClientException extends Exception {
    public ExchangeRateClientException(String message) {
        super(message);
    }

    public ExchangeRateClientException(String message, Throwable cause) {
        super(message, cause);
    }
}