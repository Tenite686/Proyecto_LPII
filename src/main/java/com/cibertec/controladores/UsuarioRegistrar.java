package com.cibertec.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.cibertec.repositorio.UsuarioRepository;

@Controller
public class UsuarioRegistrar {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/registro")
    public String mostrarFormularioRegistro() {
        return "vistas/Usuario/registro"; // Devuelve la vista del formulario de registro
    }

    @PostMapping("/registro")
    public String registrarUsuario(@RequestParam String nombreUsuario, @RequestParam String contraseña, Model model) {
        try {
            // Lógica para registrar el usuario en la base de datos
            usuarioRepository.registrarUsuario(nombreUsuario, contraseña);
            return "vistas/Usuario/Login"; // Devuelve la vista del formulario de registro
        } catch (Exception e) {
            model.addAttribute("error", "Error al registrar el usuario."); // Manejo de errores si falla el registro
            return "vistas/Usuario/registro"; // Devuelve la vista del formulario de registro
        }
    }
}
