package com.aluracursos.conversormoneda.services;

import com.aluracursos.conversormoneda.models.Currencies;
import com.aluracursos.conversormoneda.models.RatesInformation;
import com.aluracursos.conversormoneda.utils.ApiClientException;

import java.util.Date;
import java.util.Map;

public class Rates extends BaseRateService {
    public Rates(String APIKEY) {
        super(APIKEY);
    }

    public void showExchangeRates() {
        userInterface.displayHeaderMessage("  Tasas  Cambiarías");
        userInterface.displayCurrenciesCodes();

        Currencies baseCurrency = inputHandler.readCurrency("Moneda base (código): ");

        try {
            RatesInformation response = fetchRatesInformation(baseCurrency.getCode());
            showGeneralInformation(response);

        } catch (ApiClientException e) {
            handleApiException(e);
        } catch (InterruptedException e) {
            handleInterruptedException(e);
        }
    }

    private void showGeneralInformation(RatesInformation response) {
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
