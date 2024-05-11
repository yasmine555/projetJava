

import DAO.data;
import controllers.*;
import java.io.IOException;
import java.sql.SQLException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class App extends Application {
    private static Scene scene;
    private static data db;
    @Override
    public void start(Stage primaryStage) throws Exception {
    Parent root = FXMLLoader.load(getClass().getResource("vues/login.fxml"));
    Scene scene = new Scene(root);
    primaryStage.setScene(scene);
    primaryStage.setTitle("Application de Gestion de Matériel Médical");
    primaryStage.show();

    db = new data(); 
    db.createTable();

    try {
        insertSampleData();
    } catch (SQLException e) {
        System.err.println("Erreur lors de l'insertion de données d'exemple : " + e.getMessage());
    }
}
    private void insertSampleData() throws SQLException {
        db.insertMaterial("Stéthoscope", 10);
        db.insertMaterial("Thermomètre", 20);
        db.insertMaterial("Bistouri", 15);
        db.insertMaterial("Oxymètre de pouls", 8);
        db.insertMaterial("Gants médicaux (paire)", 100);
        db.insertMaterial("Garrot", 25);
        db.insertMaterial("Masque chirurgical", 50);
        db.insertMaterial("Seringue", 30);
        db.insertMaterial("Coton hydrophile", 200);
        db.insertMaterial("Pansement stérile", 40);
        db.insertMaterial("Bande élastique", 60);
        db.insertMaterial("Lampe frontale médicale", 12);
        db.insertMaterial("Compresse", 80);
        db.insertMaterial("Ciseaux médicaux", 18);
        db.insertMaterial("Désinfectant", 35);
        db.insertMaterial("Gel d'alcool", 70);
        db.insertMaterial("Attelle", 22);
        db.insertMaterial("Couverture de survie", 45);
        db.insertMaterial("Gaze", 150);
        db.insertMaterial("Filtre à particules", 30);
        db.insertMaterial("Gouttes ophtalmiques", 55);
        db.insertMaterial("Canule nasale", 25);
        db.insertMaterial("Pince à épiler", 20);
        System.out.println("Sample data inserted successfully into the database.");
    }
    
    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    
    public static void main(String[] args) {

        launch();
    }
        public static void showLogin(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("vues/login.fxml"));
        loader.setController(new LoginController());
        scene = new Scene(loader.load());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Application de Gestion de Matériel Médical");
        primaryStage.show();
    }

    public static void showListeMater(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("vues/ListeMater.fxml"));
        loader.setController(new ListeMaterController());
        scene = new Scene(loader.load());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Liste de Matériel");
        primaryStage.show();
    }

    public static void showNewMaterial(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("vues/newMaterial.fxml"));
        loader.setController(new newController());
        scene = new Scene(loader.load());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Ajouter un Matériel");
        primaryStage.show();
    }
        public static void showModifyMaterial(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("/views/modify.fxml"));
            loader.setController(new ModifyController());
            Parent root = loader.load();

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Modifier un Matériel");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
