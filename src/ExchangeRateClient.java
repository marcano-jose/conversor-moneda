import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class ExchangeRateClient {

    private static final String API_URL_BASE = "https://v6.exchangerate-api.com/v6/";
    private static final int CONNECT_TIMEOUT_SECONDS = 20;
    private static final int REQUEST_TIMEOUT_MINUTES = 2;
    private final HttpClient httpClient;
    private final String apiKey;

    public ExchangeRateClient(String apiKey) {
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(CONNECT_TIMEOUT_SECONDS))
                .build();
        this.apiKey = apiKey;
    }

    public String getExchangeRate(String baseCurrency) throws ExchangeRateClientException, InterruptedException {
        String urlRequest = API_URL_BASE + apiKey + "/latest/" + baseCurrency;
        return processExchangeRateResponse(urlRequest, baseCurrency);
    }

    public String getExchangeRate(String baseCurrency, String targetCurrency) throws ExchangeRateClientException, InterruptedException {
        String urlRequest = API_URL_BASE + apiKey + "/pair/" + baseCurrency + "/" + targetCurrency;
        String description = "el par " + baseCurrency + "-" + targetCurrency;
        return processExchangeRateResponse(urlRequest, description);
    }

    private String processExchangeRateResponse(String urlRequest, String description) throws ExchangeRateClientException, InterruptedException {
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
                throw handleApiError(response);
            }
        } catch (IOException e) {
            throw new ExchangeRateClientException("Error de IO al obtener la tasa de cambio para " + description, e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new InterruptedException("Operación interrumpida al obtener la tasa de cambio para " + description);
        }
    }

    private ExchangeRateClientException handleApiError(HttpResponse<String> response) throws ExchangeRateClientException {
        String errorBody = response.body();
        String errorMessage = "Error al obtener la tasa de cambio. Código de estado: " + response.statusCode();

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
        throw new ExchangeRateClientException(errorMessage);
    }
}