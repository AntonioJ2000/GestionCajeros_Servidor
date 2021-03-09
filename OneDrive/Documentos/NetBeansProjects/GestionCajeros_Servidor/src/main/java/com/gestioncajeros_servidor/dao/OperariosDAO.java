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

public class OperariosDAO {
    private enum toSQL {
        ADD("INSERT INTO operarios (nombre, apellidos, login, password)"
                + " VALUES(?,?,?,?)"),
        GETALL("SELECT * FROM operarios"),
        GETBYID("SELECT * FROM operarios WHERE codigoOperario = ?"),
        DELETEBYID("DELETE FROM operarios WHERE codigoOperario = ?"),
        UPDATEBYID("UPDATE operarios SET nombre = ?, apellidos = ?, login = ?, password = ? WHERE codigoOperario = ?");

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

    public OperariosDAO() {
        con = ConnectionUtils.getConnection();
        this.convert = new Convert();
    }

    public List<Operarios> getAllOperators() {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Operarios o = null;
        List<Operarios> list = new ArrayList<>();

        try {
            ps = con.prepareStatement(toSQL.GETALL.toString());
            rs = ps.executeQuery();

            while (rs.next()) {
                o = convert.convertirOperarios(rs);
                list.add(o);
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

    public Operarios getOperatorById(int codigoOperario) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Operarios o = null;

        try {
            ps = con.prepareStatement(toSQL.GETBYID.toString());

            ps.setInt(1, codigoOperario);

            rs = ps.executeQuery();

            if (rs.next()) {
                o = convert.convertirOperarios(rs);
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

        return o;
    }

    public void insertOperator(Operarios operario) {
        PreparedStatement ps = null;

        try {
            ps = con.prepareStatement(toSQL.ADD.toString());

            ps.setString(1, operario.getNombre());
            ps.setString(2, operario.getApellidos());
            ps.setString(3, operario.getLogin());
            ps.setString(4, operario.getPassword());

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

    public void deleteOperatorById(int codigoOperario) {
        PreparedStatement ps = null;

        try {
            ps = con.prepareStatement(toSQL.DELETEBYID.toString());

            ps.setInt(1, codigoOperario);

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

    public void updateOperatorById(Operarios operario) {
        PreparedStatement ps = null;

        try {
            ps = con.prepareStatement(toSQL.UPDATEBYID.toString());

            ps.setString(1, operario.getNombre());
            ps.setString(2, operario.getApellidos());
            ps.setString(3, operario.getLogin());
            ps.setString(4, operario.getPassword());
            ps.setInt(5, operario.getCodigoOperario());

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
}
