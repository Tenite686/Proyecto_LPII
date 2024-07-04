package com.cibertec.controladores;

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
public class ProductoEditar {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @GetMapping("/editarProducto")
    public String mostrarFormulario(Model model, @RequestParam("id") int idEditar) {
        Producto producto = productoRepository.findById(idEditar)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));

        Iterable<Categoria> categorias = categoriaRepository.findAll();
        model.addAttribute("producto", producto);
        model.addAttribute("categorias", categorias);

        return "vistas/producto/editar";
    }

    @PostMapping("/editarProducto")
    public String procesarFormulario(Model model,
                                     @RequestParam("id") int id,
                                     @RequestParam("codigo") String codigo,
                                     @RequestParam("descripcion") String descripcion,
                                     @RequestParam("precio") double precio,
                                     @RequestParam("stock") int stock,
                                     @RequestParam("categoria") int idCategoria) {

        Categoria categoria = categoriaRepository.findById(idCategoria)
                .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada"));

        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));

        producto.setCodigo_inventario(codigo);
        producto.setDescripcion(descripcion);
        producto.setPrecio(precio);
        producto.setStock(stock);
        producto.setCategoria(categoria);

        productoRepository.save(producto);

        model.addAttribute("producto", producto);
        model.addAttribute("exito", true);
        Iterable<Categoria> categorias = categoriaRepository.findAll();
        model.addAttribute("categorias", categorias);
        
        return "vistas/producto/editar";
    }
}
