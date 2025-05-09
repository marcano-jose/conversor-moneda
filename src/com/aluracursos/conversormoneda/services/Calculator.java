package com.aluracursos.conversormoneda.services;

import com.aluracursos.conversormoneda.models.Currencies;
import com.aluracursos.conversormoneda.utils.InputHandler;
import com.aluracursos.conversormoneda.utils.UserInterface;

public class Calculator {
    private final UserInterface userInterface;
    private final InputHandler inputHandler;
    private final String apiKey;

    public Calculator(String apiKey) {
        this.userInterface = new UserInterface();
        this.inputHandler = new InputHandler();
        this.apiKey = apiKey;
    }

    public void display() {
        userInterface.displayHeaderMessage("Calculadora Cambiaría");
        userInterface.displayCurrenciesCodes();

        double amount = inputHandler.readDecimal("\nMonto a convertir: ");
        Currencies sourceCurrencies = inputHandler.readCurrency("\nMoneda origen (código): ");
        Currencies targetCurrencies = inputHandler.readCurrency("\nMoneda destino (código): ");

        // Aquí irá la lógica para realizar la conversión utilizando las monedas y el monto
        System.out.println("\nRealizando conversión de " + amount + " " + sourceCurrencies.getName() +
                " a " + targetCurrencies.getName() + "...");
        // Aquí se puede llamar a la ApiClient
    }
}
