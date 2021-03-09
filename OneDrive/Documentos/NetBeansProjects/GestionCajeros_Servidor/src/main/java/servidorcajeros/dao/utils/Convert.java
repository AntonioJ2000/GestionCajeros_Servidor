/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidorcajeros.dao.utils;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import com.gestioncajeros_servidor.connection.ConnectionUtils;
import com.gestioncajeros_servidor.model.*;

/**
 *
 * @author anton
 */
public class Convert {
    public Convert() {
        ConnectionUtils.getConnection();
    }

    public Cliente convertirCliente(ResultSet rs) {
        Cliente cl = null;
        try {
            if (rs != null) {
                cl = new Cliente();
                int id = rs.getInt("codigoCliente");
                String nombre = rs.getString("nombre");
                String apellidos = rs.getString("apellidos");
                String dni = rs.getString("dni");
                String login = rs.getString("login");
                String password = rs.getString("password");
                Date fecha_nac = rs.getDate("fecha_nac");
                String telefono = rs.getString("telefono");
                String email = rs.getString("email");

                cl.setCodigoCliente(id);
                cl.setNombre(nombre);
                cl.setApellidos(apellidos);
                cl.setDni(dni);
                cl.setLogin(login);
                cl.setPassword(password);
                cl.setFecha_nac(fecha_nac);
                cl.setTelefono(telefono);
                cl.setEmail(email);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return cl;
    }

    public Cuenta convertirCuenta(ResultSet rs) {
        Cuenta cu = null;

        try {
            if (rs != null) {
                cu = new Cuenta();
                int id = rs.getInt("codigoCuenta");
                int saldo = rs.getInt("saldo");
                LocalDateTime fechaHoraCreacion = rs.getTimestamp("fechaHoraCreacion").toLocalDateTime(); 
                LocalDateTime fechaHoraUltimaModificacion = rs.getTimestamp("fechaHoraUltimaModificacion").toLocalDateTime();
                
                cu.setCodigoCuenta(id);
                cu.setSaldo(saldo);
                cu.setFechaHoraCreacion(fechaHoraCreacion);
                cu.setFechaHoraUltimaModificacion(fechaHoraUltimaModificacion);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return cu;
    }

    public Operarios convertirOperarios(ResultSet rs) {
        Operarios op = null;

        try {
            if (rs != null) {
                op = new Operarios();
                int id = rs.getInt("codigoOperario");
                String nombre = rs.getString("nombre");
                String apellidos = rs.getString("apellidos");
                String login = rs.getString("login");
                String password = rs.getString("password");
                
                op.setCodigoOperario(id);
                op.setNombre(nombre);
                op.setApellidos(apellidos);
                op.setLogin(login);
                op.setPassword(password);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return op;
    }
    
    public Cliente_Cuenta convertirCC(ResultSet rs){
        Cliente_Cuenta cc = null;

        try {
            if (rs != null) {
                cc = new Cliente_Cuenta();
                int cuenta = rs.getInt("codigoCuenta");
                int cliente = rs.getInt("codigoCliente");
                LocalDateTime fechaHoraUltimoAcceso = rs.getTimestamp("fechaHoraUltimoAcceso").toLocalDateTime();
                
                cc.setCodigoCliente(cliente);
                cc.setCodigoCuenta(cuenta);
                cc.setFechaHoraUltimoAcceso(fechaHoraUltimoAcceso);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return cc;
    }
}
