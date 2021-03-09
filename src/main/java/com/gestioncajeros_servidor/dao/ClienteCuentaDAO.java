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
public class ClienteCuentaDAO {
     private enum toSQL {
        GETALL("SELECT * FROM cliente_cuenta"),
        GETBYCLIENTE("SELECT * FROM cliente_cuenta WHERE codigoCliente = ?"),
        GETBYCUENTA("SELECT * FROM cliente_cuenta WHERE codigoCuenta = ?"),
        DELETE("DELETE FROM cliente_cuenta WHERE codigoCuenta = ? AND codigoCliente = ?"),
        INSERT("INSERT INTO cliente_cuenta (codigoCliente, codigoCuenta, fechaHoraUltimoAcceso) VALUES (?,?,?)"),
        UPDATE("UPDATE cliente_cuenta SET fechaHoraUltimoAcceso = ? WHERE codigoCliente = ? AND codigoCuenta = ?");

        private String value;

        toSQL(String s) {
            value = s;
        }

        public String toString() {
            return value;
        }
    }

    private Convert convert;

    private Connection con;

    public ClienteCuentaDAO() {
        con = ConnectionUtils.getConnection();
        this.convert = new Convert();
    }

    public List<Cliente_Cuenta> getAll() {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Cliente_Cuenta cc = null;
        List<Cliente_Cuenta> list = new ArrayList<>();

        try {
            ps = con.prepareStatement(toSQL.GETALL.toString());
            rs = ps.executeQuery();

            while (rs.next()) {
                cc = convert.convertirCC(rs);
                list.add(cc);
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

    public List<Cliente_Cuenta> getByCliente(Cliente cliente) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Cliente_Cuenta cc = null;
        List<Cliente_Cuenta> list = new ArrayList<>();

        try {
            ps = con.prepareStatement(toSQL.GETBYCLIENTE.toString());

            ps.setInt(1, cliente.getCodigoCliente());

            rs = ps.executeQuery();

            while (rs.next()) {
                cc = convert.convertirCC(rs);
                list.add(cc);
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

    public List<Cliente_Cuenta> getByCuenta(Cuenta cuenta) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Cliente_Cuenta cc = null;
        List<Cliente_Cuenta> list = new ArrayList<>();

        try {
            ps = con.prepareStatement(toSQL.GETBYCUENTA.toString());

            ps.setInt(1, cuenta.getCodigoCuenta());

            rs = ps.executeQuery();

            while (rs.next()) {
                cc = convert.convertirCC(rs);
                list.add(cc);
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

    public void delete(Cliente cliente, Cuenta cuenta) {
        PreparedStatement ps = null;

        try {
            ps = con.prepareStatement(toSQL.DELETE.toString());

            ps.setInt(1, cuenta.getCodigoCuenta());
            ps.setInt(2, cliente.getCodigoCliente());

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

    public void insert(Cliente_Cuenta cc) {
        PreparedStatement ps = null;

        try {
            ps = con.prepareStatement(toSQL.INSERT.toString());

            ps.setInt(1, cc.getCodigoCliente());
            ps.setInt(2, cc.getCodigoCuenta());
            ps.setTimestamp(3, Timestamp.valueOf(cc.getFechaHoraUltimoAcceso()));

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

    public void update(Cliente_Cuenta cc) {
        PreparedStatement ps = null;

        try {
            ps = con.prepareStatement(toSQL.UPDATE.toString());

            ps.setInt(2, cc.getCodigoCliente());
            ps.setInt(3, cc.getCodigoCuenta());
            ps.setTimestamp(1, Timestamp.valueOf(cc.getFechaHoraUltimoAcceso()));

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
}
