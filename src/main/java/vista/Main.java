package vista;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static modelo.App.testConnection;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        testConnection();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/InterfazIniciarSesion.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setTitle("Veterinaria Antonio - Iniciar Sesion");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

  public static void main(String[] args){
        launch(args);
  }
}
