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
    @GetMapping("/login")
    public String mostrarLogin() {
    	return "vistas/Usuario/Login";
    }

    @PostMapping("/registro")
    public String registrarUsuario(@RequestParam String nombreUsuario, @RequestParam String contraseña, @RequestParam String confirmarContraseña ,Model model) {
      
    	//Verificar si el nombre de usuario ya existe
    	if(usuarioRepository.existeUsuarioPorNombre(nombreUsuario)) {
    		model.addAttribute("error","El nombre de usuario ya existe. intenta con otro");
    		return "vistas/Usuario/registro";
    	}
    	
    	// Verificar si las contraseñas coinciden
        if (!contraseña.equals(confirmarContraseña)) {
            model.addAttribute("error", "Las contraseñas no coinciden.");
            return "vistas/Usuario/registro";
        }
        
        try {
            // Registrar usuario (usa método personalizado)
            usuarioRepository.registrarUsuario(nombreUsuario, contraseña);

            // Mensaje de éxito
            model.addAttribute("mensaje", "Usuario registrado correctamente. Ahora puedes iniciar sesión.");
            return "vistas/Usuario/registro"; // Puedes cambiar a "redirect:/iniciar-sesion" si quieres redirigir
        } catch (Exception e) {
            model.addAttribute("error", "Error al registrar el usuario.");
            return "vistas/Usuario/registro";
        }
    }
}
