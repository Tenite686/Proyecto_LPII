package com.cibertec.repositorio;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cibertec.modelos.Cliente;
import com.cibertec.modelos.Producto;
import com.cibertec.modelos.Venta;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Integer> {

    @Query("SELECT v FROM Venta v WHERE v.id_venta = :id")
    public Venta buscarPorId(Integer id);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO VENTA (id_producto, id_cliente, fecha) " +
                   "VALUES (:productoId, :clienteId, :fecha)",
                   nativeQuery = true)
    void agregar(int productoId, int clienteId, Date fecha);

    @Transactional
    @Modifying
    @Query("UPDATE Venta v SET v.producto = :producto, v.cliente = :cliente, " +
           "v.fecha = :fecha WHERE v.id_venta = :id")
    void actualizar(Integer id, Producto producto, Cliente cliente, Date fecha);
    
    @Transactional
    @Modifying
    @Query("DELETE FROM Venta v WHERE v.id_venta = :id")
    void eliminar(Integer id);

    @Query("SELECT MAX(v.id_venta) FROM Venta v WHERE v.fecha = :fecha")
    Integer nuevoId(Date fecha);

    @Query("SELECT v FROM Venta v WHERE v.fecha = :fecha")
    public List<Venta> buscarPorFecha(Date fecha);
    
    @Query("SELECT v FROM Venta v WHERE v.cliente = :cliente")
    List<Venta> buscarPorCliente(Cliente cliente);

    @Query("SELECT v FROM Venta v WHERE v.producto = :producto")
    List<Venta> buscarPorProducto(Producto producto);
    
    @Query("SELECT v.fecha FROM Venta v")
    List<Date> listarTodasLasFechas();
    boolean existsByCliente(Cliente cliente);
    
    @Query("SELECT v FROM Venta v " +
            "WHERE (:id_cliente =-1 OR v.cliente.id_cliente = :id_cliente) " +
            "AND (:id_producto =-1 OR v.producto.id_producto = :id_producto) " +
            "AND (:fechaDesde IS NULL OR v.fecha >= :fechaDesde) " +
            "AND (:fechaHasta IS NULL OR v.fecha <= :fechaHasta)")
    List<Venta> listaCompleja(
            @Param("id_cliente") Integer id_cliente,
            @Param("id_producto") Integer id_producto,
            @Param("fechaDesde") Date fechaDesde,
            @Param("fechaHasta") Date fechaHasta);


}
