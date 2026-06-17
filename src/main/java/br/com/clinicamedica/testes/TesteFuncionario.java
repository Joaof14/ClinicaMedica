package br.com.clinicamedica.testes;

import br.com.clinicamedica.ConexaoDB;
import br.com.clinicamedica.Funcionario;

import java.util.List;

import static br.com.clinicamedica.Funcionario.*;

public class TesteFuncionario {
    public static void main(String[] args) {

        Funcionario f1 = new Funcionario("Joao Souza", 35, "Masculino", "00233344456", "84999990003", "joao.souza",
                "senha789", true, 3000.0, 40, "Manhã", true);

        cadastrarFuncionario("Joao Souza", 35, "Masculino", "00233344456", "84999990003", "joao.souza256", "senha789",
                true, 3000.0, 40, "Tarde", true);

        f1.verFuncionario();

        atualizarFuncionario(f1.getCpf(), 4000.0, f1.getCargaHorariaSemanal(), f1.getTurno(), true);

        deletarFuncionario(f1.getCpf());

        System.out.println("Teste de listar funcionário:");
        List<Funcionario> l1 = listarFuncionario();
        System.out.println(l1);

        System.out.println(" ");

        System.out.println("Teste de listar por papel = true = atendente");
        List<Funcionario> l2 = listarFuncionariosPorPapel(true);
        System.out.println(l2);

    }
}
