import com.aluracursos.conversormoneda.models.RatesInformation;
import com.aluracursos.conversormoneda.services.ApiClient;
import com.aluracursos.conversormoneda.utils.ApiClientException;
import com.aluracursos.conversormoneda.services.Calculator;
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

        String baseCurrency = "USD";
        List<String> preferredCurrencies = Arrays.asList("ARS", "BOB", "BRL", "CLP", "COP", "USD");

        ApiClient apiClient = new ApiClient(apiKey);
        // ExchangeRates rates = new ExchangeRates(preferredCurrencies);
        Calculator calculator = new Calculator();

        try {
            String exchangeRateJson = apiClient.lookupInfo(baseCurrency);

            Gson gson = new Gson();
            RatesInformation response = gson.fromJson(exchangeRateJson, RatesInformation.class);

            // rates.showExchangeInfo(response);
            calculator.display();

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
