package com.aluracursos.conversormoneda.services;

import com.aluracursos.conversormoneda.models.Currencies;
import com.aluracursos.conversormoneda.models.RatesInformation;
import com.aluracursos.conversormoneda.utils.ApiClientException;
import com.aluracursos.conversormoneda.utils.InputHandler;
import com.aluracursos.conversormoneda.utils.UserInterface;
import com.google.gson.Gson;

public class Calculator {
    private final UserInterface userInterface;
    private final InputHandler inputHandler;
    private final String apiKey;

    public Calculator(String apiKey) {
        this.userInterface = new UserInterface();
        this.inputHandler = new InputHandler();
        this.apiKey = apiKey;
    }

    public void showExchangeCalculator() {
        userInterface.displayHeaderMessage("Calculadora Cambiaría");
        userInterface.displayCurrenciesCodes();

        double amount = inputHandler.readDecimal("\nMonto a convertir: ");
        Currencies sourceCurrencies = inputHandler.readCurrency("\nMoneda origen (código): ");
        Currencies targetCurrencies = inputHandler.readCurrency("\nMoneda destino (código): ");

        ApiClient apiClient = new ApiClient(apiKey);

        try {
            String exchangeRateJson = apiClient.lookupInfo(sourceCurrencies.getCode(), targetCurrencies.getCode() );

            Gson gson = new Gson();
            RatesInformation response = gson.fromJson(exchangeRateJson, RatesInformation.class);

            Double conversion = amount * response.getConversion_rate();

            System.out.printf("\nConversión: %,.2f (%s) => %,.2f (%s)%n",
                    amount,
                    sourceCurrencies.getCode(),
                    conversion,
                    targetCurrencies.getCode());

        } catch (ApiClientException e) {
            System.err.println(e.getMessage());

            if (e.getCause() != null) {
                System.err.println(e.getCause().getMessage());
            }

        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}
