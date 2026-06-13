package br.com.clinicamedica.ui;

public class MenuFuncionario {
    public static void exibir(){
        int opcao;
        do{
            Utils.limparTela();
            System.out.println("|--------------------------------------------------|");
            System.out.println("|           GERENCIAR FUNCIONÁRIOS                 |");
            System.out.println("|--------------------------------------------------|");
            System.out.println("|  1 · Cadastrar funcionário                       |");
            System.out.println("|  2 · Ver funcionário (por CPF)                   |");
            System.out.println("|  3 · Listar todos os funcionários                |");
            System.out.println("|  4 · Listar funcionários por papel               |");
            System.out.println("|  5 · Atualizar funcionário                       |");
            System.out.println("|  6 · Deletar funcionário                         |");
            System.out.println("|  0 · Voltar                                      |");
            System.out.println("|--------------------------------------------------|");
            opcao = Utils.lerInteiro("");
            //Implementar switch case

        }while (opcao != 0);
        
    }



}