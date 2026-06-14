package br.com.clinicamedica.ui;

import br.com.clinicamedica.Usuario;

public class MenuPrincipal {

    public static void main(String[] args) {

        System.out.println("|--------------------------------------------------|");
        System.out.println("|     SISTEMA GERENCIADOR CLÍNICA DE SAÚDE         |");
        System.out.println("|         UFERSA – Campus Pau dos Ferros           |");
        System.out.println("|--------------------------------------------------|");
        System.out.println();

        boolean autenticado = true; //trocar para menuLogin quando implementar 

        if (autenticado) {
            menuSelecaoPerfil();
        }

        Utils.fecharScanner();
        System.out.println("\nSistema encerrado. Até logo!");
    }

    private static boolean menuLogin() {
        
        return false;
    }

    private static void menuSelecaoPerfil() {
        //Versão de teste das telas
        //TODO: refazer;
        int opcao;

        do {
            System.out.println();
            System.out.println("|-------------- PERFIL ----------------|");
            System.out.println("| 1 · Paciente                         |");
            System.out.println("| 2 · Funcionário                      |");
            System.out.println("| 3 · Médico                           |");
            System.out.println("| 0 · Sair                             |");
            System.out.println("|--------------------------------------|");

            opcao = Utils.lerInteiro("Escolha uma opção: ");

            switch (opcao) {
                case 1 -> MenuPaciente.exibir();
                case 2 -> MenuFuncionario.exibir();
                case 3 -> MenuMedico.exibir();
                case 0 -> { }
                default -> Utils.msgOpcaoInvalida();
            }
        } while (opcao != 0);
    }
}