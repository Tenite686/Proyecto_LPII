package com.cibertec.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cibertec.modelos.Categoria;

import jakarta.transaction.Transactional;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {

    @Transactional
    @Modifying
    @Query("DELETE FROM Producto p WHERE p.categoria.id_categoria = :id")
    void eliminarProductosPorCategoriaId(Integer id);

    @Transactional
    @Modifying
    @Query("UPDATE Producto p SET p.categoria = NULL WHERE p.categoria.id_categoria = :id")
    void desvincularProductosPorCategoriaId(Integer id);

    @Transactional
    @Modifying
    @Query("UPDATE Categoria c SET c.nombre = :nombre WHERE c.id_categoria = :id")
    void actualizar(Integer id, String nombre);

    @Transactional
    @Modifying
    @Query("DELETE FROM Categoria c WHERE c.id_categoria = :id")
    void eliminar(Integer id);

    Categoria findByNombre(String nombre);

    @Query("SELECT c.nombre FROM Categoria c")
    List<String> findAllNombres();
    
    @Query("SELECT c FROM Categoria c WHERE c.nombre LIKE %:nombre%")
    List<Categoria> buscarPorNombre(String nombre);

    @Query("SELECT c FROM Categoria c WHERE c.id_categoria = :id")
    Categoria buscarPorId(Integer id);
}
