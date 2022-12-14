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
                          trafficCams[i].timestamp,
                          trafficCams[i].name]);
    }
    for(let i = 0; i < trafficCameras.length; i++){
        const currMarker = trafficCameras[i];
        var latitude = parseFloat(currMarker[1]);
        var longitude = parseFloat(currMarker[2]);
        const marker= new google.maps.Marker({
            position: { lat: latitude , lng: longitude },
            map,
            title: currMarker[5],
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
        // console.log(locationForecast[i].forecasts[j].start);
        // console.log(locationForecast[i].forecasts[j].end);
        // console.log(locationMetadata[j].label_location.latitude);
        // console.log(locationMetadata[j].label_location.longitude);
        // console.log("----");
        shorttermMarkersArray.push([locationForecast[i].forecasts[j].area, 
                                    locationForecast[i].forecasts[j].forecast,
                                    locationMetadata[j].label_location.latitude,
                                    locationMetadata[j].label_location.longitude,
                                    locationForecast[i].forecasts[j].url,
                                    locationForecast[i].forecasts[j].start,
                                    locationForecast[i].forecasts[j].end]);
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
  
    controlButton.textContent = "Toggle Traffic Cam | Recenter Map";
    controlButton.title = "Toggle between Traffic Camera and Weather";
    controlButton.type = "button";
  
    // Setup the click event listeners: toggle between showing area weather and traffic cams.
    controlButton.addEventListener("click", () => {
        var flag = true;

            for(let i = 0; i < trafficMarks.length; i++){
                const currMarker = trafficMarks[i];
                if(currMarker.visible == true){
                    currMarker.setVisible(false);
                } else {
                    currMarker.setVisible(true);
                    flag = false; //If traffic visible, weather not visible
                }
            }

        for(let i = 0; i < stMarkersVisible.length; i++){
            const currMarker = stMarkersVisible[i];
            if(flag == false){
                currMarker.setVisible(false);
            } else {
                currMarker.setVisible(true);
            }
        }

        zoomLevel = map.getZoom();
        for(let i = 0; i < markersArray.length; i++){
            const currMarker = markersArray[i];
            if(flag == false & zoomLevel <= 11){
                currMarker.setVisible(false);
            } 
        }

        map.setCenter({lat:1.355678, lng:103.818053});
    });
  
    return controlButton;
  }

  const contentString = '<h1>hello</h1><img src="https://www.nea.gov.sg/assets/images/icons/weather-bg/LR.png"></img>'

  //NSEW Large Icons - Clickable Map Icons
  for(let i = 0; i < markersArray.length; i++){
      const currMarker = markersArray[i];
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

        var prev_infowindow =false; 

        google.maps.event.addListener(currMarker, 'click', function(){
            if( prev_infowindow ) {
               prev_infowindow.close();
            }
            
            prev_infowindow = infowindow;
            infowindow.open(map, currMarker);
        });
  }

  //Icons by neighbourhood - Clickable Map Icons
  for(let i = 0; i < stMarkersVisible.length; i++){

    const currMarker = stMarkersVisible[i];
    const timeMarker = shorttermMarkersArray[0];
    const infowindow = new google.maps.InfoWindow({
        content: '<p style="color:black"><strong>Validity</strong></p><p>From: ' + timeMarker[5] + '<p/><p>To: ' + timeMarker[6] + '</p>',
    });
      currMarker.addListener("click", () => {
          infowindow.open({
            anchor: currMarker,
            map,
            shouldFocus: false,
          });
        });

        var prev_infowindow =false; 

        google.maps.event.addListener(currMarker, 'click', function(){
            if( prev_infowindow ) {
               prev_infowindow.close();
            }
    
            prev_infowindow = infowindow;
            infowindow.open(map, currMarker);
        });
    }


    //Getting traffic camera clickable information
    for(let i = 0; i < trafficMarks.length; i++){
        const currMarker = trafficMarks[i];
        const timeMarker = shorttermMarkersArray[0];
        var camImg = trafficCameras[i][3];
        var substring = "Error Detected. Might be an issue with the API.";
        if(trafficCameras[i][5] == null){
            console.log(trafficCameras[i][0]);
            console.log(trafficCameras[i][1]);
            console.log(trafficCameras[i][2]);
            console.log(trafficCameras[i][3]);
            console.log(trafficCameras[i][4]);
            console.log(trafficCameras[i][5]);
            console.log("true");   
        } else{
            if(trafficCameras[i][5].length > 1){
            substring = trafficCameras[i][5].substring(6);
        } else {
            substring = "Error Detected";
        };
        }
        
        const infowindow = new google.maps.InfoWindow({
        height: "300px",
        content: '<div><h2 style="color:purple;font:sans serif">'+  substring
        + '</h2><h4 style="color:purple;font:sans serif"><Strong>Camera ID:</Strong> ' 
        + trafficCameras[i][0] + '</h4><br><p style="color:purple;font:sans serif">Timestamp: </p>' +
        trafficCameras[i][4] + '<br><img height="300px" style="border:solid 1px black" src=' + trafficCameras[i][3] 
        + '></img></div><br><br><a href="https://iconscout.com/icons/camera" target="_blank">Camera Icon</a> by <a href="https://iconscout.com/contributors/icon-mafia" target="_blank">Icon Mafia</a>'
     });
       currMarker.addListener("click", () => {
           infowindow.open({
             anchor: currMarker,
             map,
             shouldFocus: false,
           });
         });

         var prev_infowindow =false; 

        google.maps.event.addListener(currMarker, 'click', function(){
            if( prev_infowindow ) {
               prev_infowindow.close();
            }
    
            prev_infowindow = infowindow;
            infowindow.open(map, currMarker);
        });
    }
}



window.initMap = initMap;