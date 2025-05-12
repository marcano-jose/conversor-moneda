import com.aluracursos.conversormoneda.services.Calculator;
import com.aluracursos.conversormoneda.services.Rates;
import com.aluracursos.conversormoneda.utils.InputHandler;
import com.aluracursos.conversormoneda.utils.MenuBuilder;

public class Main {
    private static final String APIKEY = System.getenv("MI_API_KEY");
    private static final String MENU_HEADER = " App. Conversor de Moneda";
    private static final String MENU_DESCRIPTION = "Transacciones:";
    private static final Integer OPTION_TASAS = 1;
    private static final Integer OPTION_CALCULADORA = 2;
    private static final Integer OPTION_SALIR = 3;
    private static final String INVALID_OPTION_MESSAGE = "Error: Opción inválida. Por favor, elija una transacción válida.";

    public static void main(String[] args) {
        if (APIKEY == null || APIKEY.trim().isEmpty()) {
            throw new IllegalStateException("Variable de entorno, MI_API_KEY, sin configurar o vacía. " +
                    "La aplicación no puede continuar.");
        }

        Rates rates = new Rates(APIKEY);
        Calculator calculator = new Calculator(APIKEY);
        MenuBuilder<Integer> menu = new MenuBuilder<>(MENU_HEADER, MENU_DESCRIPTION);
        InputHandler inputData = new InputHandler();

        menu.addOption(OPTION_TASAS, "Tasas", rates::showExchangeRates);
        menu.addOption(OPTION_CALCULADORA, "Calculadora", calculator::showExchangeCalculator);
        menu.addOption(OPTION_SALIR, "Salir", () -> System.out.println("Saliendo ..."));

        Integer selection;
        Runnable selectedAction;

        do {
            menu.display();
            selection = (int) inputData.readDecimal("\nElegir transacción: ");
            selectedAction = menu.getActionForOption(selection);

            if (selectedAction != null) {
                selectedAction.run();
            } else {
                System.err.println(INVALID_OPTION_MESSAGE);
            }
        } while (!selection.equals(OPTION_SALIR));

        inputData.closeScanner();
    }
}
