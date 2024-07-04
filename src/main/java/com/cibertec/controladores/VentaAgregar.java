package com.cibertec.controladores;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cibertec.modelos.Venta;
import com.cibertec.modelos.Cliente;
import com.cibertec.modelos.Producto;
import com.cibertec.repositorio.VentaRepository;
import com.cibertec.repositorio.ClienteRepository;
import com.cibertec.repositorio.ProductoRepository;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class VentaAgregar {

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @GetMapping("/agregarVenta")
    public String mostrarFormulario(Model model) {
        Venta venta = new Venta();
        model.addAttribute("venta", venta);

        // Obtener la lista de clientes y productos para seleccionar en el formulario
        Iterable<Cliente> clientes = clienteRepository.findAll();
        Iterable<Producto> productos = productoRepository.findAll();
        model.addAttribute("clientes", clientes);
        model.addAttribute("productos", productos);

        return "vistas/venta/agregar";
    }

    @PostMapping("/agregarVenta")
    public String procesarFormulario(Model model,
                                     @RequestParam("id_cliente") int idCliente,
                                     @RequestParam("id_producto") int idProducto,
                                     @RequestParam("fecha") String fecha) {

        Cliente cliente = clienteRepository.findById(idCliente)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));
        Producto producto = productoRepository.findById(idProducto)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));

        Venta venta = new Venta();
        venta.setCliente(cliente);
        venta.setProducto(producto);

        // Convertir la cadena fecha a Timestamp
        Timestamp timestampFecha;
        try {
            // Formato esperado para la fecha
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date parsedDate = dateFormat.parse(fecha);
            timestampFecha = new Timestamp(parsedDate.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            // Manejo de errores en caso de que la cadena de fecha no pueda ser parseada
            return "redirect:/agregarVenta?error=formato_fecha_invalido";
        }

        venta.setFecha(timestampFecha);

        ventaRepository.save(venta);

        // Obtener el nuevo ID de la venta agregada
        int nuevoId = venta.getId_venta();

        model.addAttribute("venta", venta);
        model.addAttribute("exito", true);

        // Obtener la lista de clientes y productos para seleccionar en el formulario
        Iterable<Cliente> clientes = clienteRepository.findAll();
        Iterable<Producto> productos = productoRepository.findAll();
        model.addAttribute("clientes", clientes);
        model.addAttribute("productos", productos);

        return "vistas/venta/agregar";
    }
}
