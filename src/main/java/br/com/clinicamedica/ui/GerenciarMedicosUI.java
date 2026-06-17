package br.com.clinicamedica.ui;

import java.util.List;
import br.com.clinicamedica.Funcionario;
import br.com.clinicamedica.Medico;

public class GerenciarMedicosUI {

    public static void exibir(Funcionario funcionarioLogado) {
        int opcao;

        do {
            Utils.limparTela();
            System.out.println("=== GERENCIAR MÉDICOS ===");
            System.out.println("1 · Cadastrar médico");
            System.out.println("2 · Atualizar médico");
            System.out.println("3 · Remover médico");
            System.out.println("4 · Listar médicos");
            System.out.println("5 · Listar médicos por área");
            System.out.println("0 · Voltar");

            opcao = Utils.lerInteiro("Escolha uma opção: ");

            switch (opcao) {
                case 1 -> cadastrarMedico();
                case 2 -> atualizarMedico();
                case 3 -> removerMedico();
                case 4 -> listarMedicos();
                case 5 -> listarMedicosPorArea();
                case 0 -> { }
                default -> Utils.msgOpcaoInvalida();
            }
        } while (opcao != 0);
    }

    private static void cadastrarMedico() {
        Utils.limparTela();
        System.out.println("=== CADASTRAR MÉDICO ===");

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
            String area = Utils.lerTexto("Área de atuação: ");
            String crm = Utils.lerTexto("CRM: ");

            Medico medico = Medico.cadastrarMedico(
                nome, idade, sexo, cpf, telefone, login, senha,
                true, salario, cargaHoraria, turno, atendente, area, crm
            );

            if (medico != null) {
                System.out.println("Médico cadastrado com sucesso.");
            } else {
                System.out.println("Não foi possível cadastrar o médico.");
            }
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar médico: " + e.getMessage());
        }

        Utils.pausar();
    }

    private static void atualizarMedico() {
        Utils.limparTela();
        System.out.println("=== ATUALIZAR MÉDICO ===");

        try {
            String crmAtual = Utils.lerTexto("CRM atual do médico: ");
            String novaArea = Utils.lerTexto("Nova área de atuação: ");
            String novoCrm = Utils.lerTexto("Novo CRM: ");

            boolean atualizado = Medico.atualizarMedico(crmAtual, novaArea, novoCrm);

            if (atualizado) {
                System.out.println("Médico atualizado com sucesso.");
            } else {
                System.out.println("Médico não encontrado.");
            }
        } catch (Exception e) {
            System.out.println("Erro ao atualizar médico: " + e.getMessage());
        }

        Utils.pausar();
    }

    private static void removerMedico() {
        Utils.limparTela();
        System.out.println("=== REMOVER MÉDICO ===");

        try {
            String crm = Utils.lerTexto("CRM do médico: ");
            boolean removido = Medico.deletarMedico(crm);

            if (removido) {
                System.out.println("Médico removido com sucesso.");
            } else {
                System.out.println("Médico não encontrado.");
            }
        } catch (Exception e) {
            System.out.println("Erro ao remover médico: " + e.getMessage());
        }

        Utils.pausar();
    }

    private static void listarMedicos() {
        Utils.limparTela();
        System.out.println("=== LISTAR MÉDICOS ===");

        try {
            List<Medico> medicos = Medico.listarMedicos();

            if (medicos.isEmpty()) {
                System.out.println("Nenhum médico cadastrado.");
            } else {
                for (Medico medico : medicos) {
                    medico.verMedico();
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao listar médicos: " + e.getMessage());
        }

        Utils.pausar();
    }

    private static void listarMedicosPorArea() {
        Utils.limparTela();
        System.out.println("=== LISTAR MÉDICOS POR ÁREA ===");

        try {
            String area = Utils.lerTexto("Área de atuação: ");
            List<Medico> medicos = Medico.listarMedicosPorArea(area);

            if (medicos.isEmpty()) {
                System.out.println("Nenhum médico encontrado para essa área.");
            } else {
                for (Medico medico : medicos) {
                    medico.verMedico();
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao listar médicos por área: " + e.getMessage());
        }

        Utils.pausar();
    }
}