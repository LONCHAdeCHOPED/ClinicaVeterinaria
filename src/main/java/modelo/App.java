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
                System.out.println("  3. Leer pacientes");
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
                        case 3:
                            leerPacientes();
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

    // Dentro de modelo.App
    public static boolean validarUsuario(String user, String pass) {
        // Hemos ajustado los nombres a: nombreusuario y contraseña
        String sql = "SELECT * FROM usuario WHERE nombreusuario = ? AND contraseña = ?";

        try {
            if (con == null || con.isClosed()) testConnection();

            PreparedStatement ps = con.prepareStatement(sql);
            // Usamos trim() por si la base de datos añadió espacios en blanco (tipo CHAR)
            ps.setString(1, user.trim());
            ps.setString(2, pass.trim());

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                System.out.println("✅ Usuario encontrado: " + rs.getString("nombreusuario"));
                return true;
            }
        } catch (SQLException e) {
            System.err.println("❌ Error en la consulta SQL: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }


    public static void crearUsuario(String nombre, String password, String rol) {
        // 1. Buscamos el último ID para calcular el siguiente
        int nuevoId = obtenerUltimoId() + 1;

        // 2. Ahora incluimos el idusuario en la sentencia SQL
        String sql = "INSERT INTO usuario (idusuario, nombreusuario, rol, contraseña) VALUES (?, ?, ?, ?)";

        try {
            if (con == null || con.isClosed()) testConnection();

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, nuevoId);      // Enviamos el ID calculado
            ps.setString(2, nombre);
            ps.setString(3, rol);
            ps.setString(4, password);

            int filas = ps.executeUpdate();
            if (filas > 0) {
                System.out.println("✅ Usuario creado con ID: " + nuevoId);
            }
            ps.close();
        } catch (SQLException e) {
            System.err.println("❌ Error al insertar: " + e.getMessage());
        }
    }

    // Función auxiliar para saber por qué número vamos
    private static int obtenerUltimoId() {
        String sql = "SELECT MAX(idusuario) FROM usuario";
        try (Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt(1); // Retorna el número más alto
            }
        } catch (SQLException e) {
            System.err.println("Error consultando último ID: " + e.getMessage());
        }
        return 0; // Si la tabla está vacía, empezamos desde 0 (+1 = 1)
    }
    /*
    public static void crearTablas(){
        String sql = """

            """;
        try {
            if (con != null) {
                Statement st = con.createStatement();
                st.execute(sql);
                st.close();
                System.out.println("✅ Tabla '' creada o ya existente.");
            }
        } catch (SQLException e) {
            System.err.println("ERROR - No se pudo crear la tabla: " + e.getMessage());
        }
    } */

}


