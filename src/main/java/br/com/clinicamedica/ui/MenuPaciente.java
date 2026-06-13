package br.com.clinicamedica.ui;

public class MenuPaciente {
    public static void exibir() {
        System.out.println("|--------------------------------------------------|");
        System.out.println("|              GERENCIAR PACIENTES                 |");
        System.out.println("|--------------------------------------------------|");
        System.out.println("|  1 · Cadastrar paciente                          |");
        System.out.println("|  2 · Ver paciente (por CPF)                      |");
        System.out.println("|  3 · Listar todos os pacientes                   |");
        System.out.println("|  4 · Listar pacientes por sintoma                |");
        System.out.println("|  5 · Atualizar paciente                          |");
        System.out.println("|  6 · Deletar paciente                            |");
        System.out.println("|  0 · Voltar                                      |");
        System.out.println("|--------------------------------------------------|");
        System.out.print("  Escolha uma opção: ");
    }
}
