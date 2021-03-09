/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestioncajeros_servidor;

import com.gestioncajeros_servidor.connection.ConnectionUtils;

/**
 *
 * @author anton
 */
public class ServidorCajeros {
    public static void main(String[] args) {
        System.out.println(ConnectionUtils.getConnection());
    }
}
