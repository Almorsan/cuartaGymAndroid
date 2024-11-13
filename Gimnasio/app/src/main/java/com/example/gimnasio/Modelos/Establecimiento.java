package com.example.gimnasio.Modelos;

import java.util.Set;

public class Establecimiento {

    private Long id;
    private String direccion;
    private String foto;
    private String fechaCreacionRegistro;
    private Set<Entrenador> entrenadores;
    private Set<Sala> salas;
    private Set<Cliente> clientes;

    private Entrenador creador;

    public Establecimiento() {
    }

    public Establecimiento(String direccion, String foto, String fechaCreacionRegistro) {
        this.direccion = direccion;
        this.foto = foto;
        this.fechaCreacionRegistro = fechaCreacionRegistro;
    }

    public Establecimiento(String direccion, String foto,
                           String fechaCreacionRegistro, Set<Entrenador> entrenadores,
                           Set<Sala> salas, Set<Cliente> clientes,
                           Entrenador creador) {
        this.direccion = direccion;
        this.foto = foto;
        this.fechaCreacionRegistro = fechaCreacionRegistro;
        this.entrenadores = entrenadores;
        this.salas = salas;
        this.clientes = clientes;
        this.creador=creador;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getFechaCreacionRegistro() {
        return fechaCreacionRegistro;
    }

    public void setFechaCreacionRegistro(String fechaCreacionRegistro) {
        this.fechaCreacionRegistro = fechaCreacionRegistro;
    }

    public Set<Entrenador> getEntrenadores() {
        return entrenadores;
    }

    public void setEntrenadores(Set<Entrenador> entrenadores) {
        this.entrenadores = entrenadores;
    }

    public Set<Sala> getSalas() {
        return salas;
    }

    public void setSalas(Set<Sala> salas) {
        this.salas = salas;
    }

    public Set<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(Set<Cliente> clientes) {
        this.clientes = clientes;
    }

    public Entrenador getCreador() {
        return creador;
    }

    public void setCreador(Entrenador creador) {
        this.creador = creador;
    }

    public Long getId() {
        return id;
    }
}
