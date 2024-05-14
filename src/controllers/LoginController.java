package controllers;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.Personnel;
import DAO.DataDAO;

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
    private void handleLogin() {
        String personnelIdStr = personnelIdField.getText();
        String password = passwordField.getText();

        try {
            int personnelId = Integer.parseInt(personnelIdStr);

            Personnel personnel = dataDAO.findPersonnelById(personnelId);

            if (personnel != null && personnel.getPassword().equals(password)) {
                openMainView();
            } else {
                System.out.println("Identifiants incorrects. Veuillez réessayer.");
            }
        } catch (NumberFormatException e) {
            System.err.println("L'ID du personnel n'est pas un entier valide : " + personnelIdStr);
        } catch (IOException e) {
            System.err.println("Erreur d'accès à la base de données : " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Une erreur est survenue : " + e.getMessage());
            e.printStackTrace();
        }
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
