package trafficweatherapp.project.Services;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class dateTimeService {
    
    public dateTimeService() {

    }

    public String dateFormat(String date) {
        //2022-08-26
        String year = date.substring(0, 4);
        String month = date.substring(5, 7);
        String day = date.substring(8);

        switch (month) {
            case "01":
                month = "January";
                break;

            case "02":
                month = "February";                
                break;
            
            case "03":
                month = "March";
    
                break;

            case "04":
            month = "April";
                
                break;
            
            case "05":
            month = "May";
                break;

            case "06":
            month = "June";
                
                break;

            case "07":
            month = "July";
                
                break;

            case "08":
            month = "August";
                
                break;

            case "09":
            month = "September";
                
                break;
            case "10":
            month = "October";
                
                break;
            case "11":
            month = "November";
                
                break;

            case "12":
            month = "December";
                
                break;

            default:
                break;
        }

        return day + " " + month + " " + year;
    }

    public String timeFormat(String time) {
        //16:53:09
        int HH = Integer.parseInt(time.substring(0, 2));
        String ampm = "";
        if(HH > 12){
            HH = HH - 12;
            ampm = "pm";
        } else {
            ampm = "am";
            if(HH == 12)
                ampm = "pm";
            // if(HH == 00){
            //     HH = 12;
            //     ampm = "am";
            // }
            else if(HH == 00) {
                HH = 12;
                ampm = "am";
            }
                
        }
        
        return HH + time.substring(2, 5) + " " + ampm;
    }

    public String getCurrentDay() {
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        System.out.println(new SimpleDateFormat("HH:mm", Locale.ENGLISH).format(date));
        System.out.println(new SimpleDateFormat("EE", Locale.ENGLISH).format(date.getTime()));
        System.out.println(new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date.getTime()));
        return new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date.getTime());
    }
}
