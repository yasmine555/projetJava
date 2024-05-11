/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import models.Material; 

/**
 *
 * @author HP-ELITEBOOK
 */
public class newController {
    @FXML
    private TextField nameField;

    @FXML
    private TextField quantityField;

    private Connection conn;

    @FXML
    private void saveMaterial() {
        String name = nameField.getText().trim();
        String quantityText = quantityField.getText().trim();

        if (name.isEmpty() || quantityText.isEmpty()) {
            // Afficher un message d'erreur si des champs sont vides
            System.out.println("Veuillez remplir tous les champs.");
            return;
        }

        try {
            int quantity = Integer.parseInt(quantityText);
            Material newMaterial = new Material(name, quantity);
            insertMaterial(newMaterial);
            clearFields(); // Effacer les champs après l'ajout
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
            System.out.println("Nouveau matériau ajouté avec succès.");
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
}


