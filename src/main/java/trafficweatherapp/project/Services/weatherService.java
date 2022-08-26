package trafficweatherapp.project.Services;

import java.util.HashMap;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import trafficweatherapp.project.Models.forecastObj24h;

public class weatherService {

    //for get24hrForecast()
    private forecastObj24h forecast;

    public forecastObj24h getforecastObj() {
        return forecast;
    }

    public weatherService() {
    }

    public void get2hrForecast() {

    }
    
    public Object[] get24hrForecast() {
        RestTemplate template = new RestTemplate();
        ResponseEntity<forecastObj24h> responseEntity =
        template.getForEntity("https://api.data.gov.sg/v1/environment/24-hour-weather-forecast",
                  forecastObj24h.class);
        forecastObj24h forecast = (forecastObj24h)responseEntity.getBody().getItems().get(0);
        this.forecast = forecast;
        
        // System.out.println( (((HashMap<String,Integer>) forecast.getGeneral().get("relative_humidity")).get("low")));
        HashMap firstSession = (HashMap)forecast.getPeriods().get(0).get("regions"); //north, south, east, west, central
        HashMap secondSession = (HashMap)forecast.getPeriods().get(1).get("regions");
        HashMap thirdSession = (HashMap)forecast.getPeriods().get(2).get("regions");
        
        String[] regions = {"north", "south", "central", "east", "west"};
        Object[] allRegions = new Object[3];
        HashMap[] apiData = {firstSession, secondSession, thirdSession};

        for (int i = 0; i < allRegions.length; i++) {
            String[] weather = new String[5];
            for (int j = 0; j < weather.length; j++) {
                weather[j] = getUrl(getForecastForRegion(regions[j], apiData[i]));
            }
            allRegions[i] = weather;
        }
        return allRegions;
    }

    public void get4DForecast() {

    }

    //Helper classes
    public String getForecastForRegion(String region, HashMap apiData) {
        
        switch (region) {
            case "north":
            return (String)apiData.get("north");

            case "south":
            return (String)apiData.get("south");

            case "central":
            return (String)apiData.get("central");

            case "east":
            return (String)apiData.get("east");

            case "west":
            return (String)apiData.get("west");
        
            default:
            return "";
        }
    }

    public String getUrl(String weather) {

        // https://www.nea.gov.sg/assets/images/icons/weather-bg/PC.png --> Partly Cloudy (Day)
        // https://www.nea.gov.sg/assets/images/icons/weather-bg/PN.png --> Partly Cloudy (Night)
        // https://www.nea.gov.sg/assets/images/icons/weather-bg/TL.png --> Thundery Showers
        // https://www.nea.gov.sg/assets/images/icons/weather-bg/HR.png --> Heavy Rain
        // https://www.nea.gov.sg/assets/images/icons/weather-bg/LR.png --> Light Rain
        // https://www.nea.gov.sg/assets/images/icons/weather-bg/CL.png --> Cloudy

        switch (weather) {
            case "Partly Cloudy (Day)":
                return "https://www.nea.gov.sg/assets/images/icons/weather-bg/PC.png";

            case "Partly Cloudy (Night)":
                return "https://www.nea.gov.sg/assets/images/icons/weather-bg/PN.png";

            case "Thundery Showers":
                return "https://www.nea.gov.sg/assets/images/icons/weather-bg/TL.png";

            case "Heavy Rain":
                return "https://www.nea.gov.sg/assets/images/icons/weather-bg/HR.png";

            case "Light Rain":
                return "https://www.nea.gov.sg/assets/images/icons/weather-bg/LR.png";


            case "Cloudy":
                return "https://www.nea.gov.sg/assets/images/icons/weather-bg/CL.png";
        
            default:
                return "No weather data found - check url";
        }
    }

}