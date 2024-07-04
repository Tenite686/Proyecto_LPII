package com.cibertec.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.cibertec.modelos.Cliente;
import com.cibertec.repositorio.ClienteRepository;

@Controller
public class ClienteAgregar {

    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping("/agregarCliente")
    public String doGET(Model modelo) {
        Cliente cliente = new Cliente();
        modelo.addAttribute("cliente", cliente);
        return "vistas/Cliente/agregar"; // Ruta sin la extensión del archivo
    }

    @PostMapping("/agregarCliente")
    public String doPOST(Model modelo,
    		 @RequestParam("txtId") int id,
             @RequestParam("txtNombre") String nombre,
             @RequestParam("txtNum_ruc") String num_ruc,
             @RequestParam("txtDireccion") String direccion,
             @RequestParam("txtTelefono") String telefono) {
        clienteRepository.agregar(nombre, num_ruc, direccion, telefono);
        // Obtener el nuevo ID del cliente agregado
        Integer nuevoId = clienteRepository.nuevoId(nombre);
        Cliente cliente = new Cliente(nuevoId, nombre, num_ruc, direccion, telefono);
        modelo.addAttribute("cliente", cliente);
        return "vistas/Cliente/agregar"; // Ruta sin la extensión del archivo
    }
}
