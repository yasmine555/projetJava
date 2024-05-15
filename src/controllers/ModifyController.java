package controllers;

import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import models.Material;
import DAO.DataDAO;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

public class ModifyController {

    private Material selectedMaterial;

    @FXML
    private TextField oldNameField;

    @FXML
    private TextField newNameField;

    @FXML
    private TextField oldQuantityField;

    @FXML
    private TextField newQuantityField;
    
    @FXML
    private Button update_material;

    public void initMaterial(Material material) {
        this.selectedMaterial = material;
        oldNameField.setText(material.getName());
        oldQuantityField.setText(String.valueOf(material.getQuantity()));
    }

    @FXML

   private void handleUpdateMaterial() {
    if (selectedMaterial != null) {
        String newName = newNameField.getText().trim();
        String newQuantityText = newQuantityField.getText().trim();

        if (newName.isEmpty() || newQuantityText.isEmpty()) {
            Alert emptyFieldsAlert = new Alert(Alert.AlertType.WARNING);
            emptyFieldsAlert.setTitle("Champs manquants");
            emptyFieldsAlert.setHeaderText(null);
            emptyFieldsAlert.setContentText("Veuillez remplir tous les champs.");
            emptyFieldsAlert.showAndWait();
            return;
        }

        try {
            int newQuantity = Integer.parseInt(newQuantityText);

            // Mettre à jour les informations du matériau sélectionné
            selectedMaterial.setName(newName);
            selectedMaterial.setQuantity(newQuantity);

            DataDAO dataDAO = new DataDAO();
            dataDAO.updateMaterial(selectedMaterial);

            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Succès");
            successAlert.setHeaderText(null);
            successAlert.setContentText("Matériau mis à jour avec succès.");
            successAlert.showAndWait();

            System.out.println("Material updated successfully.");
        } catch (NumberFormatException e) {
            Alert invalidQuantityAlert = new Alert(Alert.AlertType.ERROR);
            invalidQuantityAlert.setTitle("Erreur");
            invalidQuantityAlert.setHeaderText(null);
            invalidQuantityAlert.setContentText("Veuillez entrer une quantité valide.");
            invalidQuantityAlert.showAndWait();

            System.err.println("Invalid quantity format: " + newQuantityText);
        }
    }
}

}
