package com.javathon.queuemonitoring.controllers;

import com.javathon.queuemonitoring.controllers.responses.UpdateResponse;
import com.javathon.queuemonitoring.model.App;
import com.javathon.queuemonitoring.controllers.responses.AllPlacesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
public class ApiController {

    private App app;

    @Autowired
    public ApiController(App app) {
        this.app = app;
    }

    @GetMapping("get_all")
    public AllPlacesResponse getAllPlaces(){
        return app.getAllPlaces();
    }

    @GetMapping("/update")
    public UpdateResponse updateInformation(@RequestParam(value = "id") long id,
                                            @RequestParam(value = "count") int count){
        return app.updateInformation(id, count);
    }



}
