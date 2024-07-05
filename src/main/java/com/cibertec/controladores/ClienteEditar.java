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
public class ClienteEditar {

    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping("/editarCliente")
    public String doGET(Model modelo, @RequestParam("cod") int idEditar) {
        Cliente cliente = clienteRepository.buscarPorId(idEditar);
        modelo.addAttribute("cliente", cliente);
        return "vistas/Cliente/editar";
    }

    @PostMapping("/editarCliente")
    public String doPOST(Model modelo,
                         @RequestParam("txtId") int id,
                         @RequestParam("txtNombre") String nombre,
                         @RequestParam("txtNum_ruc") String num_ruc,
                         @RequestParam("txtDireccion") String direccion,
                         @RequestParam("txtTelefono") String telefono) {
        clienteRepository.actualizar(id, nombre, num_ruc, direccion, telefono);
        Cliente cliente = new Cliente(id, nombre, num_ruc, direccion, telefono);
        modelo.addAttribute("cliente", cliente);
        modelo.addAttribute("exito", true);

        return "vistas/Cliente/editar";
    }
}
