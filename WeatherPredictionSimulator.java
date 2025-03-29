import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

// API Handler Class
class APIHandler {
    private static final String GEO_API_URL = "https://geocoding-api.open-meteo.com/v1/search";
    private static final String WEATHER_API_URL = "https://api.open-meteo.com/v1/forecast";


    public JSONObject fetchLocationData(String cityName) {
        try {
            String url = GEO_API_URL + "?name=" + cityName;
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            return new JSONObject(response.toString()).getJSONArray("results").getJSONObject(0);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public JSONObject fetchWeatherData(double latitude, double longitude) {
        try {
            String url = WEATHER_API_URL + "?latitude=" + latitude + "&longitude=" + longitude + "&current_weather=true";
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            return new JSONObject(response.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

// Weather Condition Interface
interface WeatherCondition {
    double getTemperature();
    double getHumidity();
    double getWindSpeed();
    String visualizeCondition();
}

// Prediction Algorithm Interface
interface PredictionAlgorithm {
    void setWeatherData(JSONObject data);
    double predictTemperature();
    double predictHumidity();
    double predictWindSpeed();
}

// Weather Condition Classes
class SunnyCondition implements WeatherCondition {
    private double temperature;
    private double humidity;
    private double windSpeed;

    public SunnyCondition(double temperature, double humidity, double windSpeed) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
    }

    @Override
    public double getTemperature() {
        return temperature;
    }

    @Override
    public double getHumidity() {
        return humidity;
    }

    @Override
    public double getWindSpeed() {
        return windSpeed;
    }

    @Override
    public String visualizeCondition() {
        return "☀️ Sunny";
    }
}

// Prediction Algorithm Implementations
class ForecastPrediction implements PredictionAlgorithm {
    private JSONObject weatherData;

    @Override
    public void setWeatherData(JSONObject data) {
        this.weatherData = data;
    }

    @Override
    public double predictTemperature() {
        return weatherData.getJSONObject("current_weather").getDouble("temperature");
    }

    @Override
    public double predictHumidity() {
        // Here you could retrieve humidity if available, or simulate it based on other factors
        return 50.0; // Placeholder value
    }

    @Override
    public double predictWindSpeed() {
        return weatherData.getJSONObject("current_weather").getDouble("windspeed");
    }
}

// Region Class
abstract class Region {
    protected double latitude;
    protected double longitude;
    protected PredictionAlgorithm predictionAlgorithm;

    public Region(double latitude, double longitude, PredictionAlgorithm algorithm) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.predictionAlgorithm = algorithm;
    }

    public abstract String getRegionType();
    public abstract void predictWeather();
}

// Urban Location Class
class UrbanLocation extends Region {
    public UrbanLocation(double latitude, double longitude, PredictionAlgorithm algorithm) {
        super(latitude, longitude, algorithm);
    }

    @Override
    public String getRegionType() {
        return "Urban";
    }

    @Override
    public void predictWeather() {
        predictionAlgorithm.setWeatherData(new APIHandler().fetchWeatherData(latitude, longitude));
        System.out.println("Predicted Temperature: " + predictionAlgorithm.predictTemperature() + "°C");
        System.out.println("Predicted Humidity: " + predictionAlgorithm.predictHumidity() + "%");
        System.out.println("Predicted Wind Speed: " + predictionAlgorithm.predictWindSpeed() + " km/h");
    }
}

// Main Class to Run the Simulator
public class WeatherPredictionSimulator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter city name for weather prediction: ");
        String cityName = scanner.nextLine();

        APIHandler apiHandler = new APIHandler();
        JSONObject locationData = apiHandler.fetchLocationData(cityName);
        
        if (locationData != null) {
            double latitude = locationData.getDouble("latitude");
            double longitude = locationData.getDouble("longitude");
            System.out.println("Location Coordinates: " + latitude + ", " + longitude);

            // Create a weather prediction algorithm
            PredictionAlgorithm algorithm = new ForecastPrediction();
            Region region = new UrbanLocation(latitude, longitude, algorithm);

            // Predict the weather
            region.predictWeather();
        } else {
            System.out.println("Could not retrieve location data. Please check the city name.");
        }
        scanner.close();
    }
}
