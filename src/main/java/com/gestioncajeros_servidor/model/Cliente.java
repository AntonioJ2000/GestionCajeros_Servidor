/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestioncajeros_servidor.model;

/**
 *
 * @author anton
 */
public class Cliente {
    private int codigoCliente;
    private String nombre;
    private String apellidos;
    private String dni;
    private String login;
    private String password;
    private java.sql.Date fecha_nac;
    private String telefono;
    private String email;
    
    private Cuenta cuenta;
    private int numeroEjercicio;

    public Cliente() {}

    public Cliente(int codigoCliente, String nombre, String apellidos, String dni, String login, String password, java.sql.Date fecha_nac, String telefono, String email) {
        this.codigoCliente = codigoCliente;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.dni = dni;
        this.login = login;
        this.password = password;
        this.fecha_nac = fecha_nac;
        this.telefono = telefono;
        this.email = email;
    }
    
    public Cliente(String nombre, String apellidos, String dni, String login, String password, java.sql.Date fecha_nac, String telefono, String email) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.dni = dni;
        this.login = login;
        this.password = password;
        this.fecha_nac = fecha_nac;
        this.telefono = telefono;
        this.email = email;
    }
    
    public Cliente(Cliente cliente, Cuenta cuenta, int numeroEjercicio) {
        this.codigoCliente = cliente.getCodigoCliente();
        this.nombre = cliente.getNombre();
        this.apellidos = cliente.getApellidos();
        this.dni = cliente.getDni();
        this.login = cliente.getLogin();
        this.password = cliente.getPassword();
        this.fecha_nac = cliente.getFecha_nac();
        this.telefono = cliente.getTelefono();
        this.email = cliente.getEmail();
        this.cuenta = cuenta;
        this.numeroEjercicio = numeroEjercicio;
    }
    
    public int getCodigoCliente() {
        return codigoCliente;
    }

    public void setCodigoCliente(int codigoCliente) {
        this.codigoCliente = codigoCliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public java.sql.Date getFecha_nac() {
        return fecha_nac;
    }

    public void setFecha_nac(java.sql.Date fecha_nac) {
        this.fecha_nac = fecha_nac;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        String cadena = "";
        return "\n------ ID: "+codigoCliente+" ------\nNombre: "+nombre
                +"\nApellidos: "+apellidos+"\nLogin: "+login
                +"\nPassword: "+password+"\nFecha de nacimiento: "+fecha_nac
                +"\nTelefono: "+telefono+"\nEmail: "+email;
    }
}
