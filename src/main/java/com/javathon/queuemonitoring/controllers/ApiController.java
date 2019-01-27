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

    /**
     * Returns list of places nearby
     * @param lat user latitude
     * @param lon user longitude
     */
    @GetMapping("get/all")
    public AllPlacesResponse getAllPlaces(@RequestParam(value = "lat") double lat,
                                          @RequestParam(value = "lon") double lon){
        return app.getAllPlaces(lat, lon);
    }

    /**
     * Update queue information for some place
     * @param id place id
     * @param userSize new userSize of peoples in queue
     */
    @GetMapping("update/information")
    public UpdateResponse updateInformation(@RequestParam(value = "id") long id,
                                            @RequestParam(value = "user_size") int userSize){
        return app.updateInformation(id, userSize);
    }

    @GetMapping("update/user/come")
    public SuccessResponse updateUserCome(@RequestParam(value = "id") long id){
        return app.updateUserCome(id);
    }

    /**
     * Calculate time to go and predict queue size for some place
     * @param id place id
     * @param lat user latitude
     * @param lon user longitude
     */
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
