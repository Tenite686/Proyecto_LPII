package com.cibertec.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cibertec.modelos.Cliente;
import com.cibertec.repositorio.ClienteRepository;

@Controller
public class ClienteEliminar {

    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping("/eliminarCliente")
    public String mostrarFormulario(Model model, @RequestParam("id") int idEliminar) {
        Cliente cliente = clienteRepository.buscarPorId(idEliminar);
        model.addAttribute("cliente", cliente);
        return "vistas/cliente/eliminar";
    }

    @PostMapping("/eliminarCliente")
    public ResponseEntity<?> procesarFormulario(@RequestParam("id") int id) {
        // Verificar si el cliente tiene ventas asociadas
        boolean tieneVentasAsociadas = clienteRepository.existsVentasByClienteId(id);

        if (!tieneVentasAsociadas) {
            clienteRepository.deleteById(id);
            return ResponseEntity.ok().body("{\"exito\": true}");
        } else {
            return ResponseEntity.ok().body("{\"exito\": false, \"mensaje\": \"El cliente tiene ventas asociadas y no se puede eliminar.\"}");
        }
    }
}
