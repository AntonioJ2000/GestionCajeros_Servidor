/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestioncajeros_servidor.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import com.servidorcajeros_servidor.dao.utils.Convert;
import com.gestioncajeros_servidor.connection.ConnectionUtils;
import com.gestioncajeros_servidor.model.*;

/**
 *
 * @author anton
 */
public class CuentaDAO {
     private enum toSQL {
        ADD("INSERT INTO cuenta (saldo, fechaHoraCreacion, fechaHoraUltimaModificacion)"
                + " VALUES(?,?,?)"),
        GETALL("SELECT * FROM cuenta"),
        GETBYID("SELECT * FROM cuenta WHERE codigoCuenta = ?"),
        DELETEBYID("DELETE FROM cuenta WHERE codigoCuenta = ?"),
        UPDATEBYID("UPDATE cuenta SET saldo = ?, fechaHoraCreacion = ?, fechaHoraUltimaModificacion = ? WHERE codigoCuenta = ?"),
        GETACCOUNT("SELECT c.* FROM cuenta AS c "
                + "INNER JOIN cliente_cuenta AS cc ON c.codigoCuenta = cc.codigoCuenta "
                + "INNER JOIN cliente AS cl ON cc.codigoCliente = cl.codigoCliente "
                + "WHERE cl.codigoCliente = ?");

        private String value;

        toSQL(String s) {
            value = s;
        }

        public String toString() {
            return value;
        }
    }
    
    private boolean hayCliente = false;

    private Convert convert;

    private Connection con;

    public CuentaDAO() {
        con = ConnectionUtils.getConnection();
        this.convert = new Convert();
    }

    public List<Cuenta> getAllAccounts() {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Cuenta c = null;
        List<Cuenta> list = new ArrayList<>();

        try {
            ps = con.prepareStatement(toSQL.GETALL.toString());
            rs = ps.executeQuery();

            while (rs.next()) {
                c = convert.convertirCuenta(rs);
                list.add(c);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }

        return list;
    }

    public Cuenta getAccountById(int codigoCuenta) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Cuenta c = null;

        try {
            ps = con.prepareStatement(toSQL.GETBYID.toString());

            ps.setInt(1, codigoCuenta);

            rs = ps.executeQuery();

            if (rs.next()) {
                c = convert.convertirCuenta(rs);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }

        return c;
    }
    
    public Cuenta getAccountFromClient(int codigoCliente) {
        Cuenta cuenta = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = con.prepareStatement(toSQL.GETACCOUNT.toString());

            ps.setInt(1, codigoCliente);

            rs = ps.executeQuery();

            if (rs.next()) {
                cuenta = convert.convertirCuenta(rs);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }

        return cuenta;
    }
    
    public int insertAccount(Cuenta cuenta){
        int result = 0;
        PreparedStatement ps = null;

        try {
            ps = con.prepareStatement(toSQL.ADD.toString(), Statement.RETURN_GENERATED_KEYS);

            ps.setDouble(1, cuenta.getSaldo());
            ps.setTimestamp(2, Timestamp.valueOf(cuenta.getFechaHoraCreacion()));
            ps.setTimestamp(3, Timestamp.valueOf(cuenta.getFechaHoraUltimaModificacion()));

            if (ps.executeUpdate() == 0) {
                throw new SQLException("No se ha insertado correctamente");
            }
            
            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                    result = generatedKeys.getInt(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
        
        return result;
    }

    public void deleteAccountById(int codigoCuenta) {
        PreparedStatement ps = null;

        try {
            ps = con.prepareStatement(toSQL.DELETEBYID.toString());

            ps.setInt(1, codigoCuenta);

            if (ps.executeUpdate() == 0) {
                throw new SQLException("No se ha eliminado correctamente");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public void updateAccountById(Cuenta cuenta) {
        PreparedStatement ps = null;

        try {
            ps = con.prepareStatement(toSQL.UPDATEBYID.toString());

            ps.setDouble(1, cuenta.getSaldo());
            ps.setTimestamp(2, Timestamp.valueOf(cuenta.getFechaHoraCreacion()));
            ps.setTimestamp(3, Timestamp.valueOf(cuenta.getFechaHoraUltimaModificacion()));
            ps.setInt(4, cuenta.getCodigoCuenta());

            if (ps.executeUpdate() == 0) {
                throw new SQLException("No se ha actualizado correctamente");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
    
    public synchronized void introducirDinero(){
        while (hayCliente == true) {
            try {
                // Hay bola no puedo meter más bolas y me pongo a la espera
              wait();
            } catch (InterruptedException e) { }
        }
        //Después de liberarme, al llegar aquí, es porque he salido del wait y además hayBola=false
        System.out.println("Soy el hilo "+ "e introduzco una bola");
        hayCliente = true;
        //libero a todos los hilos que están esperando
        notifyAll();
    }
}
