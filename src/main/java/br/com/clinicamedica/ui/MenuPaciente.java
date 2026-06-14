package br.com.clinicamedica.ui;


public class MenuPaciente {

    public static void exibir() {
        int opcao;

        do {
            System.out.println();
            System.out.println("|---------------- MENU PACIENTE -------------------|");
            System.out.println("|  1 · Ver meus dados                              |");
            System.out.println("|  2 · Ver meu histórico de consultas              |");
            System.out.println("|  3 · Ver minhas prescrições                      |");
            System.out.println("|  0 · Voltar                                      |");
            System.out.println("|--------------------------------------------------|");

            opcao = Utils.lerInteiro("Escolha uma opção: ");

            switch (opcao) {
                case 1 -> verMeusDados();
                case 2 -> verHistoricoConsultas();
                case 3 -> verPrescricoes();
                case 0 -> { }
                default -> Utils.msgOpcaoInvalida();
            }
        } while (opcao != 0);
    }

    private static void verMeusDados() {
        System.out.println("Função: verMeusDados");
        // TODO
    }

    private static void verHistoricoConsultas() {
        System.out.println("Função: verHistoricoConsultas");
        // TODO
    }

    private static void verPrescricoes() {
        System.out.println("Função: verPrescricoes");
        // TODO
    }
}