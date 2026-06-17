package br.com.clinicamedica.ui;

import java.util.List;

import br.com.clinicamedica.Funcionario;
import br.com.clinicamedica.Paciente;

public class GerenciarPacientesUI {
    public static void exibir(){
        int opcao;
        do {
            System.out.println();
            System.out.println("|---------- GERENCIAMENTO DE PACIENTES ------------|");
            System.out.println("|  1 · Cadastrar paciente                          |");
            System.out.println("|  2 · Atualizar paciente                          |");
            System.out.println("|  3 · Remover paciente                            |");
            System.out.println("|  4 · Listar pacientes                            |");
            System.out.println("|  5 · Listar pacientes por sintomas               |");
            System.out.println("|  0 · Voltar                                      |");
            System.out.println("|--------------------------------------------------|");
            opcao = Utils.lerInteiro("Escolha uma opção: ");

            switch (opcao) {
                case 1 -> cadastrarPaciente();
                case 2 -> atualizarPaciente();
                case 3 -> removerPaciente();
                case 4 -> listarPacientes();
                case 5 -> listarPacientesPorSintomas();
                case 0 -> { }
                default -> Utils.msgOpcaoInvalida();
            }
        } while (opcao != 0);

    }


    private static void cadastrarPaciente() {
        Utils.limparTela();
        System.out.println("=== CADASTRAR PACIENTE ===");

        try {
            String nome = Utils.lerTexto("Nome: ");
            int idade = Utils.lerInteiro("Idade: ");
            String sexo = Utils.lerTexto("Sexo: ");
            String cpf = Utils.lerTexto("CPF: ");
            String telefone = Utils.lerTexto("Telefone: ");
            String login = Utils.lerTexto("Login: ");
            String senha = Utils.lerTexto("Senha: ");
            float peso = Float.parseFloat(Utils.lerTexto("Peso: "));
            float altura = Float.parseFloat(Utils.lerTexto("Altura: "));
            String sintomas = Utils.lerTexto("Sintomas: ");

            Paciente paciente = Paciente.cadastrarPaciente(
                nome, idade, sexo, cpf, telefone, login, senha,
                true, peso, altura, sintomas
            );

            if (paciente != null) {
                System.out.println("\nPaciente cadastrado com sucesso.");
            } else {
                System.out.println("\nNão foi possível cadastrar o paciente.");
            }
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar paciente: " + e.getMessage());
        }

        Utils.pausar();
    }

    private static void atualizarPaciente() {
        Utils.limparTela();
        System.out.println("=== ATUALIZAR PACIENTE ===");

        try {
            String cpfBusca = Utils.lerTexto("Informe o CPF do paciente: ");
            Paciente paciente = buscarPacientePorCpf(cpfBusca);

            if (paciente == null) {
                System.out.println("Paciente não encontrado.");
            } else {
                String nome = Utils.lerTexto("Novo nome: ");
                int idade = Utils.lerInteiro("Nova idade: ");
                String sexo = Utils.lerTexto("Novo sexo: ");
                String cpf = Utils.lerTexto("Novo CPF: ");
                String telefone = Utils.lerTexto("Novo telefone: ");
                String login = Utils.lerTexto("Novo login: ");
                String senha = Utils.lerTexto("Nova senha: ");
                float altura = Float.parseFloat(Utils.lerTexto("Nova altura: "));
                float peso = Float.parseFloat(Utils.lerTexto("Novo peso: "));
                String sintomas = Utils.lerTexto("Novos sintomas: ");

                paciente.atualizarPaciente(
                    paciente, nome, idade, sexo, cpf, telefone, login, senha,
                    true, altura, peso, sintomas
                );
            }
        } catch (Exception e) {
            System.out.println("Erro ao atualizar paciente: " + e.getMessage());
        }

        Utils.pausar();
    }

    private static void removerPaciente() {
        Utils.limparTela();
        System.out.println("=== REMOVER PACIENTE ===");

        try {
            String cpfBusca = Utils.lerTexto("Informe o CPF do paciente: ");
            Paciente paciente = buscarPacientePorCpf(cpfBusca);

            if (paciente == null) {
                System.out.println("Paciente não encontrado.");
            } else {
                paciente.deletarPaciente(paciente);
            }
        } catch (Exception e) {
            System.out.println("Erro ao remover paciente: " + e.getMessage());
        }

        Utils.pausar();
    }

    private static void listarPacientes() {
        Utils.limparTela();
        System.out.println("=== LISTAR PACIENTES ===");

        try {
            List<Paciente> pacientes = Paciente.listarPacientes();

            if (pacientes.isEmpty()) {
                System.out.println("Nenhum paciente cadastrado.");
            } else {
                for (Paciente paciente : pacientes) {
                    paciente.verPaciente();
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao listar pacientes: " + e.getMessage());
        }

        Utils.pausar();
    }

    private static void listarPacientesPorSintomas() {
        Utils.limparTela();
        System.out.println("=== LISTAR PACIENTES POR SINTOMAS ===");

        try {
            String sintomas = Utils.lerTexto("Informe o sintoma para busca: ");
            List<Paciente> pacientes = Paciente.listarPacientesPorSintomas(sintomas);

            if (pacientes.isEmpty()) {
                System.out.println("Nenhum paciente encontrado com esse sintoma.");
            } else {
                for (Paciente paciente : pacientes) {
                    paciente.verPaciente();
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao listar pacientes por sintomas: " + e.getMessage());
        }

        Utils.pausar();
    }


    private static Paciente buscarPacientePorCpf(String cpf) {
        List<Paciente> pacientes = Paciente.listarPacientes();

        for (Paciente paciente : pacientes) {
            if (paciente.getCpf().equals(cpf)) {
                return paciente;
            }
        }

        return null;
    }

}