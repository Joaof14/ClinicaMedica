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

        try {
            String nome = Utils.lerTexto("Nome: ");
            int idade = Utils.lerInteiro("Idade: ");
            String sexo = Utils.lerTexto("Sexo: ");
            String cpf = Utils.lerTexto("CPF: ");
            String telefone = Utils.lerTexto("Telefone: ");
            String login = Utils.lerTexto("Login: ");
            String senha = Utils.lerTexto("Senha: ");
            double salario = Double.parseDouble(Utils.lerTexto("Salário: "));
            int cargaHoraria = Utils.lerInteiro("Carga horária semanal: ");
            String turno = Utils.lerTexto("Turno: ");
            boolean atendente = Utils.lerBooleano("É atendente? (true/false): ");

            Funcionario funcionario = Funcionario.cadastrarFuncionario(
                nome, idade, sexo, cpf, telefone, login, senha,
                true, salario, cargaHoraria, turno, atendente
            );

            if (funcionario != null) {
                System.out.println("Funcionário cadastrado com sucesso.");
            } else {
                System.out.println("Não foi possível cadastrar o funcionário.");
            }
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar funcionário: " + e.getMessage());
        }

        Utils.pausar();
    }

    private static void atualizarFuncionario() {
        Utils.limparTela();
        System.out.println("=== ATUALIZAR FUNCIONÁRIO ===");

        try {
            String cpf = Utils.lerTexto("CPF do funcionário: ");
            double salario = Double.parseDouble(Utils.lerTexto("Novo salário: "));
            int cargaHoraria = Utils.lerInteiro("Nova carga horária semanal: ");
            String turno = Utils.lerTexto("Novo turno: ");
            boolean atendente = Utils.lerBooleano("É atendente? (true/false): ");

            boolean atualizado = Funcionario.atualizarFuncionario(
                cpf, salario, cargaHoraria, turno, atendente
            );

            if (atualizado) {
                System.out.println("Funcionário atualizado com sucesso.");
            } else {
                System.out.println("Funcionário não encontrado.");
            }
        } catch (Exception e) {
            System.out.println("Erro ao atualizar funcionário: " + e.getMessage());
        }

        Utils.pausar();
    }

    private static void removerFuncionario() {
        Utils.limparTela();
        System.out.println("=== REMOVER FUNCIONÁRIO ===");

        try {
            String cpf = Utils.lerTexto("CPF do funcionário: ");
            boolean removido = Funcionario.deletarFuncionario(cpf);

            if (removido) {
                System.out.println("Funcionário removido com sucesso.");
            } else {
                System.out.println("Funcionário não encontrado.");
            }
        } catch (Exception e) {
            System.out.println("Erro ao remover funcionário: " + e.getMessage());
        }

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