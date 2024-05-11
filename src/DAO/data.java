package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import models.Material;

public class data {
    private static final String DATABASE_URL = "jdbc:sqlite:data.db";

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

    public void updateMaterialQuantity(String name, int newQuantity) {
        String sql = "UPDATE Material SET quantity = ? WHERE name = ?";

        try (Connection conn = dbconnect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, newQuantity);
            pstmt.setString(2, name);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error updating material quantity: " + e.getMessage());
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
}
