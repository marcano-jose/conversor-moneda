package com.aluracursos.conversormoneda.services;

import com.aluracursos.conversormoneda.utils.ApiClient;
import com.aluracursos.conversormoneda.utils.ApiClientException;
import com.aluracursos.conversormoneda.utils.InputHandler;
import com.aluracursos.conversormoneda.utils.UserInterface;
import com.google.gson.Gson;
import com.aluracursos.conversormoneda.models.RatesInformation;

public abstract class BaseRateService {
    protected final UserInterface userInterface;
    protected final InputHandler inputHandler;
    protected final String apiKey;
    protected final ApiClient apiClient;
    protected final Gson gson;

    public BaseRateService(String apiKey) {
        this.userInterface = new UserInterface();
        this.inputHandler = new InputHandler();
        this.apiKey = apiKey;
        this.apiClient = new ApiClient(apiKey);
        this.gson = new Gson();
    }

    protected RatesInformation fetchRatesInformation(String code) throws ApiClientException, InterruptedException {
        String jsonResponse = apiClient.lookupInfo(code);
        return gson.fromJson(jsonResponse, RatesInformation.class);
    }

    protected RatesInformation fetchRatesInformation(String sourceCode, String targetCode) throws ApiClientException, InterruptedException {
        String jsonResponse = apiClient.lookupInfo(sourceCode, targetCode);
        return gson.fromJson(jsonResponse, RatesInformation.class);
    }

    protected void handleApiException(ApiClientException e) {
        System.err.println(e.getMessage());
        if (e.getCause() != null) {
            System.err.println(e.getCause().getMessage());
        }
    }

    protected void handleInterruptedException(InterruptedException e) {
        System.err.println(e.getMessage());
        Thread.currentThread().interrupt();
    }
}
