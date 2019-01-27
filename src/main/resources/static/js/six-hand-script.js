{/* <div class="row center-xs hidden-block"> <div class="btn-group"> <div class="container"> <div class="row center-xs middle-xs"> <div class="col-xs-12 col-md-3"> <div class="way-shower"> <p>На путь <i class="far fa-clock icon"></i> 23 min </p> </div> <div class="way-shower"> <p>Будет 40 <i class="fas fa-male fa-lg icon"></i> </p> </div> </div> <div class="col-xs-12 col-md-3"> <button class="right">Я на месте</button> </div> </div> </div> </div> </div> </div> </div>' */}

var renewPlaces = function(locations_list, coordinates, callback){

    $.ajax({
        url: "http://localhost:5000/api/get/all",

        method: "GET",

        data: coordinates,

        success: function(data){
            var container = $(".block-list__content:first");
            container.empty();
            
            // adding card basis
            data.places.forEach(function(item){
                locations_list.push({id:item.id, html:'<div class="container"> <div class="card" id="'
                + item.id +
                '"> <div class="row"> <div class="container"> <div class="center-xs row"> <div class="col-xs-12"> <h1> <b class="headline">'
                 + item.name + 
                 '</b> </h1> <p class="address">'
                 + item.address +
                 '</p></div> <div class="icon-block"> <i class="fas fa-male fa-lg icon"></i><b>23</b> </div> </div> </div> </div>'});
                
            });
            locations_list.forEach(function(item){
                container.append(item.html);
            });
            callback();
        },
        error: (err)=>console.log(err)
    })

}

var openPlace = function(place, coordinates){
    $.ajax({
        url: "http://localhost:5000/api/get/details",
        data: {
            id: place.id,
            lat: coordinates.lat,
            lon: coordinates.lon
        },
        success:function(data){
            let html = '<div class="row center-xs hidden-block"> <div class="btn-group"> <div class="container"> <div class="row center-xs middle-xs"> <div class="col-xs-12 col-md-3"> <div class="way-shower"> <p>На путь <i class="far fa-clock icon"></i>'
            + data.time +
            '</p> </div> <div class="way-shower"> <p>Будет '
            + data.peopleInQueue + 
            ' <i class="fas fa-male fa-lg icon"></i> </p> </div> </div> <div class="col-xs-12 col-md-3"> <button class="right">Я на месте</button> </div> </div> </div> </div> </div>'
            place.additional = html;
            place.total = place.html.slice(place.html.indexOf("row")-12) + place.additional;
            to_be_changed = $("#"+place.id);
            to_be_changed.html(place.total); 
        }
    });
}

$("document").ready(function () {
    var places = [];
    var geolocation_coords;

    navigator.geolocation.getCurrentPosition((data)=>{

        geolocation_coords = {
        lat: data.coords["latitude"],
        lon: data.coords["longitude"]
        };
        // initialising cards
        // TODO: strange result in design

        renewPlaces(places, geolocation_coords, callback=function(){

            $(".card").on("click",function (e){
                var present_id = e.currentTarget.id;
                openPlace(places.filter(function(item){
                    return item.id == present_id;
                })[0]
                , geolocation_coords);
                // $(".card").off("click");
                $(".card").on("click", function (e) {
                    let present_id = e.currentTarget.id;
                    $("#" + present_id + " .hidden-block").fadeToggle();
                });
            });
                
        });
    });
    

    var lastPosition = 0;
    
    // hiding and opening card
    $(".hidden-block").hide();

    
    $("button").on("click", function(e){
       e.stopPropagation(); 
    });


    // getting additional information about exact card
    $(".card").on("click",function (e){
        var present_id = e.currentTarget.id;
        openPlace(places.filter(function(item){
            return item[0] == present_id;
        })[0], geolocation_coords);
        $(".card").off("click");
        $(".card").on("click", function (e) {
            let present_id = e.currentTarget.id;
            $("#" + present_id + " .hidden-block").fadeToggle();
        });
    });
    

    

    // header hiding
    
    $(window).scroll((e) => {
        var newPosition = $(this).scrollTop();
        if (newPosition - lastPosition > 0) {
            $("header:first").css({
                position: "absolute"
            });
        } else {
            $("header:first").css({
                position: "fixed"
            });
        }
        lastPosition = newPosition;
    })

});

