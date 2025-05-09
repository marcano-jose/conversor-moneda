package com.aluracursos.conversormoneda.services;

import com.aluracursos.conversormoneda.models.Currencies;
import com.aluracursos.conversormoneda.models.RatesInformation;
import com.aluracursos.conversormoneda.utils.ApiClientException;
import com.aluracursos.conversormoneda.utils.InputHandler;
import com.aluracursos.conversormoneda.utils.UserInterface;
import com.google.gson.Gson;

import java.util.Date;
import java.util.Map;

public class Rates {
    private final UserInterface userInterface;
    private final InputHandler inputHandler;
    private final String apiKey;

    public Rates(String apiKey) {
        this.userInterface = new UserInterface();
        this.inputHandler = new InputHandler();
        this.apiKey = apiKey;
    }

    public void showExchangeRates() {
        userInterface.displayHeaderMessage("  Tasas  Cambiarías");
        userInterface.displayCurrenciesCodes();

        Currencies baseCurrency = inputHandler.readCurrency("\nMoneda base (código): ");

        ApiClient apiClient = new ApiClient(apiKey);

        try {
            String exchangeRateJson = apiClient.lookupInfo(baseCurrency.getCode());

            Gson gson = new Gson();
            RatesInformation response = gson.fromJson(exchangeRateJson, RatesInformation.class);

            showGeneralInformation(response);

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

    public void showGeneralInformation(RatesInformation response) {
        Date lastUpdate = new Date(response.getTime_last_update_unix() * 1000);
        System.out.println("\nUltima actualización: " + lastUpdate);
        System.out.println("Base: (" + response.getBase_code() + ")");
        showPreferredRates(response.getConversion_rates());
    }

    private void showPreferredRates(Map<String, Double> rates) {
        System.out.println("\nTasas de las monedas preferidas:");
        for (Currencies currency : Currencies.values()) {
            if (rates.containsKey(currency.getCode())) {
                System.out.printf("- %s (%s): %,.2f%n",
                        currency.getName(),
                        currency.getCode(),
                        rates.get(currency.getCode()));
            } else {
                System.out.println("- (" + currency.getName() + "): No disponible en esta consulta.");
            }
        }
    }
}
