package trafficweatherapp.project.Services;

import java.util.HashMap;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;

import trafficweatherapp.project.Models.forecastObj24h;
import trafficweatherapp.project.Models.forecastObj2h;

public class weatherService {

    //for get24hrForecast()
    private forecastObj24h forecast;

    public forecastObj24h getforecastObj() {
        return forecast;
    }

    public weatherService() {
    }

    public forecastObj2h get2hrForecast() {
        RestTemplate template = new RestTemplate();
        ResponseEntity<forecastObj2h> responseEntity =
        template.getForEntity("https://api.data.gov.sg/v1/environment/2-hour-weather-forecast",
            forecastObj2h.class);

        forecastObj2h forecastObj =(forecastObj2h)responseEntity.getBody();
        ArrayList<HashMap> forecast = (ArrayList<HashMap>)forecastObj.getItems().get(0).getForecasts();
        ArrayList<HashMap> metadata = forecastObj.getArea_metadata();
        for (int i = 0; i < metadata.size(); i++) {
            // System.out.println(metadata.get(i).get("name")); //Ang Mo Kio
            HashMap coords = (HashMap)metadata.get(i).get("label_location");
            Double latitude = (Double)coords.get("latitude");
            Double longitude = (Double)coords.get("longitude");
        }
        return forecastObj;
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
                return "/animated/cloudy-day-3.svg";
                // return "https://www.nea.gov.sg/assets/images/icons/weather-bg/PC.png";

            case "Partly Cloudy (Night)":
                return "/animated/cloudy-night-3.svg";
                // return "https://www.nea.gov.sg/assets/images/icons/weather-bg/PN.png";

            case "Thundery Showers":
                return "/animated/thunder.svg";
                // return "https://www.nea.gov.sg/assets/images/icons/weather-bg/TL.png";

            case "Heavy Rain":
                return "/animated/rainy-6.svg";
                // return "https://www.nea.gov.sg/assets/images/icons/weather-bg/HR.png";

            case "Light Rain":
                return "/animated/rainy-5.svg";
                // return "https://www.nea.gov.sg/assets/images/icons/weather-bg/LR.png";

            case "Showers":
                return "/animated/rainy-3.svg";
                // return "https://www.nea.gov.sg/assets/images/icons/weather-bg/LR.png";

            case "Cloudy":
                return "/animated/cloudy.svg";
                // return "https://www.nea.gov.sg/assets/images/icons/weather-bg/CL.png";
        
            case "Moderate Rain":
                return "/animated/rainy-5.svg";
                // return "https://www.nea.gov.sg/assets/images/icons/weather-bg/RA.png";

            default:
                return "No weather data found - check url";
        }
    }

}