package br.com.clinicamedica.testes;

import br.com.clinicamedica.Paciente;
import java.util.List;

public class TestePaciente {
    public static void main(String[] args) {
        
        // Teste de cadastro
        System.out.println("=== TESTE CADASTRO ===");
        Paciente novoPaciente = Paciente.cadastrarPaciente(
            1.7F, 50F, "Dor de cabeça frequente", "Maria Santos", 20, 
            "feminino", "senha456", "84999990000", "maria.santos", "12345678901", true
        );
        
        if (novoPaciente != null) {
            novoPaciente.verPaciente();
        }
        
        // Teste de listagem
        
        // Teste de atualização
        
        // Teste de listagem por sintomas
        
        
    }
}