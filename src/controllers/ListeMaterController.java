package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import models.Material;
import DAO.data;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.scene.control.cell.PropertyValueFactory;

public class ListeMaterController implements Initializable {

    @FXML
    private TableView<Material> materialTableView;

    @FXML
    private TableColumn<Material, String> nameColumn;

    @FXML
    private TableColumn<Material, Integer> quantityColumn;

    @FXML
    private TextField searchField;

    private ObservableList<Material> materialList;
    private data materialData;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        materialData = new data();

        try {
            refreshMaterialList();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Listener pour filtrer la liste lors de la saisie dans le champ de recherche
        searchField.textProperty().addListener((observable, oldValue, newValue) -> filterMaterialList(newValue));
    }

    private void refreshMaterialList() throws SQLException {
        materialList = FXCollections.observableArrayList();
        materialList.addAll(materialData.getAllMaterials());
        materialTableView.setItems(materialList);
    }

    private void filterMaterialList(String keyword) {
        ObservableList<Material> filteredList = FXCollections.observableArrayList();

        for (Material material : materialList) {
            if (material.getName().toLowerCase().contains(keyword.toLowerCase())) {
                filteredList.add(material);
            }
        }

        materialTableView.setItems(filteredList);
    }

    @FXML
    private void deleteSelectedMaterial() throws SQLException {
        Material selectedMaterial = materialTableView.getSelectionModel().getSelectedItem();

        if (selectedMaterial != null) {
            materialData.deleteMaterial(selectedMaterial.getName());
            refreshMaterialList(); // Rafraîchir la liste après la suppression
        }
    }

    @FXML
    private void updateMaterialQuantity() {
        Material selectedMaterial = materialTableView.getSelectionModel().getSelectedItem();

        if (selectedMaterial != null) {
            TextInputDialog dialog = new TextInputDialog(String.valueOf(selectedMaterial.getQuantity()));
            dialog.setTitle("Update Quantity");
            dialog.setHeaderText("Enter new quantity for " + selectedMaterial.getName());
            dialog.setContentText("New Quantity:");

            dialog.showAndWait().ifPresent(newQuantity -> {
                try {
                    int quantity = Integer.parseInt(newQuantity);
                    materialData.updateMaterialQuantity(selectedMaterial.getName(), quantity);
                    refreshMaterialList(); // Rafraîchir la liste après la mise à jour
                } catch (NumberFormatException | SQLException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    @FXML
    private void handleNewMaterial() {
        // Code pour gérer l'ajout d'un nouveau matériau
        // (ouvrir une fenêtre de dialogue pour saisir les détails du nouveau matériau, etc.)
    }

    @FXML
    private void handleDelete() {
        // Code pour gérer la suppression d'un matériau sélectionné
        Material selectedMaterial = materialTableView.getSelectionModel().getSelectedItem();

        if (selectedMaterial != null) {
            try {
                materialData.deleteMaterial(selectedMaterial.getName());
                refreshMaterialList(); // Rafraîchir la liste après la suppression
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void handleModify() {
        // Code pour gérer la modification d'un matériau sélectionné
        Material selectedMaterial = materialTableView.getSelectionModel().getSelectedItem();

        if (selectedMaterial != null) {
            TextInputDialog dialog = new TextInputDialog(String.valueOf(selectedMaterial.getQuantity()));
            dialog.setTitle("Modify Material");
            dialog.setHeaderText("Enter new details for " + selectedMaterial.getName());
            dialog.setContentText("New Quantity:");

            dialog.showAndWait().ifPresent(newQuantity -> {
                try {
                    int quantity = Integer.parseInt(newQuantity);
                    materialData.updateMaterialQuantity(selectedMaterial.getName(), quantity);
                    refreshMaterialList(); // Rafraîchir la liste après la mise à jour
                } catch (NumberFormatException | SQLException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
