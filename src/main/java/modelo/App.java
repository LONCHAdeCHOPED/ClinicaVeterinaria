package modelo;

import java.sql.*;
record Paciente(int id, String nombre, String especie, int edad, String propietario) {}

public class App {

        public static Connection con;
        // 1. El metodo que contiene la lógica de conexión
        public static void testConnection () {
            String url = "jdbc:postgresql://ep-lively-sunset-aby2qoj7-pooler.eu-west-2.aws.neon.tech:5432/proyecto_alumno6?sslmode=require&channel_binding=require";
            String usuari = "neondb_owner";
            String password = "npg_3FCiZhx7VnBo";
            System.out.println("Intentando conectar a la base de datos...");
            try (Connection con = DriverManager.getConnection(url, usuari, password)) {
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
            System.out.println("--- Aplicación Veterinaria Iniciada ---");

        }

    public static void leerPacientes() {
        String sql = "SELECT nombre, especie, edad FROM pacientes";
        try{
            if(con!=null){
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM pacientes");
                while(rs.next()){
                    String nombre = rs.getString("nombre");
                    String especie = rs.getString("especie");
                    int edad = rs.getInt("edad");
                    System.out.println("🐾 " + nombre + " (" + especie + ") - Edad: " + edad);
                }
                rs.close();
                st.close();
            }
        } catch (Exception e){
            System.err.println("ERROR - No se pudo crear el Statement para lectura: " + e.getMessage());
        }
    }

}


