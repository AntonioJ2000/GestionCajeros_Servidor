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
public class Operarios {
    private int codigoOperario;
    private String nombre;
    private String apellidos;
    private String login;
    private String password;
    
    private Cuenta cuenta;

    public Operarios() {}

    public Operarios(int codigoOperario, String nombre, String apellidos, String login, String password) {
        this.codigoOperario = codigoOperario;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.login = login;
        this.password = password;
    }
    
     public Operarios(Operarios operario, Cuenta cuenta) {
        this.codigoOperario = operario.getCodigoOperario();
        this.nombre = operario.getNombre();
        this.apellidos = operario.getApellidos();
        this.login = operario.getLogin();
        this.password = operario.getPassword();
        this.cuenta = cuenta;
    }

    public int getCodigoOperario() {
        return codigoOperario;
    }

    public void setCodigoOperario(int codigoOperario) {
        this.codigoOperario = codigoOperario;
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
    
    @Override
    public String toString() {
        String cadena = "";
        return "\n------ ID: "+codigoOperario+" ------\nNombre: "+nombre
                +"\nApellidos: "+apellidos+"\nLogin: "+login
                +"\nPassword: "+password;
    }
}
