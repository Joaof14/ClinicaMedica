package br.com.clinicamedica.ui;

public class MenuFuncionario {

    public static void exibir() {
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
        System.out.println("Função: cadastrarPaciente");
        // TODO
    }

    private static void atualizarPaciente() {
        System.out.println("Função: atualizarPaciente");
        // TODO
    }

    private static void removerPaciente() {
        System.out.println("Função: removerPaciente");
        // TODO
    }

    private static void listarPacientes() {
        System.out.println("Função: listarPacientes");
        // TODO
    }

    private static void listarPacientesPorSintomas() {
        System.out.println("Função: listarPacientesPorSintomas");
        // TODO
    }

    private static void gerarConsulta() {
        System.out.println("Função: gerarConsulta");
        // TODO
    }

    private static void atualizarConsulta() {
        System.out.println("Função: atualizarConsulta");
        // TODO
    }

    private static void cancelarConsulta() {
        System.out.println("Função: cancelarConsulta");
        // TODO
    }

    private static void listarConsultas() {
        System.out.println("Função: listarConsultas");
        // TODO
    }

    private static void listarConsultasPorPaciente() {
        System.out.println("Função: listarConsultasPorPaciente");
        // TODO
    }

    private static void listarConsultasPorMedico() {
        System.out.println("Função: listarConsultasPorMedico");
        // TODO
    }

    private static void listarConsultasPorPeriodo() {
        System.out.println("Função: listarConsultasPorPeriodo");
        // TODO
    }

    private static void listarConsultasPorStatus() {
        System.out.println("Função: listarConsultasPorStatus");
        // TODO
    }
}s