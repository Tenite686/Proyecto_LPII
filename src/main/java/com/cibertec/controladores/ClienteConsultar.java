package com.cibertec.controladores;


import com.cibertec.modelos.Cliente;
import com.cibertec.repositorio.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ClienteConsultar {


    @Autowired
   private ClienteRepository clienteRepository;

    @GetMapping("/consultarCliente")
    public String buscarClientesPorParametros(
            @RequestParam(name = "nombre", required = false, defaultValue = "") String nombre,
            @RequestParam(name = "direccion", required = false, defaultValue = "") String direccion,
            @RequestParam(name = "num_ruc", required = false, defaultValue = "") String num_ruc,
            @RequestParam(name = "telefono", required = false, defaultValue = "") String telefono,
            Model model) {

        List<Cliente> clientes = clienteRepository.listaCompleja(
                nombre + "%",
                direccion + "%",
                num_ruc + "%",
                telefono + "%");

        model.addAttribute("clientes", clientes);
        model.addAttribute("nombre", nombre); 
        model.addAttribute("direccion", direccion);
        model.addAttribute("num_ruc", num_ruc); 
        model.addAttribute("telefono", telefono);
        return "vistas/cliente/consultar";
    }
    
}
