package modelo;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;




public class App {

        public static Connection con;

        static ArrayList<Paciente> listaPacientes = new ArrayList<>();
        // 1. El metodo que contiene la lógica de conexión
        public static void testConnection () {
            String url = "jdbc:postgresql://ep-lively-sunset-aby2qoj7-pooler.eu-west-2.aws.neon.tech:5432/proyecto_alumno6?sslmode=require&channel_binding=require";
            String usuari = "neondb_owner";
            String password = "npg_3FCiZhx7VnBo";
            System.out.println("Intentando conectar a la base de datos...");
            try{
                con = DriverManager.getConnection(url, usuari, password);
                if (con != null && !con.isClosed()) {
                    System.out.println("ÉXITO: Conexión establecida con proyecto_alumno6");
                }
            } catch (SQLException e) {
                System.err.println("ERROR de conexión: " + e.getMessage());
                e.printStackTrace();
            }
        }

        // 2. El punto de entrada que llama al metodo anterior
        public static void main (String[] args){
            // Ahora el IDE encontrará el metodo porque está definido justo arriba
            testConnection();
            boolean salir = false;
            Scanner scanner = new Scanner(System.in);
            System.out.println("--- Aplicación Veterinaria Iniciada ---");

            while (!salir) {
                System.out.println("\nMENU PRINCIPAL:");
                System.out.println("  1. Insertar datos en la tabla Pacientes");
                System.out.println("  2. No insertar datos");
                System.out.print("Selecciona una opción: ");

                try {
                    int opcion = scanner.nextInt();
                    scanner.nextLine();

                    switch (opcion) {
                        case 1:
                            insertIntoPacientes();
                            break;
                        case 2:
                            salir = true;
                            System.out.println("No se han insertado datos");
                            break;
                        default:
                            System.out.println("-> Opción no válida.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("-> Error: Debes introducir un número. Inténtalo de nuevo.");
                    scanner.nextLine();
                }
            }
            scanner.close();

        }

    public static void leerPacientes() {
        String sql = "SELECT * FROM pacientestest";
        try{
            if (con != null) {
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(sql);
                System.out.println("\n--- LISTADO DE PACIENTES (Usando Records) ---");
                while (rs.next()) {
                    // 1. Creamos el objeto Paciente con los datos de la BD
                    Paciente p = new Paciente(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getString("especie"),
                            rs.getInt("edad"),
                            rs.getString("propietario")
                    );
                    // 2. Ahora 'p' es un objeto. Podemos imprimirlo directamente
                    // gracias al toString() autom·tico de los Records.
                    System.out.println(p);
                    listaPacientes.add(p);
                }
                rs.close();
                st.close();
            }
        } catch (Exception e){
            System.err.println("ERROR - No se pudo crear el Statement para lectura: " + e.getMessage());
        }
    }


    public static void insertIntoPacientes() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Dime el nombre del paciente: ");
        String nombre = scanner.nextLine();
        System.out.print("Dime la especie del paciente: ");
        String especie = scanner.nextLine();
        System.out.print("Dime la edad del paciente: ");
        int edad = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Dime el propietario del paciente: ");
        String propietario = scanner.nextLine();

        String sql = "INSERT INTO pacientestest (nombre, especie, edad, propietario) VALUES ('"+ nombre +"', '"+ especie + "', "+edad+", '"+ propietario +"')";
        try {
            if (con != null) {
                Statement st = con.createStatement();
                st.executeUpdate(sql);
                st.close();
            }
        } catch (Exception e){
            System.err.println("ERROR - No se pudo crear el Statement para introducir datos: " + e.getMessage());
        }
    }

    public static void crearTablaHistorial() {
        String sql = """
            CREATE TABLE IF NOT EXISTS HistorialClinico (
                id          SERIAL PRIMARY KEY,
                fecha       DATE NOT NULL,
                sintomas    TEXT NOT NULL,
                diagnostico TEXT NOT NULL,
                tratamiento TEXT NOT NULL,
                observaciones TEXT NOT NULL,
                idPaciente INT
                CONSTRAINT fk_historial_paciente FOREIGN KEY (idPaciente) REFERENCES Paciente(idPaciente) ON DELETE CASCADE
            );
            """;
        try {
            if (con != null) {
                Statement st = con.createStatement();
                st.execute(sql);
                st.close();
                System.out.println("✅ Tabla 'pacientestest' creada o ya existente.");
            }
        } catch (SQLException e) {
            System.err.println("ERROR - No se pudo crear la tabla: " + e.getMessage());
        }
    }
}


