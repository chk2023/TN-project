package com._3dhs.tnproject.main;


import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping(value = {"/", "/main"})
    public String getMainPage(){
        return "/timeline/popularlist";
    }





}
