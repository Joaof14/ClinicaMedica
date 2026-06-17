package br.com.clinicamedica.testes;

import br.com.clinicamedica.ConexaoDB;
import br.com.clinicamedica.Funcionario;

import java.util.List;

import static br.com.clinicamedica.Funcionario.listarFuncionario;
import static br.com.clinicamedica.Funcionario.listarFuncionariosPorPapel;

public class TesteFuncionario {
    public static void main(String[] args) {
        // Instancia Funcionario

        // cadastrarFuncionario

        // verFuncionario+toString

        // atualizarFuncionario

        // deletarFuncionario

        System.out.println("Teste de listar funcionário:");
        List<Funcionario> l1 = listarFuncionario();
        System.out.println(l1);

        System.out.println(" ");

        System.out.println("Teste de listar por papel = true = atendente");
        List<Funcionario> l2 = listarFuncionariosPorPapel(true);
        System.out.println(l2);

        // teste set's
    }
}
