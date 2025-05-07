import com.aluracursos.conversormoneda.models.ExchangeRateInformation;
import com.aluracursos.conversormoneda.services.ApiClient;
import com.aluracursos.conversormoneda.services.ApiClientException;
import com.aluracursos.conversormoneda.services.ExchangeCalculator;
import com.aluracursos.conversormoneda.services.ExchangeRates;
import com.google.gson.Gson;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String apiKey = System.getenv("MI_API_KEY");

        if (apiKey == null || apiKey.trim().isEmpty()) {
            throw new IllegalStateException("Variable de entorno, MI_API_KEY, sin configurar o vacía. " +
                    "La aplicación no puede continuar.");
        }
        System.out.println("API Key obtenida del entorno del Sistema Operativo.");

        String baseCurrency = "USD";
        List<String> preferredCurrencies = Arrays.asList("ARS", "BOB", "BRL", "CLP", "COP", "USD");

        ApiClient apiClient = new ApiClient(apiKey);
        ExchangeRates rates = new ExchangeRates(preferredCurrencies);
        ExchangeCalculator calculator = new ExchangeCalculator();

        try {
            String exchangeRateJson = apiClient.lookupInfo(baseCurrency);

            Gson gson = new Gson();
            ExchangeRateInformation response = gson.fromJson(exchangeRateJson, ExchangeRateInformation.class);

            rates.showExchangeInfo(response);
            calculator.show();

        } catch (ApiClientException e) {
            System.err.println(e.getMessage());

            if (e.getCause() != null) {
                System.err.println(e.getCause().getMessage());
            }

        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}
