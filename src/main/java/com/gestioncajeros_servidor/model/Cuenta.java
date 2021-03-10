/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestioncajeros_servidor.model;

import java.time.LocalDateTime;

/**
 *
 * @author anton
 */
public class Cuenta {

    private int codigoCuenta;
    private double saldo;
    private LocalDateTime fechaHoraCreacion;
    private LocalDateTime fechaHoraUltimaModificacion;

    public Cuenta() {
    }

    public Cuenta(int codigoCuenta, double saldo, LocalDateTime fechaHoraCreacion, LocalDateTime fechaHoraUltimaModificacion) {
        this.codigoCuenta = codigoCuenta;
        this.saldo = saldo;
        this.fechaHoraCreacion = fechaHoraCreacion;
        this.fechaHoraUltimaModificacion = fechaHoraUltimaModificacion;
    }

    public Cuenta(double saldo, LocalDateTime fechaHoraCreacion, LocalDateTime fechaHoraUltimaModificacion) {
        this.saldo = saldo;
        this.fechaHoraCreacion = fechaHoraCreacion;
        this.fechaHoraUltimaModificacion = fechaHoraUltimaModificacion;
    }

    public int getCodigoCuenta() {
        return codigoCuenta;
    }

    public void setCodigoCuenta(int codigoCuenta) {
        this.codigoCuenta = codigoCuenta;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public LocalDateTime getFechaHoraCreacion() {
        return fechaHoraCreacion;
    }

    public void setFechaHoraCreacion(LocalDateTime fechaHoraCreacion) {
        this.fechaHoraCreacion = fechaHoraCreacion;
    }

    public LocalDateTime getFechaHoraUltimaModificacion() {
        return fechaHoraUltimaModificacion;
    }

    public void setFechaHoraUltimaModificacion(LocalDateTime fechaHoraUltimaModificacion) {
        this.fechaHoraUltimaModificacion = fechaHoraUltimaModificacion;
    }

    @Override
    public String toString() {
        String cadena = "";
        return "\n------ ID: " + codigoCuenta + " ------\nSaldo: " + saldo
                + "\nFecha y hora de creacion: " + fechaHoraCreacion
                + "\nFecha y hora de la ultima modificacion: " + fechaHoraUltimaModificacion;
    }

}
