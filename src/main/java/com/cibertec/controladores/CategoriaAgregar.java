package com.cibertec.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cibertec.modelos.Categoria;
import com.cibertec.repositorio.CategoriaRepository;

@Controller
public class CategoriaAgregar {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @GetMapping("/agregarCategoria")
    public String doGET(Model modelo) {
        Categoria categoria = new Categoria();
        modelo.addAttribute("categoria", categoria);
        return "vistas/Categoria/agregar"; // Ruta sin la extensión del archivo
    }

    @PostMapping("/agregarCategoria")
    public String doPOST(Model modelo,
                         @RequestParam("txtNombre") String nombre) {
        Categoria nuevaCategoria = new Categoria();
        nuevaCategoria.setNombre(nombre);

        categoriaRepository.save(nuevaCategoria); // Guardar la nueva categoría en la base de datos

        modelo.addAttribute("categoria", nuevaCategoria);
        return "vistas/Categoria/agregar"; // Ruta sin la extensión del archivo
    }
}
