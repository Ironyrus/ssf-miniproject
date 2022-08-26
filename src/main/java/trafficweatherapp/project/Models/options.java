package trafficweatherapp.project.Models;

import java.util.ArrayList;
import java.util.List;

public class options {
    List<options> options;
    String option;
    String optionNearbyLocations;
    String imgUrl;
    String timestamp;
    String sortup;
    String delete;

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

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getSortup() {
        return sortup;
    }

    public void setSortup(String sortup) {
        this.sortup = sortup;
    }

    public String getDelete() {
        return delete;
    }

    public void setDelete(String delete) {
        this.delete = delete;
    }

    @Override
    public String toString() {
        return "options [delete=" + delete + ", imgUrl=" + imgUrl + ", option=" + option + ", optionNearbyLocations="
                + optionNearbyLocations + ", options=" + options + ", sortup=" + sortup + ", timestamp=" + timestamp
                + "]";
    }

    
    
}