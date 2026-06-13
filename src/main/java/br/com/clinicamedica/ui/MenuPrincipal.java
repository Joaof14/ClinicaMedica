package br.com.clinicamedica.ui;

import java.util.Scanner;

public class MenuPrincipal {

    private static final Scanner entrada = new Scanner(System.in);


    public static void main(String[] args) {
        
        
        System.out.println("|--------------------------------------------------|");
        System.out.println("|     SISTEMA GERENCIADOR CLÍNICA DE SAÚDE         |");
        System.out.println("|         UFERSA – Campus Pau dos Ferros           |");
        System.out.println("|--------------------------------------------------|");
        System.out.println();
        boolean autenticado = true; // Todo: Quando terminar MenuLogin, chamar ela
        if (autenticado) {
            menuGeral();
        }
        System.out.println("\nSistema encerrado. Até logo!");
        entrada.close();
    }

    // LOGIN
    private static boolean menuLogin() {
        int tentativas = 3;
        while (tentativas > 0) {
            System.out.println(" LOGIN ");
            System.out.print("Login   : ");
            String login = entrada.nextLine().trim();
            System.out.print("Senha   : ");
            String senha = entrada.nextLine().trim();
            System.out.println("");

            // TODO: Usuario.autenticar(login, senha)
            
        }
        System.out.println("Número máximo de tentativas atingido. Encerrando.");
        return false;
    }

    // MENU GERAL
    public static void menuGeral() {
        int opcao;
        do {
            
            System.out.println("|--------------------------------------------------|");
            System.out.println("|              MENU PRINCIPAL                      |");
            System.out.println("|--------------------------------------------------|");
            System.out.println("|  1 · Gerenciar Usuários                          |");
            System.out.println("|  2 · Gerenciar Funcionários                      |");
            System.out.println("|  3 · Gerenciar Pacientes                         |");
            System.out.println("|  4 · Gerenciar Médicos                           |");
            System.out.println("|  5 · Gerenciar Consultas                         |");
            System.out.println("|  0 · Sair                                        |");
            System.out.println("|--------------------------------------------------|");
            System.out.print("  Escolha uma opção: ");
            opcao = entrada.nextInt(); //implementar uma leitura especifica para capturar erros
            
            switch (opcao) { 
                case 1 -> MenuUsuario.exibir();
                case 2 -> MenuFuncionario.exibir();
                case 3 -> MenuPaciente.exibir();
                case 4 -> MenuMedico.exibir();
                case 5 -> MenuConsulta.exibir();
                case 0 -> {}
                default -> System.out.println("Opção Inválida; Tente novamente");
            }
        } while (opcao != 0);
        
    }

    

}
