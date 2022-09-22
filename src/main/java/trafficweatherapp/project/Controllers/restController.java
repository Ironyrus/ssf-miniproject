package trafficweatherapp.project.Controllers;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import trafficweatherapp.project.Database.redisService;
import trafficweatherapp.project.Models.options;

@RestController
public class restController {
    
    @Autowired
    redisService service;

    @GetMapping(path="/REST/{username}", produces="application/json")
    public ResponseEntity<String> getTrafficCam(@PathVariable String username, @ModelAttribute options options) {
        try {
            ArrayList<String> keys = service.getKeys();
            for (String user : keys) {
                if(username.toLowerCase().equals((user).toLowerCase())){
                    username = user;
                }
            }

            options = service.getOptions(username);
            if(options == null) {
                throw new Exception();
            }
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(options.toString());
        } catch (Exception e) {
            JsonObjectBuilder builder = Json.createObjectBuilder();
            builder.add("Error: Username not found, OR Redis connection interrupted.", "Cannot find user: " + username);
            JsonObject body = builder.build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body.toString());
        }
        
    }
}