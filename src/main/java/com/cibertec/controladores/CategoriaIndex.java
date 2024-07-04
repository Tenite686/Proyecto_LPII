package com.cibertec.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cibertec.modelos.Categoria;
import com.cibertec.repositorio.CategoriaRepository;

@Controller
public class CategoriaIndex {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @GetMapping("/buscarCategoria")
    public String doGET() {
        return "vistas/Categoria/index"; // Ruta de la página que se desea cargar
    }

    @PostMapping("/buscarCategoria")
    public String doPOST(@RequestParam("txtNombreBuscar") String nombreBuscar, Model model) {
        List<Categoria> listaCategorias = categoriaRepository.buscarPorNombre(nombreBuscar);
        model.addAttribute("listaCategorias", listaCategorias); // Enviamos toda la lista de categorías
        model.addAttribute("nombreBuscado", nombreBuscar); // Y también el nombre buscado para dejarlo en su mismo <input>
        return "vistas/Categoria/index"; // Ruta de la página que se desea cargar
    }
}
