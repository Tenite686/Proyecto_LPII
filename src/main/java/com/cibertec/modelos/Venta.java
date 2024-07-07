package com.cibertec.modelos;

import java.util.Date;

import com.cibertec.util.FunctionUtil;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
public class Venta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_venta;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;
    
    @ManyToOne
    @JoinColumn(name = "id_producto")
    private Producto producto;

    public Venta() {
    }

    public Venta(int id_venta, Date fecha, Cliente cliente, Producto producto) {
        this.id_venta = id_venta;
        this.fecha = fecha;
        this.cliente = cliente;
        this.producto = producto;
    }

    public int getId_venta() {
        return id_venta;
    }

    public void setId_venta(int id_venta) {
        this.id_venta = id_venta;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }
	//Atributos para reporte	
    public String getReporteProducto() {
		return producto.getDescripcion();
	}public String getReporteCliente() {
		return cliente.getNombre();
	}
	public String getReporteFecha() {
		return FunctionUtil.getFechaString(fecha);
	}
}
