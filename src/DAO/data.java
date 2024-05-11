package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import models.Material;

public class data {
    private static final String DATABASE_URL = "jdbc:sqlite:data.db";

    private static Connection getConnection() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private Connection dbconnect() throws SQLException {
        return DriverManager.getConnection(DATABASE_URL);
    }

    public void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS Material (name TEXT, quantity INTEGER)";

        try (Connection conn = dbconnect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.err.println("Error creating table: " + e.getMessage());
        }
    }

    public void insertMaterial(String name, int quantity) {
        String sql = "INSERT INTO Material(name, quantity) VALUES (?, ?)";

        try (Connection conn = dbconnect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setInt(2, quantity);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error inserting material: " + e.getMessage());
        }
    }

    public List<Material> getAllMaterials() throws SQLException {
        List<Material> materials = new ArrayList<>();
        String sql = "SELECT name, quantity FROM Material";

        try (Connection conn = dbconnect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String name = rs.getString("name");
                int quantity = rs.getInt("quantity");
                Material material = new Material(name, quantity);
                materials.add(material);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving materials: " + e.getMessage());
            throw e;
        }

        return materials;
    }

    public static void updateMaterial(Material material) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = data.getConnection();

            String sql = "UPDATE materials SET name = ?, quantity = ? WHERE id = ?";
            statement = connection.prepareStatement(sql);

            // Définir les paramètres de la requête avec les nouvelles valeurs du matériau
            statement.setString(1, material.getName());
            statement.setInt(2, material.getQuantity());
            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Material updated successfully.");
            } else {
                System.out.println("Failed to update material.");
            }
        } catch (SQLException e) {
            System.err.println("Error updating material: " + e.getMessage());
            throw e; // Rejeter l'exception pour être gérée par l'appelant
        } finally {
            // Fermer les ressources JDBC
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }

    public void deleteMaterial(String name) {
        String sql = "DELETE FROM Material WHERE name = ?";

        try (Connection conn = dbconnect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error deleting material: " + e.getMessage());
        }
    }

    public void updateMaterialQuantity(String name, int quantity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
