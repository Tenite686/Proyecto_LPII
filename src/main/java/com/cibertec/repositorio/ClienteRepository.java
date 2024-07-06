package com.cibertec.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cibertec.modelos.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO CLIENTE (nombre, num_ruc, direccion, telefono) VALUES (:nombre, :num_ruc, :direccion, :telefono)", nativeQuery = true)
    void agregar(String nombre, String num_ruc, String direccion, String telefono);
    

    @Transactional
    @Modifying
    @Query("UPDATE Cliente c SET c.nombre = :nombre, c.num_ruc = :num_ruc, c.direccion = :direccion, c.telefono = :telefono WHERE c.id_cliente = :id")
    void actualizar(Integer id, String nombre, String num_ruc, String direccion, String telefono);

    @Transactional
    @Modifying
    @Query("DELETE FROM Cliente c WHERE c.id_cliente = :id")
    void eliminar(Integer id);

    @Query("SELECT MAX(c.id_cliente) FROM Cliente c WHERE c.nombre = :nombre")
    Integer nuevoId(String nombre);


    @Query("SELECT c FROM Cliente c WHERE c.nombre LIKE :nombre%")
    List<Cliente> buscarPorNombre(String nombre);

    @Query("SELECT c FROM Cliente c WHERE c.num_ruc LIKE :num_ruc%")
    List<Cliente> buscarPorNumRuc(String num_ruc);

    @Query("SELECT c FROM Cliente c WHERE c.direccion LIKE :direccion%")
    List<Cliente> buscarPorDireccion(String direccion);

    @Query("SELECT c FROM Cliente c WHERE c.id_cliente = :id")
    Cliente buscarPorId(Integer id);
    
    @Query("SELECT c.nombre FROM Cliente c")
    List<String> listarTodosLosNombres();
    
    Cliente findByNombre(String nombre);
    
    @Query("SELECT COUNT(v) > 0 FROM Venta v WHERE v.cliente.id = :id")
    boolean existsVentasByClienteId(@Param("id") int id);
    
    @Query("select c from Cliente c where "
            + " c.nombre like :nombre% and "
            + " c.num_ruc like :num_ruc% and "
            + " c.direccion like :direccion% and "
            + " c.telefono like :telefono% ")
    List<Cliente> listaCompleja(@Param("nombre") String nombre,
                                @Param("direccion") String direccion,
                                @Param("num_ruc") String num_ruc,
                                @Param("telefono") String telefono);

}
