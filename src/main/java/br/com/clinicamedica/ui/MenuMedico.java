package br.com.clinicamedica.ui;

public class MenuMedico {

    public static void exibir() {
        int opcao;

        do {
            System.out.println();
            System.out.println("|----------------- MENU MÉDICO --------------------|");
            System.out.println("|  1 · Ver consultas                               |");
            System.out.println("|  2 · Iniciar consulta                            |");
            System.out.println("|  3 · Concluir consulta                           |");
            System.out.println("|  4 · Registrar diagnóstico                       |");
            System.out.println("|  5 · Registrar prescrição                        |");
            System.out.println("|  6 · Atualizar status da consulta                |");
            System.out.println("|  0 · Voltar                                      |");
            System.out.println("|--------------------------------------------------|");

            opcao = Utils.lerInteiro("Escolha uma opção: ");

            switch (opcao) {
                case 1 -> verConsultas();
                case 2 -> iniciarConsulta();
                case 3 -> concluirConsulta();
                case 4 -> registrarDiagnostico();
                case 5 -> registrarPrescricao();
                case 6 -> atualizarStatusConsulta();
                case 0 -> { }
                default -> Utils.msgOpcaoInvalida();
            }
        } while (opcao != 0);
    }

    private static void verConsultas() {
        System.out.println("Função: verConsultas");
        // TODO
    }

    private static void iniciarConsulta() {
        System.out.println("Função: iniciarConsulta");
        // TODO
    }

    private static void concluirConsulta() {
        System.out.println("Função: concluirConsulta");
        // TODO
    }

    private static void registrarDiagnostico() {
        System.out.println("Função: registrarDiagnostico");
        // TODO
    }

    private static void registrarPrescricao() {
        System.out.println("Função: registrarPrescricao");
        // TODO
    }

    private static void atualizarStatusConsulta() {
        System.out.println("Função: atualizarStatusConsulta");
        // TODO
    }
}
