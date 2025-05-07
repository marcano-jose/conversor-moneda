package com.aluracursos.conversormoneda.services;

import com.aluracursos.conversormoneda.models.ExchangeRateInformation;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class ExchangeRates {
    private final List<String> preferredCurrencies;

    public ExchangeRates(List<String> preferredCurrencies) {
        this.preferredCurrencies = preferredCurrencies;
    }

    public void showExchangeInfo(ExchangeRateInformation response) {
        Date fecha = new Date(response.getTime_last_update_unix() * 1000);
        System.out.println("Ultima actualización: " + fecha);
        System.out.println("Base: Dólar estadounidense (" + response.getBase_code() + ")");
        System.out.println("Tasas de las monedas preferidas:");
        showFilteredRates(response.getConversion_rates());
    }

    private void showFilteredRates(Map<String, Double> rates) {
        for (String currency : preferredCurrencies) {
            if (rates.containsKey(currency)) {
                System.out.println("- " + currencyName(currency) + " (" + currency + "): " + rates.get(currency));
            } else {
                System.out.println("- (" + currencyName(currency) + "): No disponible en esta consulta.");
            }
        }
    }

    private String currencyName(String currencyCode) {
        return switch (currencyCode) {
            case "ARS" -> "Peso argentino";
            case "BOB" -> "Boliviano boliviano";
            case "BRL" -> "Real brasileño";
            case "CLP" -> "Peso chileno";
            case "COP" -> "Peso colombiano";
            default -> currencyCode; // Si no se encuentra, devuelve el código
        };
    }
}
