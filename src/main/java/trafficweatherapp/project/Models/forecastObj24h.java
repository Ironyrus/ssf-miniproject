package trafficweatherapp.project.Models;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class forecastObj24h {

    ArrayList<forecastObj24h> items;
    HashMap valid_period;
    HashMap general;
    ArrayList<HashMap> periods;
    

    public forecastObj24h() {
    }

    public ArrayList<forecastObj24h> getItems() {
        return items;
    }
    public void setItems(ArrayList<forecastObj24h> items) {
        this.items = items;
    }
    
    public HashMap getGeneral() {
        return general;
    }
    public void setGeneral(HashMap general) {
        this.general = general;
    }
    public ArrayList<HashMap> getPeriods() {
        return periods;
    }
    public void setPeriods(ArrayList<HashMap> periods) {
        this.periods = periods;
    }

    @Override
    public String toString() {
        return "forecastObj24h [general=" + general + ", items=" + items + ", periods=" + periods + ", valid_period="
                + valid_period + "]";
    }

    public HashMap getValid_period() {
        return valid_period;
    }

    public void setValid_period(HashMap valid_period) {
        this.valid_period = valid_period;
    }

    

    

    

    
    
}
