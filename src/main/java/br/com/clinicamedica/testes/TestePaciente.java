package br.com.clinicamedica.testes;

import java.util.List;

import br.com.clinicamedica.Paciente;

public class TestePaciente {
    public static void main(String[] args) {

        System.out.println("TESTE CADASTRO");
        Paciente novoPaciente = Paciente.cadastrarPaciente(
            "Maria Santos", 30, "Feminino", "12345678901", 
            "84999990000", "maria.santos", "senha456", true,
            60.5f, 1.62f, "Dor de cabeça frequente"
        );
        
        if (novoPaciente != null) {
            novoPaciente.verPaciente();
        }
        
        // Teste de listagem
        
        
        // Teste de busca por CPF
        
        
        // Teste de atualização
        
        
        // Teste de listagem por sintomas
        
    }
}