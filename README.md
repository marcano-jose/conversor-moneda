# Conversor de Moneda

Esta aplicación demuestra la sólida comprensión de los principios de la Programación Orientada a Objetos y las buenas prácticas de desarrollo en Java. La clara separación de responsabilidades, el manejo robusto de errores y el uso de características modernas del lenguaje contribuyen a una aplicación bien estructurada, mantenible y extensible.

## Características

La aplicación se estructura de la siguiente forma:

- Main.java
- com.aluracursos.conversormoneda
    - models
        - Currencies.java
        - CurriencesPair.java
        - MenuOptions.java
        - RatesInformation.java
    - services
        - BaseRateService.java
        - Calculator.java
        - Rates.java
    - utils
        - ApiClient.java
        - ApiClientException.java
        - InputHandler.java
        - MenuBuilder.java
        - UserInterface.java

Resaltando lo siguiente:

1. **Claros Principios de Diseño Orientado a Objetos**:

- **Encapsulamiento**: Cada clase mantiene su propio estado y comportamiento, exponiendo solo lo necesario a través de métodos públicos. Por ejemplo, `Rates` y `Calculator` gestionan la lógica de obtención y cálculo de tasas, respectivamente, sin exponer directamente los detalles de la API o el manejo de datos.

- **Abstracción**: La clase abstracta `BaseRateService` define el comportamiento común para los servicios relacionados con tasas (obtención de datos de la API, manejo de errores), permitiendo que las clases concretas (`Rates` y `Calculator`) se enfoquen en su lógica específica.

- **Herencia**: Aunque no se ve una herencia directa entre clases concretas, `Calculator` y `Rates` heredan el comportamiento común de `BaseRateService`, promoviendo la reutilización de código y la consistencia.

- **Polimorfismo**: Se observa a través del uso de la interfaz `Runnable` en `MenuBuilder`. Diferentes acciones (mostrar tasas, calculadora, salir) pueden ser asociadas a las opciones del menú y ejecutadas de manera uniforme a través del método `run()`.

2. **Separación de Responsabilidades (Single Responsibility Principle)**:

- `Main`: Su única responsabilidad es la de inicializar la aplicación, configurar el menú principal y controlar el flujo de la interacción del usuario. No se encarga de la lógica de negocio de las tasas o los cálculos.

- `Rates`: Se encarga específicamente de obtener y mostrar las tasas de cambio.

- `Calculator`: Su responsabilidad es tomar la entrada del usuario y realizar los cálculos de conversión de moneda.

- `ApiClient`: Su única tarea es comunicarse con la API externa para obtener la información necesaria.

- `InputHandler`: Maneja la entrada del usuario de forma segura y con validación.

- `MenuBuilder`: Se dedica a la creación y visualización del menú interactivo.

- `UserInterface`: Proporciona métodos para mostrar mensajes formateados al usuario, manteniendo la presentación separada de la lógica.

- **Clases en el paquete** `models`: Representan las estructuras de datos de la aplicación, como las monedas (`Currencies`), el par de monedas (`CurrenciesPair`), las opciones del menú (`MenuOptions`) y la información de las tasas (`RatesInformation`).

3. **Uso de Patrones de Diseño Implícitos**:

- **Factory (Simple Factory)**: Aunque no es un patrón formalmente implementado con una clase factory explícita, la clase `MenuBuilder` actúa como una especie de fábrica para crear y gestionar las opciones del menú y sus acciones asociadas.

- **Strategy**: El uso de `Runnable` para las acciones del menú permite definir diferentes estrategias (las implementaciones de `run()` en los métodos referenciados) que se ejecutan según la selección del usuario.

4. **Robustez y Manejo de Errores**:

- **Validación de la clave de API**: El programa verifica al inicio que la variable de entorno `MI_API_KEY` esté configurada, evitando fallos posteriores por falta de autenticación.

- **Manejo de excepciones de la API**: La clase `ApiClient` implementa un manejo detallado de los posibles errores de la API, proporcionando mensajes informativos basados en el tipo de error recibido (clave inválida, cuota alcanzada, etc.).

- **Manejo de excepciones de entrada**: `InputHandler` utiliza bloques `try-catch` para asegurar que la entrada del usuario sea válida (números decimales positivos y códigos de moneda existentes).

- **Propagación y manejo de** `InterruptedException`: Se maneja la excepción `InterruptedException` al interactuar con la API, asegurando una respuesta adecuada en caso de interrupción del hilo.

5. **Organización y Modularidad**:

- **Uso de paquetes**: La aplicación está claramente organizada en paquetes (`models`, `services`, `utils`), lo que facilita la comprensión, el mantenimiento y la escalabilidad del código. Cada paquete agrupa clases con responsabilidades relacionadas.

- **Clases pequeñas y enfocadas**: Cada clase tiende a tener una responsabilidad bien definida, lo que hace que el código sea más legible y fácil de probar.

6. **Uso de Características Avanzadas de Java**:

- **Enums** (`Currencies`): Se utiliza un `enum` para representar los códigos de moneda, lo que garantiza la seguridad de tipos y facilita la gestión de las monedas soportadas.

- **Records** (`CurrenciesPair`): Se utiliza un `record` para representar un par de monedas de forma concisa e inmutable.

- **Lambdas y Referencias a Métodos**: El `MenuBuilder` utiliza referencias a métodos (`rates::showExchangeRates`, `calculator::showExchangeCalculator`) y una lambda (`() -> System.out.println("Saliendo ...")`) para asociar acciones a las opciones del menú, lo que hace que el código sea más conciso y expresivo.

- **StringBuilder**: Se utiliza `StringBuilder` en `MenuBuilder` para construir eficientemente las cadenas del menú.

- `HttpClient`: Se utiliza la API de `HttpClient` introducida en Java 11 para realizar las llamadas HTTP a la API externa.

## Tecnologías empleadas

- Fedora Linux 41 (Workstation Edition) 
- openjdk 17.0.15
- IntelliJ IDEA 2025.1.1.1 (Community Edition)

## Instalación y ejecución

1. Clonar este repositorio:

```
git clone https://github.com/marcanojuan/conversor-moneda.git
```

2. 

## Uso


## Diagrama UML de Clase


## Mejoras futuras

- **Historial de conversiones**: Incorporar la función de registrar y visualizar el historial de las conversiones más recientes, ofreciendo a los usuarios una perspectiva detallada de sus actividades.

- **Soporte para más monedas**: Extender la selección de divisas disponibles para la conversión, posibilitando a los usuarios operar entre un espectro más amplio de opciones monetarias.

- **Registros con marca de tiempo**: Emplear las funcionalidades de la biblioteca `java.time` para generar registros de las conversiones efectuadas, detallando las monedas involucradas y la hora exacta de cada operación.

