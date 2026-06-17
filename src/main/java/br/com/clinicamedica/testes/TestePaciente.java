package br.com.clinicamedica.testes;

import java.util.List;

import br.com.clinicamedica.Paciente;

public class TestePaciente {
    public static void main(String[] args) {

        System.out.println("TESTE CADASTRO");
        Paciente novoPaciente = Paciente.cadastrarPaciente(
                "Maria Santos", 30, "Feminino", "12345678901",
                "84999990000", "maria.santos", "senha456", true,
                60.5f, 1.62f, "Dor de cabeça frequente");

        if (novoPaciente != null) {
            novoPaciente.verPaciente();

            System.out.println("TESTE ATUALIZAR");
            novoPaciente.atualizarPaciente(novoPaciente, "Maria Santos Silva", 31, "Feminino", "12345678901",
                    "84999990001", "maria.santos", "senha456", true, 1.62f, 62.0f, "Enxaqueca");
            novoPaciente.verPaciente();

            System.out.println("TESTE DELETAR");
            novoPaciente.deletarPaciente(novoPaciente);
        }

        System.out.println("TESTE LISTAR");
        List<Paciente> listaPacientes = Paciente.listarPacientes();
        for (Paciente p : listaPacientes) {
            System.out.println(p);
        }

        System.out.println("TESTE LISTAGEM POR SINTOMAS");
        List<Paciente> listaSintomas = Paciente.listarPacientesPorSintomas("Enxaqueca");
        for (Paciente p : listaSintomas) {
            System.out.println(p);
        }
    }
}