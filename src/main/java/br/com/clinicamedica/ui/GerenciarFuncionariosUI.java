package br.com.clinicamedica.ui;

import java.util.List;
import br.com.clinicamedica.Funcionario;

public class GerenciarFuncionariosUI {

    public static void exibir(Funcionario funcionarioLogado) {
        int opcao;

        do {
            Utils.limparTela();
            System.out.println("=== GERENCIAR FUNCIONÁRIOS ===");
            System.out.println("1 · Cadastrar funcionário");
            System.out.println("2 · Atualizar funcionário");
            System.out.println("3 · Remover funcionário");
            System.out.println("4 · Listar funcionários");
            System.out.println("5 · Listar funcionários por papel");
            System.out.println("0 · Voltar");

            opcao = Utils.lerInteiro("Escolha uma opção: ");

            switch (opcao) {
                case 1 -> cadastrarFuncionario();
                case 2 -> atualizarFuncionario();
                case 3 -> removerFuncionario();
                case 4 -> listarFuncionarios();
                case 5 -> listarFuncionariosPorPapel();
                case 0 -> { }
                default -> Utils.msgOpcaoInvalida();
            }
        } while (opcao != 0);
    }

    private static void cadastrarFuncionario() {
        Utils.limparTela();
        System.out.println("=== CADASTRAR FUNCIONÁRIO ===");
    }

    private static void atualizarFuncionario() {
        Utils.limparTela();
        System.out.println("=== ATUALIZAR FUNCIONÁRIO ===");
        Utils.pausar();
    }

    private static void removerFuncionario() {
        Utils.limparTela();
        System.out.println("=== REMOVER FUNCIONÁRIO ===");
        Utils.pausar();
    }

    private static void listarFuncionarios() {
        Utils.limparTela();
        System.out.println("=== LISTAR FUNCIONÁRIOS ===");

        try {
            List<Funcionario> funcionarios = Funcionario.listarFuncionario();

            if (funcionarios.isEmpty()) {
                System.out.println("Nenhum funcionário cadastrado.");
            } else {
                for (Funcionario funcionario : funcionarios) {
                    funcionario.verFuncionario();
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao listar funcionários: " + e.getMessage());
        }

        Utils.pausar();
    }

    private static void listarFuncionariosPorPapel() {
        Utils.limparTela();
        System.out.println("=== LISTAR FUNCIONÁRIOS POR PAPEL ===");

        try {
            boolean atendente = Utils.lerBooleano("Listar atendentes? (sim/nao): ");
            List<Funcionario> funcionarios = Funcionario.listarFuncionariosPorPapel(atendente);

            if (funcionarios.isEmpty()) {
                System.out.println("Nenhum funcionário encontrado.");
            } else {
                for (Funcionario funcionario : funcionarios) {
                    funcionario.verFuncionario();
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao listar funcionários por papel: " + e.getMessage());
        }

        Utils.pausar();
    }
}