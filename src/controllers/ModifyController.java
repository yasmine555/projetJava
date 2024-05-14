package controllers;

import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
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

    public void initMaterial(Material material) {
        this.selectedMaterial = material;
        oldNameField.setText(material.getName());
        oldQuantityField.setText(String.valueOf(material.getQuantity()));
    }

    @FXML
    private void handleUpdateMaterial() {
        if (selectedMaterial != null) {
            String newName = newNameField.getText();
            String newQuantityText = newQuantityField.getText();

            try {
                int newQuantity = Integer.parseInt(newQuantityText);

                // Update material details
                selectedMaterial.setName(newName);
                selectedMaterial.setQuantity(newQuantity);

                // Update material in the database
                DataDAO dataDAO = new DataDAO();
                dataDAO.updateMaterial(selectedMaterial);

                System.out.println("Material updated successfully.");
            } catch (NumberFormatException e) {
                System.err.println("Invalid quantity format: " + newQuantityText);
            }
        }
    }
}
