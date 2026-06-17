package br.com.clinicamedica.ui;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import br.com.clinicamedica.Consulta;
import br.com.clinicamedica.Funcionario;
import br.com.clinicamedica.Status;

public class GerenciarConsultasUI {
    public static void exibir(Funcionario funcionarioLogado){
        int opcao;

        do {
            System.out.println();
            System.out.println("|---------- GERENCIAMENTO DE PACIENTES ------------|");
            System.out.println("|  1 · Gerar consulta                              |");
            System.out.println("|  2 · Atualizar consulta                          |");
            System.out.println("|  3 · Cancelar consulta                           |");
            System.out.println("|  4 · Listar consultas                            |");
            System.out.println("|  5 · Listar consultas por paciente               |");
            System.out.println("|  6 · Listar consultas por médico                 |");
            System.out.println("|  7 · Listar consultas por período                |");
            System.out.println("|  8 · Listar consultas por status                 |");
            System.out.println("|  0 · Voltar                                      |");
            System.out.println("|--------------------------------------------------|");

            opcao = Utils.lerInteiro("Escolha uma opção: ");

            switch (opcao) {
                case 1 -> gerarConsulta();
                case 2 -> atualizarConsulta(funcionarioLogado);
                case 3-> cancelarConsulta();
                case 4 -> listarConsultas();
                case 5 -> listarConsultasPorPaciente();
                case 6 -> listarConsultasPorMedico();
                case 7 -> listarConsultasPorPeriodo();
                case 8 -> listarConsultasPorStatus();
                case 0 -> { }
                default -> Utils.msgOpcaoInvalida();
            }
        } while (opcao != 0);
    }
    


    private static void gerarConsulta() {
        Utils.limparTela();
        System.out.println("=== GERAR CONSULTA ===");

        try {
            LocalDate data = Utils.lerData("Data da consulta (AAAA-MM-DD): ");
            LocalTime horario = Utils.lerHorario("Horário da consulta (HH:MM): ");
            String cpfPaciente = Utils.lerTexto("CPF do paciente: ");
            String crmMedico = Utils.lerTexto("CRM do médico: ");

            Consulta.gerarConsulta(data, horario, cpfPaciente, crmMedico);
        } catch (Exception e) {
            System.out.println("Erro ao gerar consulta: " + e.getMessage());
        }

        Utils.pausar();
    }

    private static void atualizarConsulta(Funcionario funcionarioLogado) {
        Utils.limparTela();
        System.out.println("=== ATUALIZAR CONSULTA ===");

        try {
            int idConsulta = Utils.lerInteiro("Informe o ID da consulta: ");
            Consulta consulta = buscarConsultaPorId(idConsulta);

            if (consulta == null) {
                System.out.println("Consulta não encontrada.");
            } else {
                LocalDate data = Utils.lerData("Nova data (AAAA-MM-DD): ");
                LocalTime horario = Utils.lerHorario("Novo horário (HH:MM): ");
                
                Status status = consulta.getStatus();
                String prescricao = consulta.getPrescricao();

                if (funcionarioLogado instanceof br.com.clinicamedica.Medico) {
                    System.out.println("Status disponíveis: AGENDADA, EM_ANDAMENTO, CONCLUIDA, CANCELADA");
                    String statusTexto = Utils.lerTexto("Novo status: ").toUpperCase();
                    status = Status.valueOf(statusTexto);
                    prescricao = Utils.lerTexto("Prescrição (pode deixar vazio): ");
                    
                    if (prescricao.isBlank()) {
                        prescricao = null;
                    }
                } else {
                    System.out.println("Como atendente, você só pode alterar data e horário. Status e prescrição foram mantidos.");
                }

                consulta.atualizarConsulta(idConsulta, data, horario, status, prescricao);
            }
        } catch (Exception e) {
            System.out.println("Erro ao atualizar consulta: " + e.getMessage());
        }

        Utils.pausar();
    }

    private static void cancelarConsulta() {
        Utils.limparTela();
        System.out.println("=== CANCELAR CONSULTA ===");

        try {
            int idConsulta = Utils.lerInteiro("Informe o ID da consulta: ");
            Consulta consulta = buscarConsultaPorId(idConsulta);

            if (consulta == null) {
                System.out.println("Consulta não encontrada.");
            } else {
                consulta.cancelarConsulta();
            }
        } catch (Exception e) {
            System.out.println("Erro ao cancelar consulta: " + e.getMessage());
        }

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
            LocalDate inicio = Utils.lerData("Data inicial (AAAA-MM-DD): ");
            LocalDate fim = Utils.lerData("Data final (AAAA-MM-DD): ");

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

    

    private static Consulta buscarConsultaPorId(int id) {
        List<Consulta> consultas = Consulta.listarConsultas();

        for (Consulta consulta : consultas) {
            if (consulta.getId() == id) {
                return consulta;
            }
        }

        return null;
    }
}