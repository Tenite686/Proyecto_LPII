package com.cibertec.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cibertec.modelos.Categoria;
import com.cibertec.repositorio.CategoriaRepository;

@Controller
public class CategoriaEliminar {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @GetMapping("/eliminarCategoria")
    public String mostrarFormulario(Model model, @RequestParam("id") int idEliminar) {
        Categoria categoria = categoriaRepository.buscarPorId(idEliminar);
        model.addAttribute("categoria", categoria);
        return "vistas/categoria/eliminar";
    }

    @PostMapping("/eliminarCategoria")
    public String procesarFormulario(RedirectAttributes redirectAttrs, @RequestParam("txtId") int id) {
        categoriaRepository.desvincularProductosPorCategoriaId(id);
        categoriaRepository.eliminar(id);

        redirectAttrs.addFlashAttribute("exito", true);
        return "vistas/categoria/index";
    }
}
