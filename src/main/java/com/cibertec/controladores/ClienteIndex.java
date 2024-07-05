package com.cibertec.controladores;

import com.cibertec.modelos.Cliente;
import com.cibertec.repositorio.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ClienteIndex {

    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping("/buscarCliente")
    public String mostrarPaginaBusquedaCliente(Model model) {
        model.addAttribute("filtro", "nombre"); // Valor por defecto o el último seleccionado
        return "vistas/Cliente/index"; // Retorna la ruta de la página de búsqueda de clientes
    }

    @PostMapping("/buscarCliente")
    public String buscarClientesPorFiltro(@RequestParam("filtro") String filtro,
                                          @RequestParam("txtNombreBuscar") String terminoBuscar,
                                          Model model) {
        List<Cliente> listaClientes = null;

        switch (filtro) {
            case "nombre":
                listaClientes = clienteRepository.buscarPorNombre(terminoBuscar);
                break;
            case "numRuc":
                listaClientes = clienteRepository.buscarPorNumRuc(terminoBuscar);
                break;
            case "direccion":
                listaClientes = clienteRepository.buscarPorDireccion(terminoBuscar);
                break;
            default:
                listaClientes = clienteRepository.buscarPorNombre(terminoBuscar);
                break;
        }

        model.addAttribute("listaClientes", listaClientes); // Agrega la lista de clientes al modelo
        model.addAttribute("nombreBuscado", terminoBuscar); // Agrega el término buscado para mantenerlo en el input
        model.addAttribute("filtro", filtro); // Agrega el filtro seleccionado al modelo

        return "vistas/Cliente/index"; // Retorna la ruta de la página de búsqueda de clientes
    }
}
