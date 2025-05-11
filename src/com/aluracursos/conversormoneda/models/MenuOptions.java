package com.aluracursos.conversormoneda.models;

public class MenuOptions<T> {
    private final T key;
    private final String description;
    private final Runnable action;

    public MenuOptions(T key, String description, Runnable action) {
        this.key = key;
        this.description = description;
        this.action = action;
    }

    public T getKey() {
        return key;
    }

    public String getDescription() {
        return description;
    }

    public Runnable getAction() {
        return action;
    }

    @Override
    public String toString() {
        return key + ". " + description;
    }
}