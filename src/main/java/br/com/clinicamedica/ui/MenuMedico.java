package br.com.clinicamedica.ui;

import br.com.clinicamedica.Medico;
import br.com.clinicamedica.Paciente;

public class MenuMedico {

    

    public static void exibir(Medico medicoLogado) {
        int opcao;

        do {
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
                case 1 -> verConsultas();
                case 2 -> iniciarConsulta();
                case 3 -> concluirConsulta();
                case 4 -> registrarPrescricao();
                case 6 -> atualizarStatusConsulta();
                case 0 -> { }
                default -> Utils.msgOpcaoInvalida();
            }
        } while (opcao != 0);
    }

    private static void verConsultas() {
        Utils.limparTela();
        System.out.println("Função: verConsultas do medico");
        // TODO
        Utils.pausar();
    }

    private static void iniciarConsulta() {
        Utils.limparTela();
        System.out.println("Função: iniciarConsulta");
        // TODO
        Utils.pausar();
    }

    private static void concluirConsulta() {
        Utils.limparTela();
        System.out.println("Função: concluirConsulta");
        // TODO
        Utils.pausar();
    }

    private static void registrarPrescricao() {
        Utils.limparTela();
        System.out.println("Função: registrarPrescricao");
        // TODO
        Utils.pausar();
        
    }

    private static void atualizarStatusConsulta() {
        Utils.limparTela();
        System.out.println("Função: atualizarStatusConsulta");
        // TODO
        Utils.pausar();
    }
}
