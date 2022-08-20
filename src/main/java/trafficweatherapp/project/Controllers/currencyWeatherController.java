package trafficweatherapp.project.Controllers;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;

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

import java.rmi.StubNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import trafficweatherapp.project.Models.options;
import trafficweatherapp.project.Models.weatherObj;
import trafficweatherapp.project.Services.User;
import trafficweatherapp.project.Services.egg;
import trafficweatherapp.project.Services.redisService;

@Controller
public class currencyWeatherController {

    private String currApiKey = System.getenv("FIXER_API_KEY");
    private String key = System.getenv("MYVERYOWN_API_KEY");

    @Autowired
    redisService service;

    @GetMapping("/")
    public String block(Model model) {
        
        return "preindex";
    }

    @GetMapping("/accessgranted")
    public String getHome(Model model, @RequestParam("password") String password) {
        List<options> optionList = getCameraList();
        model.addAttribute("options", new options(optionList));
        if(password.equals(key))
            return "index";
        else
            return "preindex";
    }

    @GetMapping("/showWeather")
    public String showWeather(@RequestParam String country, Model model) {
        egg egg = new egg(country);
        weatherObj weatherObj = egg.getWeather(egg.coordinates);

        System.out.println("IN GETMAPPING: " + weatherObj.getWeather().get(0));
        System.out.println("weatherObj: " + weatherObj.getMain());
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
        System.out.println("TEST: " + options.getOptionNearbyLocations() + options.getOption() + options.getOptions());

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
        model.addAttribute("height", imgSize);
        model.addAttribute("image", camera.get("image"));
        model.addAttribute("camLocation", options.getOption());
        model.addAttribute("timestamp", camera.get("timestamp"));
        model.addAttribute("nearbyLocations", nearbyLocations);
        model.addAttribute("lat", camera.get("lat"));
        model.addAttribute("lon", camera.get("long"));
        //service.save(camera.get("timestamp") + " | " + options.getOption().trim() + " | " + camera.get("image")); //works
        //service.deleteAll(); //works
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

        List<options> optionList = getCameraList();
        HashMap camera = egg.getTrafficImage("test");
        ArrayList<HashMap> map = (ArrayList<HashMap>)camera.get("cameras"); 
        for (int i = 0; i < map.size(); i++) {
            for (int j = 0; j < map.size(); j++) {
                if(optionList.get(i).getOption().contains((String)map.get(j).get("camera_id"))){
                    optionList.get(i).setImgUrl((String)map.get(j).get("image"));
                }
            }
            System.out.println(optionList.get(i).getImgUrl());
        }
        List<options> option1 = new ArrayList<>();
        List<options> option2 = new ArrayList<>();
        for (int i = 0; i < optionList.size(); i++) {
            if(i < 43){
                option1.add(optionList.get(i));
            } else {
                option2.add(optionList.get(i));
            }
        }

        model.addAttribute("options1", new options(option1));
        model.addAttribute("options2", new options(option2));
        return "dashboard"; 
    }

    @PostMapping("dashboardSave")
    public String goDBS(Model model,
                @RequestParam("id") List<String> valuesFromCheckbox,
                @ModelAttribute options options) {
                for (String string : valuesFromCheckbox) {
                    System.out.println(string.trim());
                }
                return "gog";
    }



}