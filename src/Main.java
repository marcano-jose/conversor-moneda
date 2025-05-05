import com.google.gson.Gson;

import java.util.Date;

public class Main {
    public static void main(String[] args) {
        String apiKey = System.getenv("MI_API_KEY");

        if (apiKey == null || apiKey.trim().isEmpty()) {
            throw new IllegalStateException("Variable de entorno, MI_API_KEY, sin configurar o vacía. \n" +
                    "La aplicación no puede continuar.");
        }
        System.out.println("API Key obtenida del entorno del Sistema Operativo.");

        String baseCurrency = "USD";

        ExchangeRateClient client = new ExchangeRateClient(apiKey);

        try {
            String exchangeRateDataJson = client.getExchangeRate(baseCurrency);

            Gson gson = new Gson();
            ExchangeRateResponse response = gson.fromJson(exchangeRateDataJson, ExchangeRateResponse.class);

            // Ejemplo:
            Date fecha = new Date(response.getTime_last_update_unix() * 1000); // Se multiplica por 1000 porque Date espera milisegundos

            System.out.println("Ultima actualización: " + fecha);
            System.out.println("Base Dólar estadounidense: " + response.getBase_code());
            System.out.println("Tasas:");
            System.out.println("- Peso argentino: " + response.getConversion_rates().get("ARS"));
            System.out.println("- Boliviano boliviano: " + response.getConversion_rates().get("BOB"));
            System.out.println("- Real brasileño: " + response.getConversion_rates().get("BRL"));
            System.out.println("- Peso chileno: " + response.getConversion_rates().get("CLP"));
            System.out.println("- Peso colombiano: " + response.getConversion_rates().get("VES"));

        } catch (ExchangeRateClientException e) {
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
