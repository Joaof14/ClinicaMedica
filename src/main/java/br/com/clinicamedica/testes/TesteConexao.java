package br.com.clinicamedica.testes;

import br.com.clinicamedica.ConexaoDB;

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