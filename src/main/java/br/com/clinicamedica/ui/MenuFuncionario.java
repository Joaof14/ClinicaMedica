package br.com.clinicamedica.ui;
import br.com.clinicamedica.Funcionario;



public class MenuFuncionario {

    public static void exibir(Funcionario funcionarioLogado) {
        int opcao;

        do {
            System.out.println();
            System.out.println("|-------------- MENU FUNCIONÁRIO ------------------|");
            System.out.println("|  1 · Gerenciar Pacientes                         |");
            System.out.println("|  2 · Gerenciar Funcionarios                      |");
            System.out.println("|  3 · Gerenciar Medicos                           |");
            System.out.println("|  4 · Gerenciar Consultas                         |");
            System.out.println("|  0 · Voltar                                      |");
            System.out.println("|--------------------------------------------------|");

            opcao = Utils.lerInteiro("Escolha uma opção: ");

            switch (opcao) {
                case 1 -> GerenciarPacientesUI.exibir();
                case 2 -> GerenciarFuncionariosUI.exibir(funcionarioLogado);
                case 3 -> GerenciarMedicosUI.exibir(funcionarioLogado);
                case 4 -> GerenciarConsultasUI.exibir(funcionarioLogado);
                case 0 -> { }
                default -> Utils.msgOpcaoInvalida();

            }
        } while (opcao != 0);
    }

    

}