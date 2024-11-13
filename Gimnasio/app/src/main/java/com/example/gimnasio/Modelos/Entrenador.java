package com.example.gimnasio.Modelos;

public class Entrenador {

   private String nombreCreador;
   private String nickCreador;
   private String nombreUser;
   private String nickUser;
   private String fechaUltimaConexionUser;
   private String fechaNacimientoUser;
   private String fechaAltaUser;
   private String direccionEstablecimiento;

   private Long id;

    public Entrenador() {
    }


    public Entrenador(String nombreCreador, String nickCreador,
                      String nombreUser, String nickUser,
                      String fechaUltimaConexionUser, String fechaNacimientoUser,
                      String fechaAltaUser, String direccionEstablecimiento) {
        this.nombreCreador = nombreCreador;
        this.nickCreador = nickCreador;
        this.nombreUser = nombreUser;
        this.nickUser = nickUser;
        this.fechaUltimaConexionUser = fechaUltimaConexionUser;
        this.fechaNacimientoUser = fechaNacimientoUser;
        this.fechaAltaUser = fechaAltaUser;
        this.direccionEstablecimiento = direccionEstablecimiento;
    }

    public String getNombreCreador() {
        return nombreCreador;
    }

    public void setNombreCreador(String nombreCreador) {
        this.nombreCreador = nombreCreador;
    }

    public String getNickCreador() {
        return nickCreador;
    }

    public void setNickCreador(String nickCreador) {
        this.nickCreador = nickCreador;
    }

    public String getNombreUser() {
        return nombreUser;
    }

    public void setNombreUser(String nombreUser) {
        this.nombreUser = nombreUser;
    }

    public String getNickUser() {
        return nickUser;
    }

    public void setNickUser(String nickUser) {
        this.nickUser = nickUser;
    }

    public String getFechaUltimaConexionUser() {
        return fechaUltimaConexionUser;
    }

    public void setFechaUltimaConexionUser(String fechaUltimaConexionUser) {
        this.fechaUltimaConexionUser = fechaUltimaConexionUser;
    }

    public String getFechaNacimientoUser() {
        return fechaNacimientoUser;
    }

    public void setFechaNacimientoUser(String fechaNacimientoUser) {
        this.fechaNacimientoUser = fechaNacimientoUser;
    }

    public String getFechaAltaUser() {
        return fechaAltaUser;
    }

    public void setFechaAltaUser(String fechaAltaUser) {
        this.fechaAltaUser = fechaAltaUser;
    }

    public String getDireccionEstablecimiento() {
        return direccionEstablecimiento;
    }

    public void setDireccionEstablecimiento(String direccionEstablecimiento) {
        this.direccionEstablecimiento = direccionEstablecimiento;
    }

    public Long getId() {
        return id;
    }
}
