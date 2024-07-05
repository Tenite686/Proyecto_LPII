package com.cibertec.modelos;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
@Entity
public class Categoria {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private int id_categoria;
	    private String nombre;
	    @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL, orphanRemoval = true)
	    private List<Producto> productos;

	  public Categoria () {
		  
	  }

	public Categoria(int id_categoria, String nombre) {
		this.id_categoria = id_categoria;
		this.nombre = nombre;
	}
	

	public int getId_categoria() {
		return id_categoria;
	}

	public void setId_categoria(int id_categoria) {
		this.id_categoria = id_categoria;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	
	
	  
}
