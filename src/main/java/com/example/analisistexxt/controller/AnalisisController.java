package com.example.analisistexxt.controller;

import com.example.analisistexxt.model.Result;
import com.example.analisistexxt.service.AnalizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class AnalisisController {
    @Autowired
    AnalizeService analizeService;
    @GetMapping
    public String health(){
        return "It works!";
    }

    @PostMapping
    public Result analizeText(@RequestBody Map<String,String> map){
        return analizeService.analizeText(map.get("text"));
    }
}
