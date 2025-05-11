package com.aluracursos.conversormoneda.utils;

import com.aluracursos.conversormoneda.models.MenuOptions;

import java.util.LinkedHashMap;
import java.util.Map;

public class MenuBuilder<T> {
    private final StringBuilder menuLines = new StringBuilder();
    private final String header;
    private final String description;
    private final Map<T, MenuOptions<T>> options = new LinkedHashMap<>();

    public MenuBuilder(String header, String description) {
        this.header = header;
        this.description = description;
    }

    public void addOption(T key, String description, Runnable action) {
        options.put(key, new MenuOptions<>(key, description, action));
    }

    public void display() {
        menuLines.setLength(0);
        menuLines.append("\n--------------------------\n");
        menuLines.append(header).append("\n");
        menuLines.append("--------------------------\n");
        menuLines.append(description).append("\n");
        for (MenuOptions<T> entry : options.values()) {
            menuLines.append(entry).append("\n");
        }
        System.out.print(menuLines);
    }

    public Runnable getActionForOption(T key) {
        if (options.containsKey(key)) {
            return options.get(key).getAction();
        }
        return null;
    }
}