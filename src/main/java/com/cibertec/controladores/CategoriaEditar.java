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
public class CategoriaEditar {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @GetMapping("/editarCategoria")
    public String doGET(Model modelo, @RequestParam("cod") int idEditar) {
        Categoria categoria = categoriaRepository.buscarPorId(idEditar);
        modelo.addAttribute("categoria", categoria);
        return "vistas/Categoria/editar";
    }

    @PostMapping("/editarCategoria")
    public String doPOST(Model modelo,
                         @RequestParam("txtId") int id,
                         @RequestParam("txtNombre") String nombre) {
        categoriaRepository.actualizar(id, nombre);
        Categoria categoria = new Categoria(id, nombre);
        modelo.addAttribute("categoria", categoria);
        return "vistas/Categoria/editar";
    }
}
