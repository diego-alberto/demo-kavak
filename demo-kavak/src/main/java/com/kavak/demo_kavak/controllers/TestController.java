package com.kavak.demo_kavak.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/Test")
public class TestController {
    @GetMapping("TestController")
    public ResponseEntity<String> testController() {
        return ResponseEntity.ok("Tests Success");
    }

}
