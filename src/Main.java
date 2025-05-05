public class Main {
    public static void main(String[] args) {
        String apiKey = System.getenv("MI_API_KEY");
        if (apiKey == null || apiKey.trim().isEmpty()) {
            throw new IllegalStateException("La variable de entorno MI_API_KEY no está configurada o está vacía. " +
                    "La aplicación no puede continuar.");
        }
        System.out.println("API Key obtenida del entorno.");

        String baseCurrency = "USD";
        String targetCurrency = "VES";

        ExchangeRateClient client = new ExchangeRateClient(apiKey);

        try {
            String exchangeRateData = client.getExchangeRate(baseCurrency);
            //String exchangeRateData = client.getExchangeRate(baseCurrency, targetCurrency);
            System.out.println(exchangeRateData);
            // Procesar los datos de la tasa de cambio
        } catch (ExchangeRateClientException e) {
            System.err.println(e.getMessage());
            // Aquí puedes implementar lógica específica para los errores del servicio
            // Podrías registrar el error con más detalle (e.getCause()),
            // mostrar un mensaje de error amigable al usuario,
            // intentar una acción de recuperación, etc.
            if (e.getCause() != null) {
                System.err.println(e.getCause().getMessage());
            }
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
            Thread.currentThread().interrupt();
            // Manejar la interrupción adecuadamente (por ejemplo, limpiar recursos, salir)
        }
    }
}