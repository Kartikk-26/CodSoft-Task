import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CurrencyConverter {

    private static final String API_URL = "https://api.exchangerate-api.com/v4/latest/";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Step 1: Currency Selection
        System.out.print("Enter the base currency (e.g., USD): ");
        String baseCurrency = scanner.nextLine().toUpperCase();

        System.out.print("Enter the target currency (e.g., EUR): ");
        String targetCurrency = scanner.nextLine().toUpperCase();

        // Step 2: Fetch real-time exchange rates
        Map<String, Double> rates = fetchExchangeRates(baseCurrency);

        if (rates == null || !rates.containsKey(targetCurrency)) {
            System.out.println("Currency not found or unable to fetch rates.");
            return;
        }

        // Step 3: Amount Input
        System.out.print("Enter the amount in " + baseCurrency + ": ");
        double amount = scanner.nextDouble();

        // Step 4: Currency Conversion
        double convertedAmount = convertCurrency(amount, rates.get(targetCurrency));

        // Step 5: Display Result
        System.out.printf("%.2f %s = %.2f %s\n", amount, baseCurrency, convertedAmount, targetCurrency);
    }

    private static Map<String, Double> fetchExchangeRates(String baseCurrency) {
        try {
            // Build the API URL
            String urlString = API_URL + baseCurrency;
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Check if the response is successful (HTTP status code 200)
            if (connection.getResponseCode() != 200) {
                return null;
            }

            // Read the response
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            // Parse the JSON response (simple parsing for demonstration)
            String jsonResponse = response.toString();
            return parseRates(jsonResponse);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Map<String, Double> parseRates(String jsonResponse) {
        Map<String, Double> rates = new HashMap<>();

        // Find the starting index of the rates object
        String ratesKey = "\"rates\":{";
        int ratesStartIndex = jsonResponse.indexOf(ratesKey) + ratesKey.length();
        int ratesEndIndex = jsonResponse.indexOf("}", ratesStartIndex);
        String ratesObject = jsonResponse.substring(ratesStartIndex, ratesEndIndex);

        // Split the rates string into key-value pairs
        String[] rateEntries = ratesObject.split(",");

        // Populate the rates map
        for (String entry : rateEntries) {
            String[] keyValue = entry.split(":");
            String currency = keyValue[0].replace("\"", "").trim();
            double rate = Double.parseDouble(keyValue[1].trim());
            rates.put(currency, rate);
        }

        return rates;
    }

    private static double convertCurrency(double amount, double exchangeRate) {
        return amount * exchangeRate;
    }
}
