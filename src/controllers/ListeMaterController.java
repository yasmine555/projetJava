import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import models.Material;

public class ListeMaterController {

    @FXML
    private TableView<Material> materialTableView;

    @FXML
    private TableColumn<Material, String> nameColumn;

    @FXML
    private TableColumn<Material, Integer> quantityColumn;

    @FXML
    private TextField searchField;

    private ObservableList<Material> materialList;

    public void initialize() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        // Simulation de données
        List<Material> dataList = new ArrayList<>();
        dataList.add(new Material("Stéthoscope", 10));
        dataList.add(new Material("Thermomètre", 20));
        // Ajoutez d'autres matériaux ici...

        materialList = FXCollections.observableArrayList(dataList);
        materialTableView.setItems(materialList);
    }

    @FXML
    private void handleNewMaterial() throws IOException {
        Stage stage = (Stage) materialTableView.getScene().getWindow();
        App.showNewMaterial(stage);
    }

    @FXML
    private void handleModifyMaterial() {
        Material selectedMaterial = materialTableView.getSelectionModel().getSelectedItem();
        if (selectedMaterial != null) {
            Stage stage = (Stage) materialTableView.getScene().getWindow();
            App.showModifyMaterial(stage);
        }
    }

    @FXML
    private void handleSearch() {
        String searchText = searchField.getText().trim().toLowerCase();
        if (searchText.isEmpty()) {
            materialTableView.setItems(materialList);
        } else {
            ObservableList<Material> filteredList = FXCollections.observableArrayList();
            for (Material material : materialList) {
                if (material.getName().toLowerCase().contains(searchText)) {
                    filteredList.add(material);
                }
            }
            materialTableView.setItems(filteredList);
        }
    }
}
