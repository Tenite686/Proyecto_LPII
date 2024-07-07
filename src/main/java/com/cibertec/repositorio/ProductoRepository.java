package com.cibertec.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
    
    @Query("SELECT COUNT(v) > 0 FROM Venta v WHERE v.producto.id = :id")
    boolean existsVentasByProductoId(@Param("id") int id);
    
        
        @Query("select p from Producto p "
                + "where p.descripcion like CONCAT(:descripcion, '%') "
                + "and p.codigo_inventario like CONCAT(:codigo, '%') "
                + "and (:id_categoria = -1 or p.categoria.id_categoria = :id_categoria) "
                + "and (:precio is null or p.precio = :precio) "
                + "and (:stock = -1 or p.stock = :stock)")
        List<Producto> listaCompleja(
                @Param("descripcion") String descripcion,
                @Param("codigo") String codigo,
                @Param("id_categoria") int id_categoria,
                @Param("precio") Double precio,
                @Param("stock") int stock);
    


}
