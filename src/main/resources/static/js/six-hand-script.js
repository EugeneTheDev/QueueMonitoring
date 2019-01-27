var Place = function(){
    this.id =  
    this.renew_place = 
    this.open = function(){
        $.ajax({
            url:
        })
    }

    this.
}

var User = function(location, ){
    this.location = [];
    this.rendered = [];
    this.set_location = [];
    
};

$("document").ready(function () {
    var html_template = '<div class="container"> <div class="card" id="2"> <div class="row"> <div class="container"> <div class="center-xs row"> <div class="col-xs-12"> <h1> <b class="headline">Кофе Хауз</b> </h1> <p class="address">г. Москва, пр. Кутузовский, 45</p> </div> <div class="icon-block"><i class="fas fa-male fa-lg icon"></i><b>23</b> <i class="fas fa-running fa-lg icon"></i><b>3</b> </div> </div> </div> </div> <div class="row center-xs hidden-block"> <div class="btn-group"> <div class="container"> <div class="row center-xs"> <div class="col-xs-12 col-md-3"> <button class="left">В пути ( <i class="far fa-clock icon"></i>23 min ) </button> </div> <div class="col-xs-12 col-md-3"> <button class="right">На месте</button> </div> </div> </div> </div> </div> </div> </div>'
    var html_templates = [];
    var geolocation_coords;
    navigator.geolocation.getCurrentPosition((data)=>{
        geolocation_coords = {
        lat: data.coords["latitude"],
        lon: data.coords["longitude"]
    };
    console.log(geolocation_coords);
});
    

    var lastPosition = 0;
    $.ajax({
        url: "http://localhost:5000/api/get/all",
        success: function(data){
            console.log("ok");
            var content_block = $(".block-list__content");
            let html_templates = data.places.forEach(function(item){
                content_block.append('<div class="container"> <div class="card" id="'
                + item.id +
                '"> <div class="row"> <div class="container"> <div class="center-xs row"> <div class="col-xs-12"> <h1> <b class="headline">'
                 + item.name + 
                 '</b> </h1> <p class="address">'
                 + item.address +
                 '</p></div> <div class="icon-block"> <i class="fas fa-male fa-lg icon"></i><b>23</b> </div> </div> </div> </div> <div class="row center-xs hidden-block"> <div class="btn-group"> <div class="container"> <div class="row center-xs middle-xs"> <div class="col-xs-12 col-md-3"> <div class="way-shower"> <p>На путь <i class="far fa-clock icon"></i> 23 min </p> </div> <div class="way-shower"> <p>Будет 40 <i class="fas fa-male fa-lg icon"></i> </p> </div> </div> <div class="col-xs-12 col-md-3"> <button class="right">Я на месте</button> </div> </div> </div> </div> </div> </div> </div>');
            });
            console.log(html_templates);

  
        },
        error: (err)=>console.log(err)
    });

    $(".hidden-block").hide();
    $(".card").on("click", function (e) {
        let present_id = e.currentTarget.id;
        $("#" + present_id + " .hidden-block").fadeToggle();
        console.log($("#" + present_id + ".hidden-block"));

        // $(".hidden-block").fadeIn();
    });

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

