package br.com.clinicamedica.ui;

import java.util.List;

import br.com.clinicamedica.Consulta;
import br.com.clinicamedica.Medico;

public class MenuMedico {

    public static void exibir(Medico medicoLogado) {
        int opcao;

        do {
            Utils.limparTela();
            System.out.println();
            System.out.println("|----------------- MENU MÉDICO --------------------|");
            System.out.println("|  1 · Ver consultas                               |");
            System.out.println("|  2 · Iniciar consulta                            |");
            System.out.println("|  3 · Concluir consulta                           |");
            System.out.println("|  4 · Registrar prescrição                        |");
            System.out.println("|  5 · Atualizar status da consulta                |");
            System.out.println("|  0 · Voltar                                      |");
            System.out.println("|--------------------------------------------------|");

            opcao = Utils.lerInteiro("Escolha uma opção: ");

            switch (opcao) {
                case 1 -> verConsultas(medicoLogado);
                case 2 -> iniciarConsulta(medicoLogado);
                case 3 -> concluirConsulta(medicoLogado);
                case 4 -> registrarPrescricao(medicoLogado);
                case 5 -> atualizarStatusConsulta(medicoLogado);
                case 0 -> { }
                default -> Utils.msgOpcaoInvalida();
            }
        } while (opcao != 0);
    }

    private static void verConsultas(Medico medicoLogado) {
        Utils.limparTela();
        System.out.println("=== CONSULTAS DO MÉDICO ===");

        try {
            List<Consulta> consultas = Consulta.listarConsultasPorMedico(medicoLogado.getCrm());

            if (consultas.isEmpty()) {
                System.out.println("Nenhuma consulta encontrada para este médico.");
            } else {
                for (Consulta consulta : consultas) {
                    consulta.verConsulta();
                    System.out.println("----------------------------------");
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao listar consultas do médico: " + e.getMessage());
        }

        Utils.pausar();
    }

    private static void iniciarConsulta(Medico medicoLogado) {
        Utils.limparTela();
        System.out.println("=== INICIAR CONSULTA ===");

        try {
            Consulta consulta = buscarConsultaDoMedicoPorId(medicoLogado);

            if (consulta != null) {
                consulta.iniciarConsulta();
                System.out.println("Consulta iniciada com sucesso.");
            }
        } catch (Exception e) {
            System.out.println("Erro ao iniciar consulta: " + e.getMessage());
        }

        Utils.pausar();
    }

    private static void concluirConsulta(Medico medicoLogado) {
        Utils.limparTela();
        System.out.println("=== CONCLUIR CONSULTA ===");

        try {
            Consulta consulta = buscarConsultaDoMedicoPorId(medicoLogado);

            if (consulta != null) {
                String prescricao = Utils.lerTexto("Informe a prescrição: ");
                consulta.concluirConsulta(prescricao);
                System.out.println("Consulta concluída com sucesso.");
            }
        } catch (Exception e) {
            System.out.println("Erro ao concluir consulta: " + e.getMessage());
        }

        Utils.pausar();
    }

    private static void registrarPrescricao(Medico medicoLogado) {
        Utils.limparTela();
        System.out.println("=== REGISTRAR PRESCRIÇÃO ===");
        System.out.println("A prescrição será registrada ao concluir a consulta.");

        try {
            Consulta consulta = buscarConsultaDoMedicoPorId(medicoLogado);

            if (consulta != null) {
                String prescricao = Utils.lerTexto("Informe a prescrição: ");
                consulta.concluirConsulta(prescricao);
                System.out.println("Prescrição registrada com sucesso.");
            }
        } catch (Exception e) {
            System.out.println("Erro ao registrar prescrição: " + e.getMessage());
        }

        Utils.pausar();
    }

    private static void atualizarStatusConsulta(Medico medicoLogado) {
        Utils.limparTela();
        System.out.println("=== ATUALIZAR STATUS DA CONSULTA ===");

        try {
            Consulta consulta = buscarConsultaDoMedicoPorId(medicoLogado);

            if (consulta != null) {
                System.out.println("1 - Iniciar consulta");
                System.out.println("2 - Concluir consulta");
                System.out.println("3 - Cancelar consulta");

                int opcao = Utils.lerInteiro("Escolha o novo status: ");

                switch (opcao) {
                    case 1 -> {
                        consulta.iniciarConsulta();
                        System.out.println("Status atualizado para EM ANDAMENTO.");
                    }
                    case 2 -> {
                        String prescricao = Utils.lerTexto("Informe a prescrição: ");
                        consulta.concluirConsulta(prescricao);
                        System.out.println("Status atualizado para CONCLUÍDA.");
                    }
                    case 3 -> {
                        consulta.cancelarConsulta();
                        System.out.println("Status atualizado para CANCELADA.");
                    }
                    default -> System.out.println("Opção inválida.");
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao atualizar status da consulta: " + e.getMessage());
        }

        Utils.pausar();
    }

    private static Consulta buscarConsultaDoMedicoPorId(Medico medicoLogado) {
        int idConsulta = Utils.lerInteiro("Informe o ID da consulta: ");
        List<Consulta> consultas = Consulta.listarConsultasPorMedico(medicoLogado.getCrm());

        for (Consulta consulta : consultas) {
            if (String.valueOf(consulta.getId()).equals(String.valueOf(idConsulta))) {
                return consulta;
            }
        }

        System.out.println("Consulta não encontrada para este médico.");
        return null;
    }
}