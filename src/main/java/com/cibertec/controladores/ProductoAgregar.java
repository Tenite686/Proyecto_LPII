package com.cibertec.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cibertec.modelos.Producto;
import com.cibertec.modelos.Categoria;
import com.cibertec.repositorio.ProductoRepository;
import com.cibertec.repositorio.CategoriaRepository;

@Controller
public class ProductoAgregar {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @GetMapping("/agregarProducto")
    public String mostrarFormulario(Model model) {
        Producto producto = new Producto();
        model.addAttribute("producto", producto);

        // Obtener la lista de categorías para seleccionar en el formulario
        Iterable<Categoria> categorias = categoriaRepository.findAll();
        model.addAttribute("categorias", categorias);

        return "vistas/producto/agregar";
    }

    @PostMapping("/agregarProducto")
    public String procesarFormulario(Model model,
                                     @RequestParam("codigo") String codigo,
                                     @RequestParam("descripcion") String descripcion,
                                     @RequestParam("precio") double precio,
                                     @RequestParam("stock") int stock,
                                     @RequestParam("categoria") int idCategoria) {

        Categoria categoria = categoriaRepository.findById(idCategoria)
                .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada"));

        Producto producto = new Producto();
        producto.setCodigo_inventario(codigo);
        producto.setDescripcion(descripcion);
        producto.setPrecio(precio);
        producto.setStock(stock);
        producto.setCategoria(categoria);

        productoRepository.save(producto);

        // Obtener el nuevo ID del producto agregado
        int nuevoId = producto.getId_producto();

        model.addAttribute("producto", producto);
        model.addAttribute("exito", true);

        // Obtener la lista de categorías para seleccionar en el formulario
        Iterable<Categoria> categorias = categoriaRepository.findAll();
        model.addAttribute("categorias", categorias);

        return "vistas/producto/agregar";
    }
}
