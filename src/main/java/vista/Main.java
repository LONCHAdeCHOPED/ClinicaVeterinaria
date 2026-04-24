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
            // 1. Cargamos el archivo FXML desde la carpeta de recursos
            // Nota: La ruta debe empezar por / y coincidir con la jerarquía de carpetas
            FXMLLoader loader = new
                    FXMLLoader(getClass().getResource("/InterfazIniciarSesion.fxml"));
            Parent root = loader.load();
            // 2. Creamos la "Escena" (el contenido de la ventana)
            Scene scene = new Scene(root);
            // 3. Configuramos el "Escenario" (la ventana en sí)
            primaryStage.setTitle("Veterinaria Antonio - Iniciar Sesion");
            primaryStage.setScene(scene);
            // 4. Hacemos que la ventana sea visible
            primaryStage.show();
        } catch (Exception e) {
            // Es vital capturar errores aquí por si la ruta del FXML está mal
            e.printStackTrace();
        }
    }

  public static void main(String[] args){
        launch(args);
  }
}
