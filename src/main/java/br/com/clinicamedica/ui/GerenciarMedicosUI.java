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
        Utils.pausar();
    }

    private static void atualizarMedico() {
        Utils.limparTela();
        System.out.println("=== ATUALIZAR MÉDICO ===");
        utils.pausar();
    }

    private static void removerMedico() {
        Utils.limparTela();
        System.out.println("=== REMOVER MÉDICO ===");
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