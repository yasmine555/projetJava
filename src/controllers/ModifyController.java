package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.Material;
import DAO.DataDAO;

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
                showAlert(Alert.AlertType.WARNING, "Champs manquants", "Veuillez remplir tous les champs.");
                return;
            }

            try {
                int newQuantity = Integer.parseInt(newQuantityText);

                // Mettre à jour les informations du matériel sélectionné
                selectedMaterial.setName(newName);
                selectedMaterial.setQuantity(newQuantity);

                // Appeler la méthode de mise à jour dans DataDAO
                DataDAO dataDAO = new DataDAO();
                dataDAO.updateMaterial(selectedMaterial);

                // Afficher un message de succès
                showAlert(Alert.AlertType.INFORMATION, "Succès", "Matériau mis à jour avec succès.");

                // Rafraîchir la TableView dans ListeMaterController
                Stage stage = (Stage) update_material.getScene().getWindow();
                ListeMaterController controller = DataDAO.showListeMater(stage);
                if (controller != null) {
                    controller.refreshMaterialList();
                }

                // Fermer la fenêtre de modification
                stage.close();

            } catch (NumberFormatException e) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez entrer une quantité valide.");
            }
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

}
