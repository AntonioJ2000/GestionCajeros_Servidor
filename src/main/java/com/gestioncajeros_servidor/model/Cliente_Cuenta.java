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
public class Cliente_Cuenta {
    private int codigoCliente;
    private int codigoCuenta;
    private LocalDateTime fechaHoraUltimoAcceso;

    public Cliente_Cuenta() {}

    public Cliente_Cuenta(int codigoCliente, int codigoCuenta, LocalDateTime fechaHoraUltimoAcceso) {
        this.codigoCliente = codigoCliente;
        this.codigoCuenta = codigoCuenta;
        this.fechaHoraUltimoAcceso = fechaHoraUltimoAcceso;
    }

    public int getCodigoCliente() {
        return codigoCliente;
    }

    public void setCodigoCliente(int codigoCliente) {
        this.codigoCliente = codigoCliente;
    }

    public int getCodigoCuenta() {
        return codigoCuenta;
    }

    public void setCodigoCuenta(int codigoCuenta) {
        this.codigoCuenta = codigoCuenta;
    }

    public LocalDateTime getFechaHoraUltimoAcceso() {
        return fechaHoraUltimoAcceso;
    }

    public void setFechaHoraUltimoAcceso(LocalDateTime fechaHoraUltimoAcceso) {
        this.fechaHoraUltimoAcceso = fechaHoraUltimoAcceso;
    }

    @Override
    public String toString() {
        return "Cliente_Cuenta{" + "codigoCliente=" + codigoCliente + ", codigoCuenta=" + codigoCuenta + ", fechaHoraUltimoAcceso=" + fechaHoraUltimoAcceso + '}';
    }

}
