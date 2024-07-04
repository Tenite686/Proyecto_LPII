package com.cibertec.controladores;

import java.util.List;

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

@Controller
public class VentaIndex {

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @GetMapping("/buscarVenta")
    public String mostrarPaginaBusquedaVenta(Model model) {
        model.addAttribute("filtro", "cliente"); // Valor por defecto o el último seleccionado
        return "vistas/venta/index"; // Retorna la ruta de la página de búsqueda de ventas
    }

    // Método POST para procesar la búsqueda de ventas por cliente o producto
    @PostMapping("/buscarVenta")
    public String buscarVentasPorFiltro(@RequestParam("filtro") String filtro,
                                        @RequestParam("txtBuscar") String terminoBuscar,
                                        Model model) {
        List<Venta> listaVentas = null;

        switch (filtro) {
            case "cliente":
                Cliente cliente = clienteRepository.findByNombre(terminoBuscar);
                if (cliente != null) {
                    listaVentas = ventaRepository.buscarPorCliente(cliente);
                }
                break;
            case "producto":
                Producto producto = productoRepository.findByDescripcion(terminoBuscar);
                if (producto != null) {
                    listaVentas = ventaRepository.buscarPorProducto(producto);
                }
                break;
            default:
                Cliente defaultCliente = clienteRepository.findByNombre(terminoBuscar);
                if (defaultCliente != null) {
                    listaVentas = ventaRepository.buscarPorCliente(defaultCliente);
                }
                break;
        }

        model.addAttribute("listaVentas", listaVentas); // Agrega la lista de ventas al modelo
        model.addAttribute("terminoBuscado", terminoBuscar); // Agrega el término buscado para mantenerlo en el input
        model.addAttribute("filtro", filtro); // Agrega el filtro seleccionado al modelo

        return "vistas/venta/index"; // Retorna la ruta de la página de búsqueda de ventas
    }
}
