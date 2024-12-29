import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class ConversorMoneda {
    private static final String API = "cur_live_ugIQUVf7CH7l2uack9ejd4X375OZJqqw7iUhsjaw";
    private static final String URL = "https://api.currencyapi.com/v3/latest?apikey=cur_live_ugIQUVf7CH7l2uack9ejd4X375OZJqqw7iUhsjaw&currencies=EUR%2CUSD%2CCAD&base_currency=ALL";

    public static double convertir(String monedaOrigen, String monedaDestino) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL))
                .header("apikey", API_KEY)
                .header("currencies", monedaDestino)
                .header("base_currency", monedaOrigen)
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(response.body(), JsonObject.class);

        if (jsonObject.has("error")) {
            throw new RuntimeException("Error from CurrencyAPI: " + jsonObject.get("error").getAsString());
        }

        double tasaCambio = jsonObject.get("data").getAsJsonObject().get(monedaDestino).getAsDouble();
        return tasaCambio;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Bienvenido al conversor de monedas!");
        System.out.println("Ingrese la moneda de origen: ");
        String monedaOrigen = scanner.nextLine().toUpperCase();

        System.out.print("Ingrese la moneda de destino: ");
        String monedaDestino = scanner.nextLine().toUpperCase();

        try {
            double tasaCambio = convertir(monedaOrigen, monedaDestino);
            System.out.println("1 " + monedaOrigen + " equivale a " + tasaCambio + " " + monedaDestino);
        } catch (IOException | InterruptedException | RuntimeException e) {
            System.out.println("Error al realizar la conversión: " + e.getMessage());
        }
    }
}
