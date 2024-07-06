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

        // Agregar los resultados y los parámetros de búsqueda al modelo
        model.addAttribute("clientes", clientes);
        model.addAttribute("nombre", nombre); // Mantener el valor de nombre en la vista
        model.addAttribute("direccion", direccion); // Mantener el valor de direccion en la vista
        model.addAttribute("num_ruc", num_ruc); // Mantener el valor de num_ruc en la vista
        model.addAttribute("telefono", telefono); // Mantener el valor de telefono en la vista
        return "vistas/cliente/consultar";
    }
}
