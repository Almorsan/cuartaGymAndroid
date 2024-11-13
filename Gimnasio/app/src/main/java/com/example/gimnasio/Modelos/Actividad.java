package com.example.gimnasio.Modelos;

import java.util.Set;

public class Actividad {

    private Long id;
    private String fechaInicio;
    private String fechaFin;
    private String fechaCreacionRegistro;
    private TipoActividad tipoActividad;
    private Entrenador entrenador;
    private Establecimiento establecimiento;
    private Sala sala;
    private Entrenador registroCreadoPor;
    private Set<Cliente> clientes;


    public Actividad() {
    }

    public Actividad(String fechaInicio, String fechaFin) {
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public Actividad(String fechaInicio, String fechaFin, String fechaCreacionRegistro) {
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.fechaCreacionRegistro = fechaCreacionRegistro;
    }

    public Actividad(String fechaInicio, String fechaFin, String fechaCreacionRegistro,
                     TipoActividad tipoActividad, Entrenador entrenador, Establecimiento establecimiento,
                     Sala sala, Entrenador registroCreadoPor, Set<Cliente> clientes) {
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.fechaCreacionRegistro = fechaCreacionRegistro;
        this.tipoActividad = tipoActividad;
        this.entrenador = entrenador;
        this.establecimiento = establecimiento;
        this.sala = sala;
        this.registroCreadoPor = registroCreadoPor;
        this.clientes = clientes;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getFechaCreacionRegistro() {
        return fechaCreacionRegistro;
    }

    public void setFechaCreacionRegistro(String fechaCreacionRegistro) {
        this.fechaCreacionRegistro = fechaCreacionRegistro;
    }

    public TipoActividad getTipoActividad() {
        return tipoActividad;
    }

    public void setTipoActividad(TipoActividad tipoActividad) {
        this.tipoActividad = tipoActividad;
    }

    public Entrenador getEntrenador() {
        return entrenador;
    }

    public void setEntrenador(Entrenador entrenador) {
        this.entrenador = entrenador;
    }

    public Establecimiento getEstablecimiento() {
        return establecimiento;
    }

    public void setEstablecimiento(Establecimiento establecimiento) {
        this.establecimiento = establecimiento;
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    public Entrenador getRegistroCreadoPor() {
        return registroCreadoPor;
    }

    public void setRegistroCreadoPor(Entrenador registroCreadoPor) {
        this.registroCreadoPor = registroCreadoPor;
    }

    public Set<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(Set<Cliente> clientes) {
        this.clientes = clientes;
    }

    public Long getId() {
        return id;
    }
}
