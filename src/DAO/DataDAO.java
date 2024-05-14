package DAO;

import java.io.IOException;
import models.Material;
import models.Personnel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DataDAO {

    private static final String DATABASE_URL = "jdbc:sqlite:data.db";

    public static void showModifyMaterial(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(DataDAO.class.getResource("/vues/modify.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement de modify.fxml : " + e.getMessage());
        }
    }

    public static void showNewMaterial(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(DataDAO.class.getResource("/vues/new.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement de new.fxml : " + e.getMessage());
        }
    }

    public static void deleteMaterial(Material selectedMaterial) {
        String sql = "DELETE FROM Materials WHERE name = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, selectedMaterial.getName());
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Matériau supprimé avec succès : " + selectedMaterial.getName());
            } else {
                System.out.println("Échec de la suppression du matériau.");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression du matériau : " + e.getMessage());
        }
    }
    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DATABASE_URL);
    }

    public void createMaterialTable() {
        String sql = "CREATE TABLE IF NOT EXISTS Material (" +
                     "name TEXT, " +
                     "quantity INTEGER)";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Error creating Material table", e);
        }
    }

    public void insertMaterial(Material material) {
        String sql = "INSERT INTO Material(name, quantity) VALUES (?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, material.getName());
            pstmt.setInt(2, material.getQuantity());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error inserting material", e);
        }
    }

    public List<Material> getAllMaterials() {
        List<Material> materials = new ArrayList<>();
        String sql = "SELECT name, quantity FROM Material";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String name = rs.getString("name");
                int quantity = rs.getInt("quantity");
                Material material = new Material(name, quantity);
                materials.add(material);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving materials", e);
        }

        return materials;
    }

    public void createPersonnelTable() {
        String sql = "CREATE TABLE IF NOT EXISTS Personnel (" +
                     "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                     "username TEXT UNIQUE, " +
                     "password TEXT, " +
                     "firstName TEXT, " +
                     "lastName TEXT)";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Error creating Personnel table", e);
        }
    }

    public void insertPersonnel(Personnel personnel) {
        String sql = "INSERT INTO Personnel(username, password, firstName, lastName) VALUES (?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, personnel.getUsername());
            pstmt.setString(2, personnel.getPassword());
            pstmt.setString(3, personnel.getFirstName());
            pstmt.setString(4, personnel.getLastName());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error inserting personnel", e);
        }
    }

    public void updateMaterial(Material selectedMaterial) {
        String sql = "UPDATE Material SET quantity = ? WHERE name = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, selectedMaterial.getQuantity());
            pstmt.setString(2, selectedMaterial.getName());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating material", e);
        }
    }

    public Personnel findPersonnelById(int personnelId) {
    String sql = "SELECT * FROM Personnel WHERE id = ?";

    try (Connection conn = getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setInt(1, personnelId);
        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            int id = rs.getInt("id");
            String username = rs.getString("username");
            String password = rs.getString("password");
            String firstName = rs.getString("firstName");
            String lastName = rs.getString("lastName");
            return new Personnel(id, username, password, firstName, lastName);
        }
    } catch (SQLException e) {
        throw new RuntimeException("Error finding personnel by ID", e);
    }

    return null; 
}

}
