package com.javathon.queuemonitoring.controllers;

import com.javathon.queuemonitoring.controllers.responses.*;
import com.javathon.queuemonitoring.model.App;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api")
public class ApiController {

    private App app;

    @Autowired
    public ApiController(App app) {
        this.app = app;
    }

    @GetMapping("get/all")
    public AllPlacesResponse getAllPlaces(@RequestParam(value = "lat") double lat,
                                          @RequestParam(value = "lon") double lon){
        return app.getAllPlaces(lat, lon);
    }

    @GetMapping("update/information")
    public UpdateResponse updateInformation(@RequestParam(value = "id") long id,
                                            @RequestParam(value = "user_size") int userSize){
        return app.updateInformation(id, userSize);
    }

    @GetMapping("update/user/come")
    public SuccessResponse updateUserCome(@RequestParam(value = "id") long id){
        return app.updateUserCome(id);
    }

    @GetMapping("get/details")
    public BaseResponse getDetails(@RequestParam(value = "id") int id,
                                   @RequestParam(value = "lat") double lat,
                                   @RequestParam(value = "lon") double lon){
        try {
            return app.getDetails(id, lat, lon);
        } catch (IllegalStateException e) {
            return new ErrorResponse(e.getMessage());
        }
    }
}
