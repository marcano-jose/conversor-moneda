package com.aluracursos.conversormoneda.utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class ApiClient {

    private static final String API_URL_BASE = "https://v6.exchangerate-api.com/v6/";
    private static final int CONNECT_TIMEOUT_SECONDS = 20;
    private static final int REQUEST_TIMEOUT_MINUTES = 2;
    private final HttpClient httpClient;
    private final String APIKEY;

    public ApiClient(String APIKEY) {
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(CONNECT_TIMEOUT_SECONDS))
                .build();
        this.APIKEY = APIKEY;
    }

    public String lookupInfo(String baseCurrency) throws ApiClientException, InterruptedException {
        String urlRequest = API_URL_BASE + APIKEY + "/latest/" + baseCurrency;
        return queryInfo(urlRequest, baseCurrency);
    }

    public String lookupInfo(String baseCurrency, String targetCurrency) throws ApiClientException, InterruptedException {
        String urlRequest = API_URL_BASE + APIKEY + "/pair/" + baseCurrency + "/" + targetCurrency;
        String description = "el par " + baseCurrency + "-" + targetCurrency;
        return queryInfo(urlRequest, description);
    }

    private String queryInfo(String urlRequest, String description) throws ApiClientException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(urlRequest))
                .GET()
                .timeout(Duration.ofMinutes(REQUEST_TIMEOUT_MINUTES))
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return response.body();
            } else {
                throw handlingApiErrors(response);
            }
        } catch (IOException e) {
            throw new ApiClientException("Error IO al consultar la información cambiaría para " + description, e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new InterruptedException("Operación interrumpida al consultar la información cambiaría para " + description);
        }
    }

    private ApiClientException handlingApiErrors(HttpResponse<String> response) throws ApiClientException {
        String errorBody = response.body();
        String errorMessage = "Error al consultar la información cambiaría. Código de estado: " + response.statusCode();

        try {
            Gson gson = new Gson();
            JsonObject errorJson = gson.fromJson(errorBody, JsonObject.class);
            String errorType = errorJson.get("error-type").getAsString();

            errorMessage += switch (errorType) {
                case "unsupported-code" -> ". Moneda no soportada.";
                case "malformed-request" -> ". Solicitud malformada.";
                case "invalid-key" -> ". Clave de API inválida.";
                case "inactive-account" -> ". Cuenta inactiva (correo no confirmado).";
                case "quota-reached" -> ". Cuota de solicitudes alcanzada.";
                default -> ". Error desconocido."; // Error no cubierto por la API
            };

        } catch (JsonParseException e) {
            errorMessage += ". Error al analizar la respuesta del error: " + errorBody;
        } catch (Exception e) {
            errorMessage += ". Error inesperado al procesar la respuesta del error: " + errorBody;
        }
        throw new ApiClientException(errorMessage);
    }
}
