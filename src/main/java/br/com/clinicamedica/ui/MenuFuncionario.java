package br.com.clinicamedica.ui;

import br.com.clinicamedica.Funcionario;

public class MenuFuncionario {

    public static void exibir(Funcionario funcionarioLogado) {
        int opcao;

        do {
            System.out.println();
            System.out.println("|-------------- MENU FUNCIONÁRIO ------------------|");
            System.out.println("|  1 · Cadastrar paciente                          |");
            System.out.println("|  2 · Atualizar paciente                          |");
            System.out.println("|  3 · Remover paciente                            |");
            System.out.println("|  4 · Listar pacientes                            |");
            System.out.println("|  5 · Listar pacientes por sintomas               |");
            System.out.println("|  6 · Gerar consulta                              |");
            System.out.println("|  7 · Atualizar consulta                          |");
            System.out.println("|  8 · Cancelar consulta                           |");
            System.out.println("|  9 · Listar consultas                            |");
            System.out.println("| 10 · Listar consultas por paciente               |");
            System.out.println("| 11 · Listar consultas por médico                 |");
            System.out.println("| 12 · Listar consultas por período                |");
            System.out.println("| 13 · Listar consultas por status                 |");
            System.out.println("|  0 · Voltar                                      |");
            System.out.println("|--------------------------------------------------|");

            opcao = Utils.lerInteiro("Escolha uma opção: ");

            switch (opcao) {
                case 1 -> cadastrarPaciente();
                case 2 -> atualizarPaciente();
                case 3 -> removerPaciente();
                case 4 -> listarPacientes();
                case 5 -> listarPacientesPorSintomas();
                case 6 -> gerarConsulta();
                case 7 -> atualizarConsulta();
                case 8 -> cancelarConsulta();
                case 9 -> listarConsultas();
                case 10 -> listarConsultasPorPaciente();
                case 11 -> listarConsultasPorMedico();
                case 12 -> listarConsultasPorPeriodo();
                case 13 -> listarConsultasPorStatus();
                case 0 -> { }
                default -> Utils.msgOpcaoInvalida();
            }
        } while (opcao != 0);
    }

    private static void cadastrarPaciente() {
        Utils.limparTela();
        System.out.println("Função: cadastrarPaciente");
        // TODO
        Utils.pausar();
    }

    private static void atualizarPaciente() {
        Utils.limparTela();
        System.out.println("Função: atualizarPaciente");
        // TODO
        Utils.pausar();
    }

    private static void removerPaciente() {
        Utils.limparTela();
        System.out.println("Função: removerPaciente");
        // TODO
        Utils.pausar();
    }

    private static void listarPacientes() {
        Utils.limparTela();
        System.out.println("Função: listarPacientes");
        // TODO
        Utils.pausar();
    }

    private static void listarPacientesPorSintomas() {
        Utils.limparTela();
        System.out.println("Função: listarPacientesPorSintomas");
        // TODO
        Utils.pausar();
    }

    private static void gerarConsulta() {
        Utils.limparTela();
        System.out.println("Função: gerarConsulta");
        // TODO
        Utils.pausar();
    }

    private static void atualizarConsulta() {
        Utils.limparTela();
        System.out.println("Função: atualizarConsulta");
        // TODO
        Utils.pausar();
    }

    private static void cancelarConsulta() {
        Utils.limparTela();
        System.out.println("Função: cancelarConsulta");
        // TODO
        Utils.pausar();
    }

    private static void listarConsultas() {
        Utils.limparTela();
        System.out.println("Função: listarConsultas");
        // TODO
        Utils.pausar();
    }

    private static void listarConsultasPorPaciente() {
        Utils.limparTela();
        System.out.println("Função: listarConsultasPorPaciente");
        // TODO
        Utils.pausar();
    }

    private static void listarConsultasPorMedico() {
        Utils.limparTela();
        System.out.println("Função: listarConsultasPorMedico");
        // TODO
        Utils.pausar();
    }

    private static void listarConsultasPorPeriodo() {
        Utils.limparTela();
        System.out.println("Função: listarConsultasPorPeriodo");
        // TODO
        Utils.pausar();
    }

    private static void listarConsultasPorStatus() {
        Utils.limparTela();
        System.out.println("Função: listarConsultasPorStatus");
        // TODO
        Utils.pausar();
    }
}