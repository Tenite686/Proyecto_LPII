package com.cibertec.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cibertec.modelos.Venta;
import com.cibertec.modelos.Cliente;
import com.cibertec.repositorio.VentaRepository;
import com.cibertec.repositorio.ClienteRepository;

@Controller
public class VentaDetalle{

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping("/detalleVenta")
    public String mostrarDetalleVenta(@RequestParam("id") Integer ventaId, Model model) {
        // Obtener la venta por su ID
        Venta venta = ventaRepository.findById(ventaId)
                                     .orElseThrow(() -> new IllegalArgumentException("Venta no encontrada: " + ventaId));
        
        // Obtener el cliente asociado a la venta
        Cliente cliente = clienteRepository.findById(venta.getCliente().getId_cliente())
                                           .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado para la venta: " + ventaId));
        
        // Agregar venta y cliente al modelo
        model.addAttribute("venta", venta);
        model.addAttribute("cliente", cliente);
        
        // Retornar la vista para mostrar detalles
        return "vistas/Venta/detalle";
    }
}
