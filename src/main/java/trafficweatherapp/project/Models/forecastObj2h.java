package trafficweatherapp.project.Models;

import java.util.ArrayList;
import java.util.HashMap;

public class forecastObj2h {

    ArrayList<forecastObj2h> items;
    ArrayList<HashMap> area_metadata;

    String update_timestamp;
    String timestamp;
    HashMap valid_period;
    ArrayList<HashMap> forecasts;

    String area;
    String url;
    String latitude;
    String longitude;

    public forecastObj2h() {

    }

    public forecastObj2h(String area, String url, String timestamp, String latitude, String longitude) {
        this.area = area;
        this.url = url;
        this.timestamp = timestamp;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public ArrayList<forecastObj2h> getItems() {
        return items;
    }

    public void setItems(ArrayList<forecastObj2h> items) {
        this.items = items;
    }

    public ArrayList<HashMap> getArea_metadata() {
        return area_metadata;
    }

    public void setArea_metadata(ArrayList<HashMap> area_metadata) {
        this.area_metadata = area_metadata;
    }

    public String getUpdate_timestamp() {
        return update_timestamp;
    }

    public void setUpdate_timestamp(String update_timestamp) {
        this.update_timestamp = update_timestamp;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public HashMap getValid_period() {
        return valid_period;
    }

    public void setValid_period(HashMap valid_period) {
        this.valid_period = valid_period;
    }

    public ArrayList<HashMap> getForecasts() {
        return forecasts;
    }

    public void setForecasts(ArrayList<HashMap> forecasts) {
        this.forecasts = forecasts;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    
    

   
}