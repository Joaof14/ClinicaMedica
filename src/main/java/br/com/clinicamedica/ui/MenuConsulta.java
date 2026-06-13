package br.com.clinicamedica.ui;

public class MenuConsulta {
    public static void exibir() {
        int opcao;
        do{

            
            System.out.println("| -------------------------------------------------|");
            System.out.println("|              GERENCIAR CONSULTAS                 |");
            System.out.println("|--------------------------------------------------|");
            System.out.println("|  1 · Gerar nova consulta                         |");
            System.out.println("|  2 · Ver consulta (por ID)                       |");
            System.out.println("|  3 · Listar todas as consultas                   |");
            System.out.println("|  4 · Listar consultas por paciente (CPF)         |");
            System.out.println("|  5 · Listar consultas por médico (CRM)           |");
            System.out.println("|  6 · Listar consultas por período                |");
            System.out.println("|  7 · Listar consultas por status                 |");
            System.out.println("|--------------------------------------------------|");
            System.out.println("|  8 · Iniciar consulta                            |");
            System.out.println("|  9 · Concluir consulta (com prescrição)          |");
            System.out.println("| 10 · Cancelar consulta                           |");
            System.out.println("|  0 · Voltar                                      |");
            System.out.println("|--------------------------------------------------|");
            System.out.print("  Escolha uma opção: ");

            opcao = Utils.lerInteiro();
            //Implementar switch case

        }while (opcao != 0);
        
    }
}
