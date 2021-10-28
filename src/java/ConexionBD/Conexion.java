package ConexionBD;

/**
 *
 * @author José Manuel
 */

import java.sql.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class Conexion {

    private String nombreBD;
    private String usuario;
    private String password;
    private String url;
    private ResultSet cdr;
    PreparedStatement sentencia;
    private Connection conn;

    public Conexion() {

        nombreBD = "farmacia";
        usuario = "root";
        password = null;
        url = "jdbc:mysql://localhost:3306/" + nombreBD;
        realizarConexion();

    }

    public ResultSet getCdr() {
        return cdr;
    }

    public void realizarConexion() {

        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection(url, usuario, password);
        } catch (ClassNotFoundException ex) {
        JOptionPane.showMessageDialog(null, "No encontro la clase dirver" + ex, "Error", JOptionPane.ERROR_MESSAGE);
        } catch (InstantiationException ex) {
            JOptionPane.showMessageDialog(null, "No se puede crear la instancia" + ex, "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalAccessException ex) {
            JOptionPane.showMessageDialog(null, "Acceso denegado" + ex, "Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "No se pudo conectar con la base de datos" + ex, "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    public static void main(String args[]) {

        Conexion objCBD = new Conexion();
        objCBD.realizarConexion();

    }

    public void ejecutarABC(ArrayList instruccionBD) {
        try {
            sentencia = conn.prepareCall(instruccionBD.get(0).toString());
            for (int i = 1; i < instruccionBD.size(); i++) {
                sentencia.setString(i, instruccionBD.get(i).toString());
            }
            sentencia.executeUpdate();
        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(null, "Error no se puedo ejecutar la consulta " + ex, "Error ", JOptionPane.ERROR_MESSAGE);
           
        }

    }

    public ResultSet ejecutarConsulta(ArrayList instruccionBD) {
        try {
            sentencia = conn.prepareCall(instruccionBD.get(0).toString());
            for (int i = 1; i < instruccionBD.size(); i++) {
                sentencia.setString(i, instruccionBD.get(i).toString());
            }
            cdr = sentencia.executeQuery();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error no se puedo ejecutar la consulta" + ex, "Error", JOptionPane.ERROR_MESSAGE);
            
        }
        return cdr;
    }

    public void cerrarConexion() {

        try {
            conn.close();
        } catch (SQLException ex) {
     JOptionPane.showMessageDialog(null, "No se pudo finalizar la conexion" + ex, "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

}

