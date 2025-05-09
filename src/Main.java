import com.aluracursos.conversormoneda.services.Calculator;
import com.aluracursos.conversormoneda.services.Rates;

public class Main {
    public static void main(String[] args) {
        String apiKey = System.getenv("MI_API_KEY");

        if (apiKey == null || apiKey.trim().isEmpty()) {
            throw new IllegalStateException("Variable de entorno, MI_API_KEY, sin configurar o vacía. " +
                    "La aplicación no puede continuar.");
        }

        Rates rates = new Rates(apiKey);
        //Calculator calculator = new Calculator(apiKey);

        rates.showExchangeRates();
        //calculator.display();
    }
}
