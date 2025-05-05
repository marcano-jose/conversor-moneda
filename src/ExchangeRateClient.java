import com.google.gson.Gson;
import com.google.gson.JsonObject;
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

    public String standardExchangeRate(String baseCurrency) {
        String urlRequest = API_URL_BASE + apiKey + "/latest/" + baseCurrency;

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
                handleApiError(response);
            }
        } catch (IOException e) {
            System.err.println("Error de IO al obtener la tasa de cambio para " + baseCurrency + ": " + e.getMessage());
        } catch (InterruptedException e) {
            System.err.println("Operación interrumpida al obtener la tasa de cambio para " + baseCurrency + ": " + e.getMessage());
            Thread.currentThread().interrupt();
        }
        return null;
    }

    private void handleApiError(HttpResponse<String> response) {
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

        } catch (Exception e) {
            errorMessage += ". Error al analizar la respuesta del error: " + errorBody;
        }
        System.out.println(errorMessage);
    }
}