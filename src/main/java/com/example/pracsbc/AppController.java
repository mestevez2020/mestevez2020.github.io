package com.example.pracsbc;


import com.example.pracsbc.entity.Actor;
import com.example.pracsbc.entity.Ciudad;
import com.example.pracsbc.service.Endpoint;
import com.example.pracsbc.entity.Pelicula;
import com.example.pracsbc.entity.Director;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String ObtainMovies(Model model, @RequestParam String genre) {
        List<Pelicula> pelicula = Endpoint.peliculas(genre);
        model.addAttribute("peliculas", pelicula);
        model.addAttribute("num", pelicula.toArray().length);
        return "movies";
    }
    @GetMapping("/moviesByTitle")
    public String ObtainMoviesBytitle(Model model, @RequestParam String title) {
        System.out.println(title);
        List<Pelicula> pelicula = Endpoint.peliculasBytitle(title);
       model.addAttribute("peliculas", pelicula);
        model.addAttribute("num", pelicula.toArray().length);
        return "movies";
    }



    @PostMapping("/informacionDirector")
    public String InformacionDirectorPost(Model model, @RequestParam String director) {
        Director direct = Endpoint.informacionDirector(director);
        model.addAttribute("director", direct);
        return "movies";
    }

    @PostMapping("/actor")
    public String InformacionActor(Model model, @RequestParam String actor) {
        Actor act=Endpoint.informacionActor(actor);
        model.addAttribute("actor", act);
        System.out.println("actor:"+actor);

        return "movies";
    }
    @GetMapping("/ciudad")
    public String Ciudad(Model model, @RequestParam String ciudad) {
        Ciudad ciu=Endpoint.Ciudad(ciudad);
        model.addAttribute("ciudad", ciu);
        model.addAttribute("num", 1);
        return "movies";
    }

    @ExceptionHandler
    public String handleException(Model model, Exception ex, HttpServletRequest request) {
        return "home";
    }
}