package com.aluracursos.conversormoneda.services;

import com.aluracursos.conversormoneda.models.Currencies;
import com.aluracursos.conversormoneda.models.CurrenciesPair;
import com.aluracursos.conversormoneda.models.RatesInformation;
import com.aluracursos.conversormoneda.utils.ApiClientException;

public class Calculator extends BaseRateService {
    public Calculator(String APIKEY) {
        super(APIKEY);
    }

    public void showExchangeCalculator() {
        userInterface.displayHeaderMessage("Calculadora Cambiaría");
        userInterface.displayCurrenciesCodes();

        double amount = inputHandler.readDecimal("Monto a convertir: ");
        Currencies sourceCurrencies = inputHandler.readCurrency("\nMoneda origen (código): ");
        Currencies targetCurrencies = inputHandler.readCurrency("\nMoneda destino (código): ");

        try {
            RatesInformation response = fetchRatesInformation(sourceCurrencies.getCode(), targetCurrencies.getCode());
            CurrenciesPair currenciesPair = new CurrenciesPair(sourceCurrencies, targetCurrencies);
            showConversionResult(response, amount, currenciesPair);

        } catch (ApiClientException e) {
            handleApiException(e);
        } catch (InterruptedException e) {
            handleInterruptedException(e);
        }
    }

    private void showConversionResult(RatesInformation response, Double amount, CurrenciesPair currenciesPair) {
        Double conversion = amount * response.getConversion_rate();

        System.out.printf("\nConversión: %,.2f (%s) => %,.2f (%s)%n",
                amount,
                currenciesPair.source().getCode(),
                conversion,
                currenciesPair.target().getCode());
    }
}
