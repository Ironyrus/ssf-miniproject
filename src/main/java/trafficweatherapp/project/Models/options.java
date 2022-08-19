package trafficweatherapp.project.Models;

import java.util.ArrayList;
import java.util.List;

public class options {
    List<options> options;
    String option;
    String optionNearbyLocations;
    String imgUrl;

    public options() {
        
    }

    public options(List<options> options) {
        this.options = options;
    }

    public options(List<options> options, String option, String optionNearbyLocations) {
        this.options = options;
        this.option = option;
        this.optionNearbyLocations = optionNearbyLocations;
    }

    public String getOption() {
        return option;
    }
    public void setOption(String option) {
        this.option = option;
    }
    public String getOptionNearbyLocations() {
        return optionNearbyLocations;
    }
    public void setOptionNearbyLocations(String optionNearbyLocations) {
        this.optionNearbyLocations = optionNearbyLocations;
    }
    public List<options> getOptions() {
        return options;
    }
    public void setOptions(List<options> options) {
        this.options = options;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    
    
}