package com.cibertec.modelos;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
@Entity
public class Cliente {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	private int id_cliente;
	private String nombre;
	private String num_ruc;
	private String direccion;
	private String telefono;

	public Cliente() {
	}

	public Cliente(int id_cliente, String nombre, String num_ruc, String direccion, String telefono) {
		this.id_cliente = id_cliente;
		this.nombre = nombre;
		this.num_ruc = num_ruc;
		this.direccion = direccion;
		this.telefono = telefono;
	}

	public int getId_cliente() {
		return id_cliente;
	}

	public void setId_cliente(int id_cliente) {
		this.id_cliente = id_cliente;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNum_ruc() {
		return num_ruc;
	}

	public void setNum_ruc(String num_ruc) {
		this.num_ruc = num_ruc;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}


}
