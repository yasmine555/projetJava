package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.Personnel;

public class LoginController implements Initializable {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    private final List<Personnel> personnelList;

    public LoginController() {
        personnelList = new ArrayList<>();
        personnelList.add(new Personnel(1, "john.jjj", "password123", "John", "jjj"));
        personnelList.add(new Personnel(2, "jane.smith", "test456", "Jane", "Smith"));
        personnelList.add(new Personnel(3, "alice.abc", "pass123", "Alice", "abc"));
        personnelList.add(new Personnel(4, "bob.xxx", "test123", "Bob", "xxx"));
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    @FXML
    private void handleLogin() throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.equals("admin") && password.equals("admin")) {
            Stage stage = (Stage) usernameField.getScene().getWindow();
            App.showListeMater(stage);
        } else {
            System.out.println("Identifiants incorrects. Veuillez réessayer.");
        }
    }

    private Personnel findPersonnelByUsername(String username) {
        for (Personnel personnel : personnelList) {
            if (personnel.getUsername().equals(username)) {
                return personnel;
            }
        }
        return null;
    }
}
