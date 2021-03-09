package com.gestioncajeros_servidor.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConnectionUtils {
    private static Connection con = null;

    public static Connection connect(String db, String user,
            String password) throws SQLException, ClassNotFoundException {
        Connection con = null;

        Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost/" + db
                + "?useLegacyDatetimeCode=false&serverTimezone=UTC", user,
                password);

        return con;
    }

    public static Connection getConnection() {
        try {
            if (con == null || con.isClosed()) {
                try {
                    con = connect("gestion_cajeros", "root", "");
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return con;
    }

    public static void close() throws SQLException {
        if (con != null) {
            con.close();
        }
    }
}
