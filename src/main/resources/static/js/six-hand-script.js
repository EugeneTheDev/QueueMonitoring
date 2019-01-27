{/* <div class="row center-xs hidden-block"> <div class="btn-group"> <div class="container"> <div class="row center-xs middle-xs"> <div class="col-xs-12 col-md-3"> <div class="way-shower"> <p>На путь <i class="far fa-clock icon"></i> 23 min </p> </div> <div class="way-shower"> <p>Будет 40 <i class="fas fa-male fa-lg icon"></i> </p> </div> </div> <div class="col-xs-12 col-md-3"> <button class="right">Я на месте</button> </div> </div> </div> </div> </div> </div> </div>' */}



$("document").ready(function () {
    // show places by connecting to the server
    var renewPlaces = function(locations_list, coordinates, callback){

        $.ajax({
            url: "/api/get/all",
    
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
                     '</p></div> <div class="icon-block"> <i class="fas fa-male fa-lg icon"></i><b class="query">'
                     + item.queueSize +
                     '</b> </div> </div> </div> </div>'});
                    
                });
                // saves locations
                locations_list.forEach(function(item){
                    container.append(item.html);
                });
                callback();
            },
            error: (err)=>console.log(err)
        })
    
    }
    // gets additional information
    var openPlace = function(place, coordinates){
        // tells about newcomer
        $.ajax({
            url:"/api/update/user/come",
            data: {
                id: place.id
            },
            success: function(){
                console.log("joined");
                query = $("#" + place.id + " .query");
                console.log(query);
                query.text(() => 1+parseInt(query.text()));
                console.log(query.text());
            },
            error: function(err){
                console.log(err);
            }
        });

        //returns 
        $.ajax({
            url: "/api/get/details",
            data: {
                id: place.id,
                lat: coordinates.lat,
                lon: coordinates.lon
            },
            success:function(data){
                // renders the rest of the block
                let html = '<div class="row center-xs hidden-block"> <div class="btn-group"> <div class="container"> <div class="row center-xs middle-xs"> <div class="col-xs-12 col-md-3"> <div class="way-shower"> <p>На путь <i class="far fa-clock icon"></i>'
                + data.time +
                '</p> </div> <div class="way-shower"> <p>Будет '
                + data.peopleInQueue + 
                ' <i class="fas fa-male fa-lg icon"></i> </p> </div> </div> <div class="col-xs-12 col-md-3"> <button class="right"'
                + 'id="b-' + place.id + '"' + 
                '>Я на месте</button> </div> </div> <br> <form action=""> <input type="number" name="num" value="aa" placeholder="Человек в очереди"> <button class="change">Изменить</button> </form> </div> </div> </div> </div> </div>'
                place.additional = html;
                place.total = place.html.slice(place.html.indexOf("row")-12) + place.additional;
                to_be_changed = $("#"+place.id);
                to_be_changed.html(place.total); 
                // hide new forms
                $("form").hide();
                $("form").on("click", (e)=>{
                    e.stopPropagation();
                });
                
                
                // button listener
                $("button").on("click", function(e){ 
                   e.stopPropagation(); 
                   var present_id = e.currentTarget.id.slice(2); 
                   var form = $("#" + present_id + " form");
                   var input = $("#" + present_id + " form" +" input")
                   form.show();
                   const form_content = input.val();
                   var present_button = $("#" + present_id + " .change:first");
                   present_button.off("click");
      
                   present_button.on("click",function(e){
                        e.preventDefault()
                        $.ajax({
                            url: "/api/update/information",
                            data:{
                                user_size: form_content,
                                id: present_id
                            },
                            success: function(data){
                                form.hide();
                            },
                            error: function(err){
                                console.log(err);
                            }
                            
                        });
                   });
                });
            
            }
        });
    }


    var sendForm = function(){
        
    }

    var places = [];
    var geolocation_coords;
 
    navigator.geolocation.getCurrentPosition((data)=>{
        $(".cover-block").remove(); 
        
        geolocation_coords = {
        lat: data.coords["latitude"],
        lon: data.coords["longitude"]
        };

        // initialising cards
        renewPlaces(places, geolocation_coords, callback=function(){
            
            $(".card").on("click",function (e){
                var present_id = e.currentTarget.id;
                openPlace(places.filter(function(item){
                    return item.id == present_id;
                })[0]
                , geolocation_coords);
            });
            
        });
    });
    

    var lastPosition = 0;
    
    // hiding and opening card
    $(".hidden-block").hide();




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

