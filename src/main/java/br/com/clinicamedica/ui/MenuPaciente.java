package br.com.clinicamedica.ui;

import java.util.List;
import br.com.clinicamedica.Consulta;
import br.com.clinicamedica.Paciente;

public class MenuPaciente {


    

    public static void exibir(Paciente pacienteLogado) {
        
        int opcao;
        do {
            System.out.println();
            System.out.println("|---------------- MENU PACIENTE -------------------|");
            System.out.println("| 1 · Ver meus dados                               |");
            System.out.println("| 2 · Ver meu histórico de consultas               |");
            System.out.println("| 3 · Ver minhas prescrições                       |");
            System.out.println("| 0 · Voltar                                       |");
            System.out.println("|--------------------------------------------------|");

            opcao = Utils.lerInteiro("Escolha uma opção: ");

            switch (opcao) {
                case 1 -> verMeusDados(pacienteLogado);
                case 2 -> verHistoricoConsultas(pacienteLogado);
                case 3 -> verPrescricoes(pacienteLogado);
                case 0 -> { }
                default -> Utils.msgOpcaoInvalida();
            }

        } while (opcao != 0);
    }

    
    private static void verMeusDados(Paciente pacienteLogado) {
        Utils.limparTela();
        pacienteLogado.verPaciente();
        Utils.pausar();
    }

    private static void verHistoricoConsultas(Paciente pacienteLogado) {
        Utils.limparTela();
        System.out.println("Histórico de Consultas");
        //TODO
        Utils.pausar();
    }

    private static void verPrescricoes(Paciente pacienteLogado) {
        Utils.limparTela();
        System.out.println("Ver prescrições");
        //TODO
        Utils.pausar();
        
    }
}