function initMap(){
    
    map = new google.maps.Map(document.getElementById("map"), {
        mapId: "cb4462ae0d7b039a",
        //1.355678, 103.818053
        center: { lat: 1.355678, lng: 103.818053 },
        zoom: 11,
      });

    var markersArray = [];

    const markers =[
     [       1.424493,
            103.817698,
            "North",
            northSg
    ],
    [   1.287537,
        103.820853,
        "South",
        southSg
    ],
    [   1.352554, 
        103.818760,
        "Central",
        centralSg
    ],
    [   1.352969, 
        103.921486,
        "East",
        eastSg
    ],
    [   1.357938,
        103.716861,
        "West",
        westSg
    ]
    ]

    for(let i = 0; i < markers.length; i++){
        const currMarker = markers[i];

        const marker= new google.maps.Marker({
            position: { lat: currMarker[0] , lng: currMarker[1] },
            map,
            title: currMarker[2],
            icon: {
                url: currMarker[3]
            },
            animation: google.maps.Animation.DROP
            });

        markersArray.push(marker);
    }

    //Traffic Cam icons
    var trafficCameras = [];
    var trafficMarks = [];
    for(let i = 0; i < trafficCams.length; i++){
        trafficCameras.push([trafficCams[i].camId,
                          trafficCams[i].lat,
                          trafficCams[i].long,
                          trafficCams[i].image,
                          trafficCams[i].timestamp]);
    }
    for(let i = 0; i < trafficCameras.length; i++){
        const currMarker = trafficCameras[i];
        var latitude = parseFloat(currMarker[1]);
        var longitude = parseFloat(currMarker[2]);
        const marker= new google.maps.Marker({
            position: { lat: latitude , lng: longitude },
            map,
            title: currMarker[0],
            icon: {
                url: "trafficSmall.png",
            },
            animation: google.maps.Animation.DROP,
            visible: false,
            });
            trafficMarks.push(marker);
    }

    //Initialize array for Icons for each neighbourhood
    var shorttermMarkersArray = [];
    var stMarkersVisible = [];
    for(let i = 0; i < 1; i++){
        for(let j = 0; j < 47; j++){
        // console.log(locationForecast[i].forecasts[j].area);
        // console.log(locationForecast[i].forecasts[j].forecast);
        // console.log(locationForecast[i].forecasts[j].url);
        // console.log(locationMetadata[j].label_location.latitude);
        // console.log(locationMetadata[j].label_location.longitude);
        // console.log("----");
        shorttermMarkersArray.push([locationForecast[i].forecasts[j].area, 
                                    locationForecast[i].forecasts[j].forecast,
                                    locationMetadata[j].label_location.latitude,
                                    locationMetadata[j].label_location.longitude,
                                    locationForecast[i].forecasts[j].url]);
        }
    }

    //Icons for each neighbourhood
    for(let i = 0; i < shorttermMarkersArray.length; i++){

        const currMarker = shorttermMarkersArray[i];
        const marker= new google.maps.Marker({
            position: { lat: currMarker[2] , lng: currMarker[3] },
            map,
            title: currMarker[0]+ ", " + currMarker[1],
            icon: {
                url: currMarker[4],
            },
            animation: google.maps.Animation.DROP,
            visible: false,
            size: "1, 1"
            });
            stMarkersVisible.push(marker);
    }

    google.maps.event.addListener(map, 'zoom_changed', function() {
        
        zoomLevel = map.getZoom();
        if (zoomLevel > 11) {
            for(let i = 0; i < markersArray.length; i++){
                const currMarker = markersArray[i];
                currMarker.setVisible(false);
            }

            for(let i = 0; i < stMarkersVisible.length; i++){
                const currMarker = stMarkersVisible[i];
                currMarker.setVisible(true);
                }
            
            for(let i = 0; i < trafficMarks.length; i++){
                const currMarker = trafficMarks[i];
                currMarker.setVisible(true);
            }
        } else {
            for(let i = 0; i < markersArray.length; i++){
                const currMarker = markersArray[i];
                currMarker.setVisible(true);
            }

            for(let i = 0; i < stMarkersVisible.length; i++){
                const currMarker = stMarkersVisible[i];
                currMarker.setVisible(false);
            }
            
            for(let i = 0; i < trafficMarks.length; i++){
                    const currMarker = trafficMarks[i];
                    currMarker.setVisible(false);
            }
        }
    });

    // Create the DIV to hold the control.
    const centerControlDiv = document.createElement("div");
    // Create the control.
    const centerControl = createCenterControl(map);
    // Append the control to the DIV.
    centerControlDiv.appendChild(centerControl);

    map.controls[google.maps.ControlPosition.TOP_CENTER].push(centerControlDiv);


    const sg = { lat: 1.352554, lng: 103.818760 };

    //https://developers.google.com/maps/documentation/javascript/examples/control-custom
function createCenterControl(map) {
    const controlButton = document.createElement("button");
  
    // Set CSS for the control.
    controlButton.style.backgroundColor = "#fff";
    controlButton.style.border = "2px solid #fff";
    controlButton.style.borderRadius = "3px";
    controlButton.style.boxShadow = "0 2px 6px rgba(150,0,0,.3)";
    controlButton.style.color = "rgb(220,0,0)";
    controlButton.style.cursor = "pointer";
    controlButton.style.fontFamily = "Roboto,Arial,sans-serif";
    controlButton.style.fontSize = "16px";
    controlButton.style.lineHeight = "38px";
    controlButton.style.margin = "8px 0 22px";
    controlButton.style.padding = "0 5px";
    controlButton.style.textAlign = "center";
  
    controlButton.textContent = "Toggle Traffic Cam";
    controlButton.title = "Click to recenter the map";
    controlButton.type = "button";
  
    // Setup the click event listeners: simply set the map to Chicago.
    controlButton.addEventListener("click", () => {
        for(let i = 0; i < trafficMarks.length; i++){
            const currMarker = trafficMarks[i];
            if(currMarker.visible == true){
                currMarker.setVisible(false);
            } else {
                currMarker.setVisible(true);
            }
        }
    });
  
    return controlButton;
  }

  const contentString = '<h1>hello</h1><img src="https://www.nea.gov.sg/assets/images/icons/weather-bg/LR.png"></img>'

  

  //NSEW Large Icons - Clickable Map Icons
  for(let i = 0; i < markersArray.length; i++){
      const currMarker = markersArray[i];
      // currMarker.setVisible(false);
      const infowindow = new google.maps.InfoWindow({
        content: currMarker.title,
    });
      currMarker.addListener("click", () => {
          infowindow.open({
            anchor: currMarker,
            map,
            shouldFocus: false,
          });
        });
  }

  //Icons by neighbourhood - Clickable Map Icons
  for(let i = 0; i < stMarkersVisible.length; i++){

    const currMarker = stMarkersVisible[i];
    const infowindow = new google.maps.InfoWindow({
        content: currMarker.icon.url,
    });
      currMarker.addListener("click", () => {
          infowindow.open({
            anchor: currMarker,
            map,
            shouldFocus: false,
          });
        });
    }


    //Getting traffic camera clickable information
    console.log(trafficCameras[0]);
    for(let i = 0; i < trafficMarks.length; i++){
        const currMarker = trafficMarks[i];
            var camImg = trafficCameras[i][3];
          const infowindow = new google.maps.InfoWindow({
        height: "300px",
         content: '<div style="height:300px"><Strong>Map ID:</Strong> ' + trafficCameras[i][0] + '<br>' +
         '<img height="300px" src=' + trafficCameras[i][3] + '></img></div><br><a href="http://www.google.com">Link</a>',
     });
       currMarker.addListener("click", () => {
           infowindow.open({
             anchor: currMarker,
             map,
             shouldFocus: false,
           });
         });
    }
}



window.initMap = initMap;