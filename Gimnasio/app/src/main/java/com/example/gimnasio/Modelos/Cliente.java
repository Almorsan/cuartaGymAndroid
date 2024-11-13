package com.example.gimnasio.Modelos;

public class Cliente {

    private Long id;
    private String nombre;
    private String fechaNacimiento;

    private enum Genero {
        M,
        F;
    }

    private double peso;

    private double altura;

    private String foto;

    private String fechaCreacionRegistro;

    private Entrenador entrenador;

    public Cliente() {
    }

    public Cliente( String nombre, String fechaNacimiento,
                   double peso, double altura, String foto,
                   String fechaCreacionRegistro) {

        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.peso = peso;
        this.altura = altura;
        this.foto = foto;
        this.fechaCreacionRegistro = fechaCreacionRegistro;
    }

    public Cliente(Long id, String nombre, String fechaNacimiento, double peso, double altura, String foto, String fechaCreacionRegistro, Entrenador entrenador) {
        this.id = id;
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.peso = peso;
        this.altura = altura;
        this.foto = foto;
        this.fechaCreacionRegistro = fechaCreacionRegistro;
        this.entrenador = entrenador;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public double getAltura() {
        return altura;
    }

    public void setAltura(double altura) {
        this.altura = altura;
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
}
