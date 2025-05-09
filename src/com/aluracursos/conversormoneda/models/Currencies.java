package com.aluracursos.conversormoneda.models;

public enum Currencies {
    ARS("Peso argentino"),
    BOB("Boliviano boliviano"),
    BRL("Real brasileño"),
    CLP("Peso chileno"),
    COP("Peso colombiano"),
    USD("Dólar estadounidense");

    private final String name;

    Currencies(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return this.name(); // El nombre de la constante enum (ARS, BOB, etc.) es el código.
    }

    @Override
    public String toString() {
        return name;
    }
}
