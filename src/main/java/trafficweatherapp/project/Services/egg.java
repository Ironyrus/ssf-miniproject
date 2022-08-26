package trafficweatherapp.project.Services;

import java.net.URI;
import java.net.URISyntaxException;

//https://www.baeldung.com/spring-resttemplate-json-list

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import trafficweatherapp.project.Models.geoloc;
import trafficweatherapp.project.Models.googCode;
import trafficweatherapp.project.Models.trafficCamObj;
import trafficweatherapp.project.Models.weatherObj;

public class egg {

	public String country;
	public String coordinates;
	private String googleApiKey = System.getenv("GOOGLE_API_KEY");;
	private String currApiKey;
	private String weatherApiKey = System.getenv("WEATHER_API_KEY");

	@Autowired
	geoloc geoloc;
	
	@Autowired 
	weatherObj weatherObj;

	@Autowired
	trafficCamObj trafficCamObj;

	@Autowired
	googCode googCode;

	public egg(String country) {
		this.country = country;
		coordinates = getCoords(country);
	}

	public egg(){
	}

	public String getCoords(String country) {

		//Craft out request
		RestTemplate template = new RestTemplate();
		
		//Craft out response
		// ResponseEntity<geoloc> response = template.exchange(request, geoloc.class);

		ResponseEntity<geoloc[]> responseEntity =
   			template.getForEntity("http://api.openweathermap.org/geo/1.0/direct?q=" + country + "&limit=5&appid=" + weatherApiKey,
						 geoloc[].class);
		geoloc[] objects = responseEntity.getBody();
		
		//Can also use for loop to loop through JSON object
		// for (geoloc geoloc : objects) {
		// 	System.out.println(geoloc.getCountry());
		// }

		geoloc = Arrays.stream(objects)
  				.collect(Collectors.toList()).get(0);

		// System.out.println(geoloc.getName());
		// System.out.println(geoloc.getCountry());
		// System.out.println(geoloc.getLat() + ", " + geoloc.getLon());

		// System.out.println(Arrays.stream(objects)
		// .collect(Collectors.toList()).size());
		return geoloc.getLat() + ", " + geoloc.getLon();
	}

	public weatherObj getWeather(String coords) {
		String[] coordinates = coords.split(",");
		String latitude = coordinates[0].trim();
		String longitude = coordinates[1].trim();

		RestTemplate template = new RestTemplate();
		ResponseEntity<weatherObj> responseEntity =
   			template.getForEntity(("https://api.openweathermap.org/data/2.5/weather?lat=" + latitude + "&lon=" + longitude + "&appid=" + weatherApiKey),
						 weatherObj.class);
		
		// System.out.println(responseEntity.getStatusCode());
		// System.out.println(responseEntity.getBody().getName());
		// System.out.println(responseEntity.getBody().getCoord());
		// System.out.println(responseEntity.getBody().getWeather());
		double temp = (double)responseEntity.getBody().getMain().get("temp");
		double temp2 = Double.parseDouble(temp+"");
		// System.out.println(temp2 + " degrees celcius");
		return responseEntity.getBody();
	}

	public HashMap getTrafficImage(String option) {
		String url = "https://api.data.gov.sg/v1/transport/traffic-images";
		RestTemplate template = new RestTemplate();
		ResponseEntity<trafficCamObj> responseEntity = 
			template.getForEntity(url, trafficCamObj.class);
		trafficCamObj = responseEntity.getBody().getItems().get(0);
		HashMap temp = responseEntity.getBody().getItems().get(0).getCameras().get(0);
		
		ArrayList<HashMap> cameras = new ArrayList<>();
		cameras = trafficCamObj.getCameras();
		int count = 0;
		ArrayList<HashMap> cameraHash = new ArrayList<>();
		for (HashMap camera : cameras) { //timestamp, image, location, camera_id, image_metadata
			HashMap location = (HashMap)camera.get("location");
			String latitude = location.get("latitude") + "";
			String longitude = location.get("longitude") + "";
			String camId = (String)camera.get("camera_id");
			String image = (String)camera.get("image");

			camera.put("camId", camId);
			camera.put("lat", latitude);
			camera.put("long", longitude);
			camera.put("coords", latitude+","+longitude);
			camera.put("image", image);
			cameraHash.add(camera);
			// System.out.println("Camera Id: " + camera.get("camId") + " | Timestamp: " + camera.get("timestamp") + " | Latitude: " + camera.get("lat") + " | Longitude: " + camera.get("long") + " | " + camera.get("coords") + " | image: " + image);
			count++;
		}
		// System.out.println("Images: " + count);

		for (HashMap camera : cameraHash) {
			// System.out.println(camera.get("lat") + "," + camera.get("long"));
		}

		for (HashMap camera : cameraHash) {
			if(option.contains(camera.get("camId").toString())){
				return camera; //return the whole HashMap //camId, lat, long, coords, image, timestamp
			}
		}
		HashMap map = new HashMap<>();
		map.put("cameras", cameras);
		//If option is any specific camera, return the specific camera. If not, return all cameras.
		return map;
	}

}