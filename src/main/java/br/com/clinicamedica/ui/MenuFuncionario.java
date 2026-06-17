package br.com.clinicamedica.ui;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import br.com.clinicamedica.Consulta;
import br.com.clinicamedica.Funcionario;
import br.com.clinicamedica.Paciente;
import br.com.clinicamedica.Status;

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
        System.out.println("=== LISTAR CONSULTAS ===");

        try {
            List<Consulta> consultas = Consulta.listarConsultas();

            if (consultas.isEmpty()) {
                System.out.println("Nenhuma consulta cadastrada.");
            } else {
                for (Consulta consulta : consultas) {
                    consulta.verConsulta();
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao listar consultas: " + e.getMessage());
        }

        Utils.pausar();
    }

    private static void listarConsultasPorPaciente() {
        Utils.limparTela();
        System.out.println("=== LISTAR CONSULTAS POR PACIENTE ===");

        try {
            String cpf = Utils.lerTexto("Informe o CPF do paciente: ");
            List<Consulta> consultas = Consulta.listarConsultasPorPaciente(cpf);

            if (consultas.isEmpty()) {
                System.out.println("Nenhuma consulta encontrada para esse paciente.");
            } else {
                for (Consulta consulta : consultas) {
                    consulta.verConsulta();
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao listar consultas por paciente: " + e.getMessage());
        }

        Utils.pausar();
    }

    private static void listarConsultasPorMedico() {
        Utils.limparTela();
        System.out.println("=== LISTAR CONSULTAS POR MÉDICO ===");

        try {
            String crm = Utils.lerTexto("Informe o CRM do médico: ");
            List<Consulta> consultas = Consulta.listarConsultasPorMedico(crm);

            if (consultas.isEmpty()) {
                System.out.println("Nenhuma consulta encontrada para esse médico.");
            } else {
                for (Consulta consulta : consultas) {
                    consulta.verConsulta();
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao listar consultas por médico: " + e.getMessage());
        }

        Utils.pausar();
    }

    private static void listarConsultasPorPeriodo() {
        Utils.limparTela();
        System.out.println("=== LISTAR CONSULTAS POR PERÍODO ===");

        try {
            String inicioTexto = Utils.lerTexto("Data inicial (AAAA-MM-DD): ");
            String fimTexto = Utils.lerTexto("Data final (AAAA-MM-DD): ");

            LocalDate inicio = LocalDate.parse(inicioTexto);
            LocalDate fim = LocalDate.parse(fimTexto);

            List<Consulta> consultas = Consulta.listarConsultasPorPeriodo(inicio, fim);

            if (consultas.isEmpty()) {
                System.out.println("Nenhuma consulta encontrada no período informado.");
            } else {
                for (Consulta consulta : consultas) {
                    consulta.verConsulta();
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao listar consultas por período: " + e.getMessage());
        }

        Utils.pausar();
    }

    private static void listarConsultasPorStatus() {
        Utils.limparTela();
        System.out.println("=== LISTAR CONSULTAS POR STATUS ===");

        try {
            System.out.println("Status disponíveis: AGENDADA, EM_ANDAMENTO, CONCLUIDA, CANCELADA");
            String statusTexto = Utils.lerTexto("Informe o status: ").toUpperCase();

            Status status = Status.valueOf(statusTexto);
            List<Consulta> consultas = Consulta.listarConsultasPorStatus(status);

            if (consultas.isEmpty()) {
                System.out.println("Nenhuma consulta encontrada com esse status.");
            } else {
                for (Consulta consulta : consultas) {
                    consulta.verConsulta();
                }
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Status inválido. Use: AGENDADA, EM_ANDAMENTO, CONCLUIDA ou CANCELADA.");
        } catch (Exception e) {
            System.out.println("Erro ao listar consultas por status: " + e.getMessage());
        }

        Utils.pausar();
    }

    

    
}
