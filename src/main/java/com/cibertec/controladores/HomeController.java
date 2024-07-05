package com.cibertec.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index() {
        return "vistas/Usuario/Login"; // Ruta relativa al archivo HTML sin extensión
    }
    @GetMapping("/index")
    public String indexPage() {
        return "index"; // Nombre de tu vista index.html
    }
    
}
