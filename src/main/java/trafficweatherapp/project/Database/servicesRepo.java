package trafficweatherapp.project.Database;

import jakarta.json.JsonObject;
import trafficweatherapp.project.Models.options;

public interface servicesRepo {

    public void saveOptions(String username, options options);

    public options getOptions(String username);

    public void deleteAll();

}