package com.cibertec.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cibertec.modelos.Categoria;
import com.cibertec.modelos.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {
    @Query("SELECT p FROM Producto p WHERE p.id_producto = :id")
    public Producto buscarPorId(Integer id);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO PRODUCTO (codigo_inventario, descripcion, precio, stock, id_categoria) " +
                   "VALUES (:codigoInventario, :descripcion, :precio, :stock, :categoriaId)",
                   nativeQuery = true)
    void agregar(String codigoInventario, String descripcion, double precio, int stock, int categoriaId);

    @Transactional
    @Modifying
    @Query("UPDATE Producto p SET p.codigo_inventario = :codigoInventario, p.descripcion = :descripcion, " +
           "p.precio = :precio, p.stock = :stock, p.categoria = :categoria WHERE p.id_producto = :id")
    void actualizar(Integer id, String codigoInventario, String descripcion, double precio, int stock, Categoria categoria);
    
    @Transactional
    @Modifying
    @Query("DELETE FROM Producto p WHERE p.id_producto = :id")
    void eliminar(Integer id);

    @Query("SELECT MAX(p.id_producto) FROM Producto p WHERE p.descripcion = :descripcion")
    Integer nuevoId(String descripcion);

    @Query("SELECT p FROM Producto p WHERE p.descripcion LIKE :descripcion%")
    public List<Producto> buscarPorDescripcion(String descripcion);
    @Query("SELECT p FROM Producto p WHERE p.categoria = :categoria")
    List<Producto> buscarPorCategoria(Categoria categoria);
    // Consulta para obtener todos los nombres de los productos
    @Query("SELECT p.descripcion FROM Producto p")
    List<String> listarTodosLosNombres();
    
    Producto findByDescripcion(String descripcion);


}
