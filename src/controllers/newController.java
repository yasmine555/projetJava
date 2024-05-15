package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import models.Material;
import DAO.DataDAO;
import static DAO.DataDAO.showPreviousInterface;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class newController {
    @FXML
    private ListeMaterController listeMaterController; 
    @FXML
    private TextField nameField;

    @FXML
    private TextField quantityField;

    @FXML
    private Button saveButton;

    private Connection conn;
    


    @FXML
    private void saveMaterial() {
        String name = nameField.getText().trim();
        String quantityText = quantityField.getText().trim();

        if (name.isEmpty() || quantityText.isEmpty()) {
            Alert emptyFieldsAlert = new Alert(Alert.AlertType.WARNING);
            emptyFieldsAlert.setTitle("Champs Vides");
            emptyFieldsAlert.setHeaderText(null);
            emptyFieldsAlert.setContentText("Veuillez remplir tous les champs.");
            emptyFieldsAlert.showAndWait();
            return;
        }

        try {
            int quantity = Integer.parseInt(quantityText);
            Material newMaterial = new Material(name, quantity);
            insertMaterial(newMaterial);
            clearFields();

            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Succès");
            successAlert.setHeaderText(null);
            successAlert.setContentText("Nouveau matériau ajouté avec succès.");
            successAlert.showAndWait();

            Stage stage = (Stage) saveButton.getScene().getWindow(); 
            stage.close(); 
            showPreviousInterface(stage, "/vues/ListeMater.fxml");

            if (listeMaterController != null) {
                listeMaterController.refreshMaterialList();
            } else {
                System.err.println("ListeMaterController non initialisé.");
            } 
        } catch (NumberFormatException e) {
            Alert invalidQuantityAlert = new Alert(Alert.AlertType.ERROR);
            invalidQuantityAlert.setTitle("Erreur");
            invalidQuantityAlert.setHeaderText(null);
            invalidQuantityAlert.setContentText("Veuillez entrer une quantité valide.");
            invalidQuantityAlert.showAndWait();
        }
    }

    private void insertMaterial(Material material) {
        if (conn == null) {
            System.err.println("Connexion non initialisée.");
            return;
        }
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
