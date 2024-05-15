package controllers;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.Personnel;
import DAO.DataDAO;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;

public class LoginController {

    @FXML
    private TextField personnelIdField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    private DataDAO dataDAO;

    public LoginController() {
        this.dataDAO = new DataDAO();
    }

    @FXML
    public void initialize() {
    System.out.println("Contrôleur Login initialisé.");
    }

    @FXML
    private void handleLogin() throws IOException {
        String personnelIdStr = personnelIdField.getText();
        String password = passwordField.getText();

        try {
            int personnelId = Integer.parseInt(personnelIdStr);

            Personnel personnel = dataDAO.findPersonnelById(personnelId);

            if (personnel != null && personnel.getPassword().equals(password)) {
                openMainView();
            } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erreur d'authentification");
            alert.setHeaderText(null);
            alert.setContentText("Identifiants incorrects. Veuillez réessayer.");
            alert.showAndWait();
            }
        } catch (NumberFormatException e) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Erreur d'identification");
        alert.setHeaderText(null);
        alert.setContentText("L'ID du personnel n'est pas valide : " + personnelIdStr);
        alert.showAndWait();        }
    }

    private void openMainView() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vues/ListeMater.fxml"));
        
        Parent root = loader.load();

        Scene scene = new Scene(root);
        Stage mainStage = new Stage();
        mainStage.setScene(scene);
        mainStage.setTitle("Liste des Matériaux");
        String personnelIdStr = personnelIdField.getText();
        System.out.println("ID du personnel saisi : " + personnelIdStr);

        Stage loginStage = (Stage) personnelIdField.getScene().getWindow();
        loginStage.close();

        mainStage.show();
    }
}

