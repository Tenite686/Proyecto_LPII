package com.cibertec.controladores;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cibertec.modelos.Usuario;
import com.cibertec.repositorio.UsuarioRepository;

@Controller
public class UsuarioValidar {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/iniciar-sesion")
    public String mostrarFormularioDeLogin() {
        return "vistas/Usuario/Login";
    }
    @PostMapping("/validar-credenciales")
    public String validarCredenciales(Model modelo, @RequestParam String nombre_usuario, @RequestParam String contraseña) {
        Usuario usuario = usuarioRepository.validarCredenciales(nombre_usuario, contraseña);
        if (usuario != null) {
            return "Vistas/BarraCarga/cargando"; // Redirige a la página de inicio de sesión
        } else {
            modelo.addAttribute("error", "Credenciales inválidas");
            return "vistas/Usuario/Login";
        }
    }
    
}
