package vista;

import modelo.App;
import modelo.Usuario;

public class Launcher {

    public static void main(String[] args) {

        modelo.App.testConnection();

        Main.main(args);
    }
}
