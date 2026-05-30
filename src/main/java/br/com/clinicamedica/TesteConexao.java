package br.com.clinicamedica;
import java.sql.Connection;

public class TesteConexao {
    public static void main(String[] args) {
        try (Connection conn = ConexaoDB.obterConexao()) {
            System.out.println("Conexão OK!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}