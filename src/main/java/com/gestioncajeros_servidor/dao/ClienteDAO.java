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
import java.util.ArrayList;
import java.util.List;
import servidorcajeros.dao.utils.Convert;

import com.gestioncajeros_servidor.connection.ConnectionUtils;
import com.gestioncajeros_servidor.model.*;

/**
 *
 * @author anton
 */
public class ClienteDAO {
    private enum toSQL {
        CREATE("INSERT into cliente (nombre, apellidos, dni, login, password, fecha_nac, telefono, email) VALUES (?,?,?,?,?,?,?,?))"),
        GETALL("SELECT * FROM cliente"),
        GETBYID("SELECT * FROM cliente WHERE codigoCliente = ?"),
        DELETEBYID("DELETE FROM cliente where codigoCliente = ?"),
        UPDATEBYID("UPDATE cliente SET nombre = ?, apellidos = ?, dni = ?, login = ?, password = ?, fecha_nac = ?, telefono = ?, email = ? WHERE id = ?");
        
        
        
        private String value;
    
        toSQL(String s){
            value = s;
        }

        public String toString(){
            return value;
        }
        
    }
    
    private Convert convert;
    
    private Connection con;
    
    
    public ClienteDAO() {
        con = ConnectionUtils.getConnection();
        this.convert = new Convert();
    }
    
        public void createClient(Cliente c) {
        PreparedStatement ps = null;

        try {
            ps = con.prepareStatement(toSQL.CREATE.toString());

            ps.setString(1, c.getNombre());
            ps.setString(2, c.getApellidos());
            ps.setString(3, c.getDni());
            ps.setString(4, c.getLogin());
            ps.setString(5, c.getPassword());
            ps.setDate(6, c.getFecha_nac());
            ps.setString(7, c.getTelefono());
            ps.setString(8, c.getEmail());

            if (ps.executeUpdate() == 0) {
                throw new SQLException("No se ha insertado correctamente");
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
        
    public List<Cliente> getAllClients() {
        List<Cliente> list = new ArrayList<>();
        Cliente c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = con.prepareStatement(toSQL.GETALL.toString());

            rs = ps.executeQuery();

            while (rs.next()) {
                c = convert.convertirCliente(rs);
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
    
     public Cliente getClientByID(int codigoCliente) {
        Cliente c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = con.prepareStatement(toSQL.GETBYID.toString());

            ps.setInt(1, codigoCliente);

            rs = ps.executeQuery();

            if (rs.next()) {
                c = convert.convertirCliente(rs);
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
    
     public void deleteClientByID(int codigoCliente) {
        PreparedStatement ps = null;

        try {
            ps = con.prepareStatement(toSQL.DELETEBYID.toString());

            ps.setInt(1, codigoCliente);

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
     
    public void updateUser(Cliente c) {
        PreparedStatement ps = null;

        try {
            ps = con.prepareStatement(toSQL.UPDATEBYID.toString());

            ps.setString(1, c.getNombre());
            ps.setString(2, c.getApellidos());
            ps.setString(3, c.getDni());
            ps.setString(4, c.getLogin());
            ps.setString(5, c.getPassword());
            ps.setDate(6, c.getFecha_nac());
            ps.setString(7, c.getTelefono());
            ps.setString(8, c.getEmail());
            ps.setInt(9, c.getCodigoCliente());

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
}