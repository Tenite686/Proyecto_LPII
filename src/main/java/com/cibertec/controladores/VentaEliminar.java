package com.cibertec.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cibertec.modelos.Venta;
import com.cibertec.repositorio.VentaRepository;

@Controller
public class VentaEliminar {
    @Autowired
    private VentaRepository ventaRepository;

    @GetMapping("/eliminarVenta")
    public String doGET(Model modelo, @RequestParam("id") int idEliminar) {
        Venta venta = ventaRepository.buscarPorId(idEliminar);
        modelo.addAttribute("venta", venta);
        return "vistas/Venta/eliminar";
    }

    @PostMapping("/eliminarVenta")
    public String doPOST(RedirectAttributes redirectAttrs,Model modelo, @RequestParam("txtId") int id) {
        ventaRepository.eliminar(id);
        
        redirectAttrs.addFlashAttribute("exito", true);

        return "vistas/Venta/index";
    }
}
