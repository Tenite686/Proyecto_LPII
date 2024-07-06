package com.cibertec.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cibertec.modelos.Producto;
import com.cibertec.repositorio.ProductoRepository;

@Controller
public class ProductoEliminar {

    @Autowired
    private ProductoRepository productoRepository;

    @GetMapping("/eliminarProducto")
    public String mostrarFormulario(Model model, @RequestParam("id") int idEliminar) {
        Producto producto = productoRepository.findById(idEliminar).orElse(null);
        model.addAttribute("producto", producto);
        return "vistas/producto/eliminar";
    }

    @PostMapping("/eliminarProducto")
    public ResponseEntity<?> procesarFormulario(@RequestParam("id") int id) {
        // Verificar si el producto tiene ventas asociadas
        boolean tieneVentasAsociadas = productoRepository.existsVentasByProductoId(id);

        if (!tieneVentasAsociadas) {
            productoRepository.deleteById(id);
            return ResponseEntity.ok().body("{\"exito\": true}");
        } else {
            return ResponseEntity.ok().body("{\"exito\": false, \"mensaje\": \"El producto tiene asociaciones activas con ventas y no se puede eliminar.\"}");
        }
    }
}
