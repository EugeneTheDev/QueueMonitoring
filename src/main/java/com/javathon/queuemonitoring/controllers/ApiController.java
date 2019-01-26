package com.javathon.queuemonitoring.controllers;

import com.javathon.queuemonitoring.model.App;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiController {

    private App app;

    @Autowired
    public ApiController(App app) {
        this.app = app;
    }

    @GetMapping("get_all")
    public List<Document> getAllPlaces(){
        return app.getAllPlaces();
    }

}
