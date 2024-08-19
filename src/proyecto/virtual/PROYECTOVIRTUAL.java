/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package proyecto.virtual;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jhon Gaviria
 */
public class PROYECTOVIRTUAL {
    private static final String BASE = "test"; // Nombre de la base de datos
    private static final String URL = "jdbc:mysql://localhost:3306/" + BASE;
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        // Ejemplo de uso
        try (Connection con = getConnection()) {
            if (con != null) {
		System.out.println("Conexión Exitosa!! a la base de datos.");

            } else {
                System.out.println("Error al conectar a la base de datos.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static Connection getConnection() throws SQLException {
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.err.println("Error al conectar a la base de datos: " + e.getMessage());
        }
        return con;
    }
    public static int createPersona(Connection con, PersonaCode persona) throws SQLException {
        String sql = "INSERT INTO persona (clave, nombre, domicilio, telefono, correo_electronico, fecha_nacimiento, genero) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, persona.getClave());
            pstmt.setString(2, persona.getNombre());
            pstmt.setString(3, persona.getDomicilio());
            pstmt.setString(4, persona.getTelefono());
            pstmt.setString(5, persona.getCorreoElectronico());
            pstmt.setDate(6, persona.getFechaNacimiento());
            pstmt.setString(7, persona.getGenero());
            pstmt.executeUpdate();
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return -1;
    }
   
    
}
        