package br.com.clinicamedica.ui;

public class MenuUsuario {
    public static void exibir(){
        
        System.out.println("|--------------------------------------------------|");
        System.out.println("|              GERENCIAR USUÁRIOS                  |");
        System.out.println("|--------------------------------------------------|");
        System.out.println("|  1 · Cadastrar usuário                           |");
        System.out.println("|  2 · Ver usuário (por CPF)                       |");
        System.out.println("|  3 · Listar todos os usuários                    |");
        System.out.println("|  4 · Atualizar usuário                           |");
        System.out.println("|  5 · Deletar / Desativar usuário                 |");
        System.out.println("|  0 · Voltar                                      |");
        System.out.println("|--------------------------------------------------|");
        System.out.print("  Escolha uma opção: ");
    }
}
