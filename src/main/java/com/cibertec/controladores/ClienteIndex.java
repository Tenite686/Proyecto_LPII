package com.cibertec.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cibertec.modelos.Cliente;
import com.cibertec.repositorio.ClienteRepository;

@Controller
public class ClienteIndex {

    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping("/buscarCliente")
    public String doGET() {
        return "vistas/Cliente/index"; // Ruta de la página que se desea cargar
    }

    @PostMapping("/buscarCliente")
    public String doPOST(@RequestParam("txtNombreBuscar") String nombreBuscar, Model model) {
        List<Cliente> listaClientes = clienteRepository.buscarPorNombre(nombreBuscar);
        model.addAttribute("listaClientes", listaClientes); // Enviamos toda la lista de clientes
        model.addAttribute("nombreBuscado", nombreBuscar); // Y también el nombre buscado para dejarlo en su mismo <input>
        return "vistas/Cliente/index"; // Ruta de la página que se desea cargar
    }
}
