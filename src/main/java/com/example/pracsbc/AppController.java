package com.example.pracsbc;


import com.example.pracsbc.service.Endpoint;
import com.example.pracsbc.entity.pelicula;


import jakarta.servlet.http.HttpServletRequest;
import org.apache.jena.base.Sys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


@Controller
public class AppController implements ErrorController {

    @Autowired
    private Endpoint service;


    @GetMapping("/")
    public String menu() {
        return "home";
    }

    @GetMapping("/index")
    public String prueba(Model model) {
        List<pelicula> peliculas = Endpoint.peliculas();
        for(pelicula pel : peliculas){
            System.out.println(pel.getName());
        }

        model.addAttribute("peliculas", peliculas);
        //service.show();
        return "home";
    }

    @ExceptionHandler
    public String handleException(Model model, Exception ex, HttpServletRequest request) {
        return "home";
    }
}