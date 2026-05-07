package com.stockflow.inventory_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/demo")
public class DemoController {

    @GetMapping
    public String getTestData() {
        return "Inventory Service is UP!";
    }
}
