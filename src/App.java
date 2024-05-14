
import controllers.*;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vues/login.fxml"));
        Parent root = loader.load();

        // Get the controller instance and call initialize method
        LoginController loginController = loader.getController();
        loginController.initialize();

        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Gestion de Matériel Médical");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
