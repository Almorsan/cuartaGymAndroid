package com.example.gimnasio.Modelos;

public class TipoActividad {

    private Long id;
    private String nombreTipoActividad;
    private String fechaCreacionRegistro;
    private Entrenador creador;

    public TipoActividad(String nombreTipoActividad, String fechaCreacionRegistro, Entrenador creador) {
        this.nombreTipoActividad = nombreTipoActividad;
        this.fechaCreacionRegistro = fechaCreacionRegistro;
        this.creador = creador;
    }

    public TipoActividad(String nombreTipoActividad, String fechaCreacionRegistro) {
        this.nombreTipoActividad = nombreTipoActividad;
        this.fechaCreacionRegistro = fechaCreacionRegistro;
    }

    public TipoActividad() {
    }

    public String getNombreTipoActividad() {
        return nombreTipoActividad;
    }

    public void setNombreTipoActividad(String nombreTipoActividad) {
        this.nombreTipoActividad = nombreTipoActividad;
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
