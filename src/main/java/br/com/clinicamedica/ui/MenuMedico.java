package br.com.clinicamedica.ui;

public class MenuMedico {
    public static void exibir(){

        int opcao;
        do{

            opcao = Utils.lerInteiro();
            //Implementar switch case

        }while (opcao != 0);
        
        Utils.limparTela();
        System.out.println("|--------------------------------------------------|");
        System.out.println("|               GERENCIAR MÉDICOS                  |");
        System.out.println("|--------------------------------------------------|");
        System.out.println("|  1 · Cadastrar médico                            |");
        System.out.println("|  2 · Ver médico (por CRM)                        |");
        System.out.println("|  3 · Listar todos os médicos                     |");
        System.out.println("|  4 · Listar médicos por área de atuação          |");
        System.out.println("|  5 · Atualizar médico                            |");
        System.out.println("|  6 · Deletar médico                              |");
        System.out.println("|  0 · Voltar                                      |");
        System.out.println("|--------------------------------------------------|");
        System.out.print("  Escolha uma opção: ");
    }
}
