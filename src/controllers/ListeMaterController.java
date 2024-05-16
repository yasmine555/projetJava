package controllers;

import DAO.DataDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.Material;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ListeMaterController {

    @FXML
    private TableView<Material> materialTableView;

    @FXML
    private TableColumn<Material, String> nameColumn;

    @FXML
    private TableColumn<Material, Integer> quantityColumn;

    @FXML
    private TextField searchField;

    @FXML
    private Button newMaterialButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button modifyButton;

    private ObservableList<Material> materialList;
    private ObservableList<Material> filteredMaterialList;

    public void initialize() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        loadMaterialData();

        filteredMaterialList = FXCollections.observableArrayList(materialList);
        materialTableView.setItems(filteredMaterialList);

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filterMaterialList(newValue);
        });
    }

    private void loadMaterialData() {
        List<Material> dataList = new ArrayList<>();
        dataList.add(new Material("Stéthoscope", 10));
        dataList.add(new Material("Thermomètre", 20));
        dataList.add(new Material("Bistouri", 15));
        dataList.add(new Material("Oxymètre de pouls", 8));
        dataList.add(new Material("Gants médicaux (paire)", 100));
        dataList.add(new Material("Garrot", 25));
        dataList.add(new Material("Masque chirurgical", 50));
        dataList.add(new Material("Seringue", 30));
        dataList.add(new Material("Coton hydrophile", 200));
        dataList.add(new Material("Pansement stérile", 40));
        dataList.add(new Material("Bande élastique", 60));
        dataList.add(new Material("Lampe frontale médicale", 12));
        dataList.add(new Material("Compresse", 80));
        dataList.add(new Material("Ciseaux médicaux", 18));
        dataList.add(new Material("Désinfectant", 35));
        dataList.add(new Material("Gel d'alcool", 70));
        dataList.add(new Material("Attelle", 22));
        dataList.add(new Material("Couverture de survie", 45));
        dataList.add(new Material("Gaze", 150));
        dataList.add(new Material("Filtre à particules", 30));
        dataList.add(new Material("Gouttes ophtalmiques", 55));
        dataList.add(new Material("Canule nasale", 25));
        dataList.add(new Material("Pince à épiler", 20));

        materialList = FXCollections.observableArrayList(dataList);
        materialTableView.setItems(materialList);
    }

    private void filterMaterialList(String searchText) {
        String lowerCaseFilter = searchText.toLowerCase();
        ObservableList<Material> filteredList = FXCollections.observableArrayList();

        for (Material material : materialList) {
            if (material.getName().toLowerCase().contains(lowerCaseFilter)) {
                filteredList.add(material);
            }
        }

        filteredMaterialList.setAll(filteredList);
    }

    @FXML
    private void handleNewMaterial() throws IOException {
        Stage stage = (Stage) materialTableView.getScene().getWindow();
        DataDAO.showNewMaterial(stage);
        refreshMaterialList();
    }

    @FXML
    private void handleModify() throws IOException {
        Material selectedMaterial = materialTableView.getSelectionModel().getSelectedItem();
        if (selectedMaterial != null) {
            Stage stage = (Stage) materialTableView.getScene().getWindow();
            DataDAO.showModifyMaterial(stage);
            refreshMaterialList();
        }
    }

    @FXML
    private void handleDelete() {
        Material selectedMaterial = materialTableView.getSelectionModel().getSelectedItem();
        if (selectedMaterial != null) {
            materialList.remove(selectedMaterial);
            filteredMaterialList.remove(selectedMaterial); 
            DataDAO.deleteMaterial(selectedMaterial); 
            materialTableView.refresh(); 
        }
    }

    @FXML
    private void handleSearch() {
        String searchText = searchField.getText().trim().toLowerCase();
        filterMaterialList(searchText);
    }

    void refreshMaterialList() {
        loadMaterialData();
        filterMaterialList(searchField.getText().trim().toLowerCase()); 
    }
}
