package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import models.Material;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class newController {
    @FXML
    private TextField nameField;

    @FXML
    private TextField quantityField;

    @FXML
    private Button saveButton;

    private Connection conn;
    private ListeMaterController listeMaterController;

    @FXML
    private void saveMaterial() {
        String name = nameField.getText().trim();
        String quantityText = quantityField.getText().trim();

        if (name.isEmpty() || quantityText.isEmpty()) {
            System.out.println("Veuillez remplir tous les champs.");
            return;
        }

        try {
            int quantity = Integer.parseInt(quantityText);
            Material newMaterial = new Material(name, quantity);
            insertMaterial(newMaterial);
            clearFields();
            System.out.println("Nouveau matériau ajouté avec succès.");
            listeMaterController.refreshMaterialList();
        } catch (NumberFormatException e) {
            System.out.println("Veuillez entrer une quantité valide.");
        }
    }

    private void insertMaterial(Material material) {
        String sql = "INSERT INTO Material(name, quantity) VALUES (?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, material.getName());
            pstmt.setInt(2, material.getQuantity());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout du matériau : " + e.getMessage());
        }
    }

    private void clearFields() {
        nameField.clear();
        quantityField.clear();
    }

    public void setConnection(Connection conn) {
        this.conn = conn;
    }

    public void setListeMaterController(ListeMaterController listeMaterController) {
        this.listeMaterController = listeMaterController;
    }
}
