package com.aluracursos.conversormoneda.utils;

import com.aluracursos.conversormoneda.models.Currencies;

import java.util.Scanner;

public class InputHandler {
    private final Scanner scanner;

    public InputHandler() {
        this.scanner = new Scanner(System.in);
    }

    public double readDecimal(String prompt) {
        boolean isValidInput = false;
        Double value = null;

        while (!isValidInput) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            try {
                value = Double.parseDouble(input);
                isValidInput = true;
            } catch (NumberFormatException e) {
                System.err.println("Error: Ingrese un valor numérico válido.");
            }
        }
        return value;
    }

    public Currencies readCurrency(String prompt) {
        boolean isValidInput = false;
        Currencies currency = null;

        while (!isValidInput) {
            System.out.print(prompt);
            String input = scanner.nextLine().toUpperCase();
            try {
                currency = Currencies.valueOf(input);
                System.out.println("Moneda seleccionada: " + currency.getName());
                isValidInput = true;
            } catch (IllegalArgumentException e) {
                System.err.println("Error: Ingrese un código monetario válido.");
            }
        }
        return currency;
    }

    public void closeScanner() {
        scanner.close();
    }
}
