package com.example.gimnasio.Modelos;

public class Sala {
    private Long id;
    private String nombre;
    private String foto;
    private String fechaCreacionRegistro;
    private Entrenador creador;

    public Sala(String nombre, String foto, String fechaCreacionRegistro, Entrenador creador) {
        this.nombre = nombre;
        this.foto = foto;
        this.fechaCreacionRegistro = fechaCreacionRegistro;
        this.creador = creador;
    }

    public Sala() {
    }

    public Sala(String nombre, String foto, String fechaCreacionRegistro) {
        this.nombre = nombre;
        this.foto = foto;
        this.fechaCreacionRegistro = fechaCreacionRegistro;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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
