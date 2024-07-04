package com.cibertec.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cibertec.modelos.Categoria;
import com.cibertec.modelos.Producto;
import com.cibertec.repositorio.CategoriaRepository;
import com.cibertec.repositorio.ProductoRepository;

@Controller
public class ProductoIndex {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @GetMapping("/buscarProducto")
    public String mostrarPaginaBusquedaProducto(Model model) {
        model.addAttribute("filtro", "descripcion"); // Valor por defecto o el último seleccionado
        return "vistas/Producto/index"; // Retorna la ruta de la página de búsqueda de productos
    }

    // Método POST para procesar la búsqueda de productos por descripción o categoría
    @PostMapping("/buscarProducto")
    public String buscarProductosPorFiltro(@RequestParam("filtro") String filtro,
                                           @RequestParam("txtBuscar") String terminoBuscar,
                                           Model model) {
        List<Producto> listaProductos = null;

        switch (filtro) {
            case "descripcion":
                listaProductos = productoRepository.buscarPorDescripcion(terminoBuscar);
                break;
            case "categoria":
                Categoria categoria = categoriaRepository.findByNombre(terminoBuscar);
                if (categoria != null) {
                    listaProductos = productoRepository.buscarPorCategoria(categoria);
                }
                break;
            default:
                listaProductos = productoRepository.buscarPorDescripcion(terminoBuscar);
                break;
        }

        model.addAttribute("listaProductos", listaProductos); // Agrega la lista de productos al modelo
        model.addAttribute("descripcionBuscada", terminoBuscar); // Agrega el término buscado para mantenerlo en el input
        model.addAttribute("filtro", filtro); // Agrega el filtro seleccionado al modelo

        return "vistas/Producto/index"; // Retorna la ruta de la página de búsqueda de productos
    }
}
