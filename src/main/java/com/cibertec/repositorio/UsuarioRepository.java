package com.cibertec.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.cibertec.modelos.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    @Query("SELECT u FROM Usuario u WHERE u.nombre_usuario = :nombre_usuario AND u.contraseña = :contraseña")
    Usuario validarCredenciales(String nombre_usuario, String contraseña);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO USUARIO (nombre_usuario, contraseña) VALUES (:nombre_usuario, :contraseña)", nativeQuery = true)
    void registrarUsuario(String nombre_usuario, String contraseña);
}
