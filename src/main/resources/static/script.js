function initMap(){
    map = new google.maps.Map(document.getElementById("map"), {
        mapId: "cb4462ae0d7b039a",
        center: { lat: 1.361122, lng: 103.824545 },
        zoom: 11,
      });

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
        console.log(markers[i]);
    }
}

window.initMap = initMap;