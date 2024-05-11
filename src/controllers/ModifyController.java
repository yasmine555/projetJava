package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import models.Material;
import DAO.data;
import java.sql.SQLException;

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

    public void initMaterial(Material material) {
        this.selectedMaterial = material;
        oldNameField.setText(material.getName());
        oldQuantityField.setText(String.valueOf(material.getQuantity()));
    }

    @FXML
    private void handleUpdateMaterial() {
        if (selectedMaterial != null) {
            String newName = newNameField.getText();
            int newQuantity = Integer.parseInt(newQuantityField.getText());

            selectedMaterial.setName(newName);
            selectedMaterial.setQuantity(newQuantity);

            try {
                data.updateMaterial(selectedMaterial);

                System.out.println("Material updated successfully.");
            } catch (SQLException e) {
                System.err.println("Error updating material: " + e.getMessage());
            }
        }
    }
}
