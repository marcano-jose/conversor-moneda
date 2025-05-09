package com.aluracursos.conversormoneda.utils;

import com.aluracursos.conversormoneda.models.Currencies;

public class UserInterface {

    public void displayHeaderMessage(String prompt) {
        System.out.printf("""
                -----------------------
                 %s
                -----------------------
                \n""", prompt);
    }

    public void displayCurrenciesCodes() {
        System.out.println("Info. código de monedas:");
        for (Currencies moneda : Currencies.values()) {
            System.out.println("- " + moneda.name() + " (" + moneda.getName() + ")");
        }
        System.out.println();
    }
}
