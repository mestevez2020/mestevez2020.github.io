package com.example.pracsbc;


import com.example.pracsbc.service.Endpoint;
import com.example.pracsbc.entity.pelicula;


import jakarta.servlet.http.HttpServletRequest;
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
        return "init";
    }

    @GetMapping("/movies")
    public String ObtainMovies(Model model) {
        List<pelicula> pelicula = Endpoint.peliculas();
        for(com.example.pracsbc.entity.pelicula pel : pelicula){
            System.out.println(pel.getName());
        }
        model.addAttribute("peliculas", pelicula);
        return "movies";
    }

    @ExceptionHandler
    public String handleException(Model model, Exception ex, HttpServletRequest request) {
        return "home";
    }
}