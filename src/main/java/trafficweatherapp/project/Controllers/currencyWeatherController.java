package trafficweatherapp.project.Controllers;

/*
PS C:\Users\vans_\sdf-workshop1> 
git add . (add ALL content of cart to github)
git commit -m "While Loop"                  (add comment while committing)
git push origin main                        (push to main branch)
*/
//Deploying to Heroku using Github Actions - Kenneth's method
//https://github.com/marketplace/actions/deploy-to-heroku

//Deploying to Heroku using Heroku deployment features:
//https://devcenter.heroku.com/articles/github-integration#enabling-github-integration

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import trafficweatherapp.project.Database.redisService;
import trafficweatherapp.project.Models.forecastObj24h;
import trafficweatherapp.project.Models.options;
import trafficweatherapp.project.Models.weatherObj;
import trafficweatherapp.project.Services.dateTimeService;
import trafficweatherapp.project.Services.egg;
import trafficweatherapp.project.Services.weatherService;

@Controller
public class currencyWeatherController {

    private String currApiKey = System.getenv("FIXER_API_KEY");
    private String key = System.getenv("MYVERYOWN_API_KEY");
    // String key = "";
    private String googApiKey = System.getenv("GOOGLE_API_KEY");
    // String googApiKey="";
    
    @Autowired
    redisService service;

    @GetMapping("/")
    public String block(Model model) {

        return "preindex";
    }

    @GetMapping("/accessgranted")
    public String getHome(Model model, @RequestParam("password") String password,
                                        @RequestParam("timeOfDay") Optional<String> timeOfDay) {
        
        if(timeOfDay.isPresent()){
            weatherService nService = new weatherService();
            Object[] weatherRegions = nService.get24hrForecast();

            //To get valid start and end periods
            forecastObj24h forecastObj = nService.getforecastObj();
            String start = "";
            String end = "";

            int count = 0;

            //For weather icons on map
            for (int i = 0; i < weatherRegions.length; i++) {

                String[] w = (String[])weatherRegions[i]; 
                for (String item : w) {
                    if(timeOfDay.get().equals("morning") & count < 5){
                        start = ((HashMap<String,String>)forecastObj.getPeriods().get(i).get("time")).get("start");
                        end = ((HashMap<String,String>)forecastObj.getPeriods().get(i).get("time")).get("end");
                        model.addAttribute("url" + count, item);
                        String[] date = getStartandEnd(start, end);

                        dateTimeService dt = new dateTimeService();

                        String[] startTimestamp = start.split("T");
                        String starttempDate = startTimestamp[0]; //2022-08-26
                        String tempStart = dt.dateFormat(starttempDate);
                        String tempTimeStart = (startTimestamp[1]).substring(0, 8); //16:53:09
                        String temp2Start = dt.timeFormat(tempTimeStart);
                        start = tempStart + ", " + temp2Start;
                        
                        String[] endTimestamp = end.split("T");
                        String endtempDate = endTimestamp[0]; //2022-08-26
                        String tempEnd = dt.dateFormat(endtempDate);
                        String tempTimeEnd = (endTimestamp[1]).substring(0, 8); //16:53:09
                        String temp2End = dt.timeFormat(tempTimeEnd);
                        end = tempEnd + ", " + temp2End;

                        // For forecast validity period
                        model.addAttribute("startPeriod", start);
                        model.addAttribute("endPeriod", end);
                        // model.addAttribute("startPeriod", date[0]);
                        // model.addAttribute("endPeriod", date[1]);
                    } else if(timeOfDay.get().equals("afternoon") & 4 < count & count < 10) {
                        start = ((HashMap<String,String>)forecastObj.getPeriods().get(i).get("time")).get("start");
                        end = ((HashMap<String,String>)forecastObj.getPeriods().get(i).get("time")).get("end");
                        model.addAttribute("url" + (count - 5), item);
                        // String[] date = getStartandEnd(start, end);
                        
                        dateTimeService dt = new dateTimeService();

                        String[] startTimestamp = start.split("T");
                        String starttempDate = startTimestamp[0]; //2022-08-26
                        String tempStart = dt.dateFormat(starttempDate);
                        String tempTimeStart = (startTimestamp[1]).substring(0, 8); //16:53:09
                        String temp2Start = dt.timeFormat(tempTimeStart);
                        start = tempStart + ", " + temp2Start;
                        
                        String[] endTimestamp = end.split("T");
                        String endtempDate = endTimestamp[0]; //2022-08-26
                        String tempEnd = dt.dateFormat(endtempDate);
                        String tempTimeEnd = (endTimestamp[1]).substring(0, 8); //16:53:09
                        String temp2End = dt.timeFormat(tempTimeEnd);
                        end = tempEnd + ", " + temp2End;

                        //For forecast validity period
                        model.addAttribute("startPeriod", start);
                        model.addAttribute("endPeriod", end);

                        // model.addAttribute("startPeriod", date[0]);
                        // model.addAttribute("endPeriod", date[1]);
                    } else if(timeOfDay.get().equals("night") & count > 9) {
                        start = ((HashMap<String,String>)forecastObj.getPeriods().get(i).get("time")).get("start");
                        end = ((HashMap<String,String>)forecastObj.getPeriods().get(i).get("time")).get("end");
                        model.addAttribute("url" + (count - 10), item);
                        
                        dateTimeService dt = new dateTimeService();

                        String[] startTimestamp = start.split("T");
                        String starttempDate = startTimestamp[0]; //2022-08-26
                        String tempStart = dt.dateFormat(starttempDate);
                        String tempTimeStart = (startTimestamp[1]).substring(0, 8); //16:53:09
                        String temp2Start = dt.timeFormat(tempTimeStart);
                        start = tempStart + ", " + temp2Start;
                        
                        String[] endTimestamp = end.split("T");
                        String endtempDate = endTimestamp[0]; //2022-08-26
                        String tempEnd = dt.dateFormat(endtempDate);
                        String tempTimeEnd = (endTimestamp[1]).substring(0, 8); //16:53:09
                        String temp2End = dt.timeFormat(tempTimeEnd);
                        end = tempEnd + ", " + temp2End;

                        //For forecast validity period
                        model.addAttribute("startPeriod", start);
                        model.addAttribute("endPeriod", end);

                        //For forecast validity period
                        // String[] date = getStartandEnd(start, end);
                        // model.addAttribute("startPeriod", date[0]);
                        // model.addAttribute("endPeriod", date[1]);
                    }
                    count++;
                }
            }

            //For button
            String startForButton = ((HashMap<String,String>)forecastObj.getPeriods().get(0).get("time")).get("start");
            String endForButton = ((HashMap<String,String>)forecastObj.getPeriods().get(0).get("time")).get("end");
            
            dateTimeService dt = new dateTimeService();

            String[] startTimestamp = startForButton.split("T");
            String starttempDate = startTimestamp[0]; //2022-08-26
            String tempStart1 = dt.dateFormat(starttempDate);
            String tempTimeStart = (startTimestamp[1]).substring(0, 8); //16:53:09
            String temp2Start = dt.timeFormat(tempTimeStart);
            String startPeriodStr = tempStart1 + ", " + temp2Start;
            
            String[] endTimestamp = endForButton.split("T");
            String endtempDate = endTimestamp[0]; //2022-08-26
            String tempEnd1 = dt.dateFormat(endtempDate);
            String tempTimeEnd = (endTimestamp[1]).substring(0, 8); //16:53:09
            String temp2End = dt.timeFormat(tempTimeEnd);
            String endPeriodStr = tempEnd1 + ", " + temp2End;

            //For forecast validity period
            // model.addAttribute("startPeriod", start);
            // model.addAttribute("endPeriod", end);

            // String[] date = getStartandEnd(startForButton, endForButton);
            // String startPeriodStr = date[0] + "";
            // String endPeriodStr = date[1] + "";

            String[] tempStart = startPeriodStr.split(" ");
            String[] tempEnd = endPeriodStr.split(" ");

            if(tempStart[3].contains("6")&tempEnd[3].contains("6")) {
                model.addAttribute("button1", "Night");
                model.addAttribute("button2", "Morning");
                model.addAttribute("button3", "Afternoon");
            }else if(tempStart[3].contains("6")&tempEnd[3].contains("12")){
                model.addAttribute("button1", "Morning");
                model.addAttribute("button2", "Afternoon");
                model.addAttribute("button3", "Night");
            } else if(tempStart[3].contains("12")&tempEnd[3].contains("6")){
                model.addAttribute("button1", "Afternoon");
                model.addAttribute("button2", "Night");
                model.addAttribute("button3", "Morning");
            } else if(tempStart[3].contains("00")&tempEnd[3].contains("6")) {
                model.addAttribute("button1", "Night");
                model.addAttribute("button2", "Morning");
                model.addAttribute("button3", "Afternoon");
            }

        } else {
            weatherService nService = new weatherService();
            Object[] weatherRegions = nService.get24hrForecast();

            //To get valid start and end periods
            forecastObj24h forecastObj = nService.getforecastObj();
            String start = "";
            String end = "";
            String[] tempStart = null;
            String[] tempEnd = null;
            Date startPeriod = null;
            Date endPeriod = null;

            String firstSessionStart = ((HashMap<String,String>)forecastObj.getPeriods().get(0).get("time")).get("start");
            String firstSessionEnd = ((HashMap<String,String>)forecastObj.getPeriods().get(0).get("time")).get("end");

            int count = 0;
            for (int i = 0; i < weatherRegions.length; i++) {

                String[] w = (String[])weatherRegions[i]; 
                for (int j = 0; j < w.length; j++) {
                    //Getting current date and time
                    egg egg = new egg();
                    HashMap camera = egg.getTrafficImage("test"); //Getting SGT from LTA traffic cam timestamps - If not Heroku server defaults to UTC
                    ArrayList<HashMap> cameras = (ArrayList<HashMap>)camera.get("cameras");
                    String timeNow = (String)cameras.get(0).get("timestamp");
                    // System.out.println(timeNow); //2022-08-26T17:52:51+08:00
                    int HH = Integer.parseInt(timeNow.substring(11, 13));
                    
                    // SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
                    
                    // Date date = new Date(System.currentTimeMillis());
                    // formatter.format(date);
                    // String[] temp = (date + "").split(" ");
                    // int HH = Integer.parseInt(temp[3].substring(0, 2));

                    start = ((HashMap<String,String>)forecastObj.getPeriods().get(i).get("time")).get("start");
                    end = ((HashMap<String,String>)forecastObj.getPeriods().get(i).get("time")).get("end");

                    dateTimeService dt = new dateTimeService();

                    String[] startTimestamp = start.split("T");
                    String starttempDate = startTimestamp[0]; //2022-08-26
                    String tempStart1 = dt.dateFormat(starttempDate);
                    String tempTimeStart = (startTimestamp[1]).substring(0, 8); //16:53:09
                    String temp2Start = dt.timeFormat(tempTimeStart);
                    String startPeriodStr = tempStart1 + ", " + temp2Start;
                    
                    String[] endTimestamp = end.split("T");
                    String endtempDate = endTimestamp[0]; //2022-08-26
                    String tempEnd1 = dt.dateFormat(endtempDate);
                    String tempTimeEnd = (endTimestamp[1]).substring(0, 8); //16:53:09
                    String temp2End = dt.timeFormat(tempTimeEnd);
                    String endPeriodStr = tempEnd1 + ", " + temp2End;                    

                    //replace below code
                    // String[] tempSandE = getStartandEnd(start, end);
                    // String startPeriodStr = tempSandE[0] + "";
                    // String endPeriodStr = tempSandE[1] + "";
                    // DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX");
                    // startPeriod = null;
                    // endPeriod = null;
                    // String startPeriodStr = "";
                    // String endPeriodStr = "";
                    // try {
                    //     startPeriodStr = df.parse(start) + "";
                    //     startPeriod = df.parse(start);  

                    //     endPeriodStr = df.parse(end) + "";
                    //     endPeriod = df.parse(end);  

                    // } catch (ParseException e) {
                    //     e.printStackTrace();
                    // }
                    
                    //Getting HH from forecastObj 
                    tempStart = startPeriodStr.split(" ");
                    tempEnd = endPeriodStr.split(" ");

                    //Between 6am and 12pm
                    if(6 <= HH & HH <= 12 & tempStart[3].contains("6") & tempEnd[3].contains("12")){
                        model.addAttribute("url" + j, w[j]);
                        model.addAttribute("button1", "Morning");
                        model.addAttribute("button2", "Afternoon");
                        model.addAttribute("button3", "Night");
                        model.addAttribute("startPeriod", startPeriodStr);
                        model.addAttribute("endPeriod", endPeriodStr);
                        if(count == 4)
                            break;
                    //Between 1pm and 6pm
                    } else if(12 <= HH & HH <= 18 & tempStart[3].contains("12") & tempEnd[3].contains("6")){
                        model.addAttribute("url" + j, w[j]);
                        model.addAttribute("button1", "Afternoon");
                        model.addAttribute("button2", "Night");
                        model.addAttribute("button3", "Morning");
                        model.addAttribute("startPeriod", startPeriodStr);
                        model.addAttribute("endPeriod", endPeriodStr);
                        if(count == 4)
                            break;
                    //Between 12am and 6am
                    } else if(tempStart[3].contains("0:00:00") & tempEnd[3].contains("6")) {
                        model.addAttribute("url" + j, w[j]);
                        model.addAttribute("button1", "Night");
                        model.addAttribute("button2", "Morning");
                        model.addAttribute("button3", "Afternoon");
                        model.addAttribute("startPeriod", startPeriodStr);
                        model.addAttribute("endPeriod", endPeriodStr);
                        if(count == 4)
                            break;
                    //Between 6pm and 6am                    
                    } 
                    else if ((18 <= HH || HH <= 6) & tempStart[3].contains("6") & tempEnd[3].contains("6")){ 
                        model.addAttribute("url" + j, w[j]);
                        model.addAttribute("button1", "Night");
                        model.addAttribute("button2", "Morning");
                        model.addAttribute("button3", "Afternoon");
                        model.addAttribute("startPeriod", startPeriodStr);
                        model.addAttribute("endPeriod", endPeriodStr);
                        if(count == 4)
                            break;
                    }
                    count++;
                }
            }

            // DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX");
            // startPeriod = null;
            // endPeriod = null;
            // String startPeriodStr = "";
            // String endPeriodStr = "";
            // try {
            //     startPeriodStr = df.parse(start) + "";
            //     startPeriod = df.parse(start);  

            //     endPeriodStr = df.parse(end) + "";
            //     endPeriod = df.parse(end);  

            // } catch (ParseException e) {
            //     e.printStackTrace();
            // }

            // tempStart = startPeriodStr.split(" ");
            // tempEnd = endPeriodStr.split(" ");
        }

        List<options> optionList = getCameraList();

        List<options> popularOptionList = new ArrayList<>();
        for (int k = 0; k < optionList.size(); k++) {
            if(optionList.get(k).getOption().contains("2701") || optionList.get(k).getOption().contains("2702") || optionList.get(k).getOption().contains("4703") || optionList.get(k).getOption().contains("4713")){
                popularOptionList.add(optionList.get(k));
            }
        }

        model.addAttribute("options", new options(optionList));
        model.addAttribute("checkpointOptions", new options(popularOptionList));

        if(password.trim().equals(key))
            return "index";
        else
            return "preindex";
    }

    //Helper class for above method
    public String[] getStartandEnd(String start, String end) {

        dateTimeService dt = new dateTimeService();

        String[] startTimestamp = start.split("T");
        String starttempDate = startTimestamp[0]; //2022-08-26
        String tempStart1 = dt.dateFormat(starttempDate);
        String tempTimeStart = (startTimestamp[1]).substring(0, 8); //16:53:09
        String temp2Start = dt.timeFormat(tempTimeStart);
        String startPeriodStr = tempStart1 + ", " + temp2Start;
        
        String[] endTimestamp = end.split("T");
        String endtempDate = endTimestamp[0]; //2022-08-26
        String tempEnd1 = dt.dateFormat(endtempDate);
        String tempTimeEnd = (endTimestamp[1]).substring(0, 8); //16:53:09
        String temp2End = dt.timeFormat(tempTimeEnd);
        String endPeriodStr = tempEnd1 + ", " + temp2End;

        // DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX");
        // Date startP = null;
        // Date endP = null;
        // try {
        //     startP = df.parse(start);  

        //     endP = df.parse(end);  

        // } catch (ParseException e) {
        //     e.printStackTrace();
        // }
        // Date[] date = {startP, endP};
        // // return date;

        // //Date formatting
        //     //start
        // String[] startPeriod = (startP + "").split(" "); 
        // //Fri Aug 26 06:00:00 SGT 2022
        // String ampm = "";
        // if(Integer.parseInt(startPeriod[3].substring(0,2)) < 12){
        //     ampm = "am";
        // } else {
        //     ampm = "pm";
        //     if(Integer.parseInt(startPeriod[3].substring(0,2)) >= 13){
        //         startPeriod[3] = (Integer.parseInt(startPeriod[3].substring(0,2)) - 12) + startPeriod[3].substring(2);
        //     }
        // }
        // String startReturn = startPeriod[0] + "day, " + startPeriod[2] + " " + startPeriod[1] + " " + startPeriod[5] + " " + startPeriod[3] + ampm; 

        //     //end
        // String[] endPeriod = (endP + "").split(" "); 
        // //Fri Aug 26 06:00:00 SGT 2022
        // String ampm2;
        // if(Integer.parseInt(endPeriod[3].substring(0,2)) < 12){
        //     ampm2 = "am";
        // } else {
        //     ampm2 = "pm";
        //     if(Integer.parseInt(endPeriod[3].substring(0,2)) >= 13){
        //         endPeriod[3] = (Integer.parseInt(endPeriod[3].substring(0,2)) - 12) + endPeriod[3].substring(2);
        //     }
        // }
        // String endReturn = endPeriod[0] + "day, " + endPeriod[2] + " " + endPeriod[1] + " " + endPeriod[5] + " " + endPeriod[3] + ampm2;
        String[] returnDate = {startPeriodStr, endPeriodStr};
        return returnDate;
        //Date formatting
    }

    @GetMapping("/showWeather")
    public String showWeather(@RequestParam String country, Model model) {
        egg egg = new egg(country);
        weatherObj weatherObj = egg.getWeather(egg.coordinates);

        // System.out.println("IN GETMAPPING: " + weatherObj.getWeather().get(0));
        // System.out.println("weatherObj: " + weatherObj.getMain());
        HashMap weatherMap = new HashMap<>();
        weatherMap = (HashMap)weatherObj.getWeather().get(0);

        double temp = (double)weatherObj.getMain().get("temp");
        temp = temp - 273.15;

        String icon = (String)weatherMap.get("icon");
        String weatherNow =(String)weatherMap.get("main");
        // String temp = (String)mainMap.get("temp");

        model.addAttribute("weather", weatherObj);
        model.addAttribute("weatherNow", weatherNow);
        model.addAttribute("icon", icon);
        model.addAttribute("temp", temp);
        return "showWeather";
    }

    @PostMapping("showTrafficCam")
    public String showTrafficCam(Model model,
                                @ModelAttribute options options,
                                @RequestParam String imgSize) {
        // System.out.println("TEST: " + options.getOptionNearbyLocations() + options.getOption() + options.getOptions());

        egg egg = new egg();
        HashMap camera = egg.getTrafficImage(options.getOption());
        
        List<options> optionList = getCameraList();
        //getting nearby locations
        String nearbyLocations = "No nearby location data found.";
        for (int i = 0; i < optionList.size(); i++) {
            if(optionList.get(i).getOption().trim().equals(options.getOption().trim())){
                nearbyLocations = optionList.get(i).getOptionNearbyLocations();
            }
        }

        
        // //Date formatting
        // String[] a = (date[0] + "").split(" "); 
        // //Fri Aug 26 06:00:00 SGT 2022
        // String ampm;
        // if(Integer.parseInt(a[3].substring(0,2)) < 12){
        //     ampm = "am";
        // } else {
        //     ampm = "pm";
        //     if(Integer.parseInt(a[3].substring(0,2)) >= 13){
        //         a[3] = (Integer.parseInt(a[3].substring(0,2)) - 12) + a[3].substring(2);
        //     }
        // }
        // String dt =a[0] + "day, " + a[2] + " " + a[1] + " " + a[5] + " " + a[3] + ampm; 
        // //Date formatt

        // 2022-08-26T16:53:09+08:00
        if((String)camera.get("timestamp") != null) {
            String timestamp = (String)camera.get("timestamp");
            String[] tempTimestamp = timestamp.split("T");
            String tempDate = tempTimestamp[0]; //2022-08-26
            dateTimeService dt = new dateTimeService();
            String temp = dt.dateFormat(tempDate);

            String tempTime = (tempTimestamp[1]).substring(0, 8); //16:53:09
            String temp2 = dt.timeFormat(tempTime);
            timestamp = temp + ", " + temp2;
            model.addAttribute("timestamp", timestamp);
        } 

        model.addAttribute("height", imgSize);
        model.addAttribute("image", camera.get("image"));
        model.addAttribute("camLocation", options.getOption().trim());
        model.addAttribute("nearbyLocations", nearbyLocations);
        model.addAttribute("lat", camera.get("lat"));
        model.addAttribute("lon", camera.get("long"));
        model.addAttribute("apikey", googApiKey);

        //Shows traffic map of Causeway if cameras are 2701 or 2702
        model.addAttribute("checkpoint", options.getOption().trim());
        model.addAttribute("ciq", options.getOption().trim());

        //service.deleteAll(); //works
        // System.out.println(options.getOption());
        if(options.getOption().equals("0")){
            model.addAttribute("message", "Error: No option selected!");
        }
        return "showTrafficCam";
    }

    //Helper class to get a list of options that users choose from website.
    public List<options> getCameraList() {
        FileReader fReader = null;
        List<String> options = new ArrayList<>();
        StringBuilder sb = new StringBuilder();

        //Read from file
        try {
            File myObj = new File("./src/main/resources/static/egg.txt");
            fReader = new FileReader(myObj);
            int i;
            while(((i=fReader.read()) != -1)) {
                sb.append((char)i);
            }

            fReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String[] terms = sb.toString().split("~");
        List<options> optionList = new ArrayList<>();

        for (int i = 0; i < terms.length; i++) {
            String[] temp = terms[i].split("Nearby Locations:");
            optionList.add(i, new options());
            optionList.get(i).setOption(temp[0]);
            optionList.get(i).setOptionNearbyLocations(temp[1]);
        }
        return optionList;
    }

    @RequestMapping("dashboard")
    public String getDashboard(Model model) {
        egg egg = new egg();

        //Populating option list with image URLs and timestamp
        List<options> optionList = getCameraList();
        HashMap camera = egg.getTrafficImage("test");
        ArrayList<HashMap> map = (ArrayList<HashMap>)camera.get("cameras"); 
        for (int i = 0; i < map.size(); i++) {
            for (int j = 0; j < map.size(); j++) {
                if(optionList.get(i).getOption().contains((String)map.get(j).get("camera_id"))){
                    optionList.get(i).setImgUrl((String)map.get(j).get("image"));
                    optionList.get(i).setTimestamp((String)map.get(j).get("timestamp"));
                }
            }
            // System.out.println(optionList.get(i).getImgUrl());
        }
        List<options> option1 = new ArrayList<>();
        List<options> option2 = new ArrayList<>();
        for (int i = 0; i < optionList.size(); i++) {
            String[] date = new String[2];
            if(i < 43){
                if(optionList.get(i).getTimestamp() != null || optionList.get(i).getImgUrl() != null){
                    date = getStartandEnd(optionList.get(i).getTimestamp(), optionList.get(i).getTimestamp());
                    optionList.get(i).setTimestamp(date[0]);
                }
                option1.add(optionList.get(i));
            } else {
                if(optionList.get(i).getTimestamp() != null || optionList.get(i).getImgUrl() != null){
                    date = getStartandEnd(optionList.get(i).getTimestamp(), optionList.get(i).getTimestamp());
                    optionList.get(i).setTimestamp(date[0]);
                }
                option2.add(optionList.get(i));
            }
        }

        List<String> valuesFromCheckbox = new ArrayList<>();
        model.addAttribute("id", valuesFromCheckbox);
        model.addAttribute("options1", new options(option1));
        model.addAttribute("options2", new options(option2));
        return "dashboard"; 
    }

    @PostMapping("dashboardSave")
    public String goDBS (Model model,
                @RequestParam("id") List<String> valuesFromCheckbox,
                @RequestParam("username") String username,
                @ModelAttribute options options) {
                
                //Service object
                egg egg = new egg();

                //Get list of cameras from API
                List<options> optionList = getCameraList();
                
                //Populating dropdown with all cameras
                model.addAttribute("options", new options(optionList));

                //Get list of all cameras. Make an array of cameras
                HashMap camera = egg.getTrafficImage("test");
                ArrayList<HashMap> cameras = (ArrayList<HashMap>)camera.get("cameras");
                
                //Populating option list with image URLs
                for (int i = 0; i < cameras.size(); i++) {
                    for (int j = 0; j < cameras.size(); j++) {
                        if(optionList.get(i).getOption().contains((String)cameras.get(j).get("camera_id"))){
                            optionList.get(i).setImgUrl((String)cameras.get(j).get("image"));
                            String[] date = getStartandEnd((String)cameras.get(j).get("timestamp"), (String)cameras.get(j).get("timestamp"));
                            optionList.get(i).setTimestamp(date[0]);
                            // optionList.get(i).setTimestamp((String)cameras.get(j).get("timestamp"));
                        }
                    }
                }

                //Only adding options from checkbox to webpage
                List<options> userCameras = new ArrayList<>();
                for (int i = 0; i < optionList.size(); i++) {
                    for (int j = 0; j < valuesFromCheckbox.size(); j++) {
                        if((valuesFromCheckbox.get(j)).trim().equals((optionList.get(i).getOption()).trim())) {
                            userCameras.add(optionList.get(i));
                        }
                    }                    
                }

                //If username exists, do not save new options and display existing ones
                //Else If username exists but contents are empty, populate username with new options
                ArrayList<String> keys = service.getKeys();
                if(!keys.contains(username)) {
                    options opt = new options(userCameras);
                    service.saveOptions(username, opt);
                } else if (service.getOptions(username).getOption() == null & keys.contains(username)) { 
                    options opt = new options(userCameras);
                    service.saveOptions(username, opt);
                } 
                // else if(keys.contains(username)){
                //     options currentOptions = service.getOptions(username);
                //     List<options> optionsArray = currentOptions.getOptions();
                //     List<options> optionsArray2 = new ArrayList<>();

                //     for (options option : optionsArray) { //For each current option
                //         for (int i = 0; i < optionList.size(); i++){
                //             if(optionList.get(i).getOption().equals(option.getOption())){
                //                 optionsArray2.add(optionList.get(i));
                //             }
                //         }
                //     }
                //     service.saveOptions(username, new options(optionsArray2));
                // }

                //Get data from Redis
                options gotOption = service.getOptions(username);

                model.addAttribute("username", username);
                model.addAttribute("userCameras", new options(gotOption.getOptions()));
                return "User";
    }

    @RequestMapping("dashboardSave/{username}")
    public String getUser(Model model, @PathVariable String username, @ModelAttribute options options, @RequestParam("details") Optional<String> details) {
        
        options gotOption = service.getOptions(username);
        List<options> optionsArray = gotOption.getOptions();

        //If user added a camera
        if(details.isPresent()){
            boolean flag = false;
            for (options each : optionsArray) {
                if(each.getOption().equals(options.getOption()))
                    flag = true; //If flag is true, option to add already exists.
            }
            if(flag == false){
                optionsArray.add(options);
            } else {
                model.addAttribute("message", "Error: Camera ID " + options.getOption() + " is already present.");
            }
        }

        if(!(options.getDelete() == null)){ //Delete button pressed
            for (int i = 0; i < optionsArray.size(); i++) {
                if(optionsArray.get(i).getOption().equals(options.getOption())){
                    optionsArray.remove(i);
                }
            }
        } else if (!(options.getSortup() == null)) { //Sort up button pressed
            for (int i = 0; i < optionsArray.size(); i++) {
                if(optionsArray.get(i).getOption().equals(options.getOption())){
                    if(i > 0){
                        options temp = optionsArray.get(i-1);
                        optionsArray.set(i-1, optionsArray.get(i));
                        optionsArray.set(i, temp);
                    }
                }
            }
        }

        //Service object
        egg egg = new egg();

        //Get list of cameras from API
        List<options> optionList = getCameraList();

        //Populating dropdown with all cameras
        model.addAttribute("options", new options(optionList));

        //Get list of all cameras. Make an array of cameras
        HashMap camera = egg.getTrafficImage("test");
        ArrayList<HashMap> cameras = (ArrayList<HashMap>)camera.get("cameras");

        //Populating option list with image URLs
        for (int i = 0; i < cameras.size(); i++) {
            for (int j = 0; j < cameras.size(); j++) {
                if(optionList.get(i).getOption().contains((String)cameras.get(j).get("camera_id"))){
                    optionList.get(i).setImgUrl((String)cameras.get(j).get("image"));
                    String[] date = getStartandEnd((String)cameras.get(j).get("timestamp"), (String)cameras.get(j).get("timestamp"));
                    optionList.get(i).setTimestamp(date[0]);
                    // optionList.get(i).setTimestamp((String)cameras.get(j).get("timestamp"));
                }
            }
        }

        //Changing date to readable format, updating image and timestamp to latest data
        for (options option : optionList) {
            for (options oaOption : optionsArray) {
                if(oaOption.getOption().trim().equals(option.getOption().trim())){
                    if(option.getTimestamp() != null & option.getImgUrl() != null){
                        oaOption.setImgUrl(option.getImgUrl()); //image
                        oaOption.setTimestamp(option.getTimestamp());    //timestamp
                    }
                }
            }
        }

        gotOption.setOptions(optionsArray);
        service.saveOptions(username, gotOption);

        model.addAttribute("username", username);
        model.addAttribute("userCameras", new options(gotOption.getOptions()));
        return "User";
    }

    // @PostMapping("gogSubmit")
    // public String gogSubmit(Model model,
    //                         @ModelAttribute options options,
    //                         @RequestParam("details") Boolean details,
    //                         @RequestParam String imgSize) {
    //     List<options> optionList = getCameraList();
    //     System.out.println(details);
    //     System.out.println(options.getOption().trim());
    //     System.out.println(options.toString());
    //     model.addAttribute("height", imgSize);

    //     return "showTrafficCam";
    // }
}