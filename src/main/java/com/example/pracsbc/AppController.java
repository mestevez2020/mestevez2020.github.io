package com.example.pracsbc;


import com.example.pracsbc.service.Endpoint;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class AppController implements ErrorController {

    @Autowired
    private Endpoint service;

    @GetMapping("/")
    public String menu() {
        return "home";
    }

    @GetMapping("/index")
    public String prueba() {
        service.show();
        return "home";
    }

    @ExceptionHandler
    public String handleException(Model model, Exception ex, HttpServletRequest request) {
        return "home";
    }
}