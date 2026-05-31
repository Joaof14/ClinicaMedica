package br.com.clinicamedica;

import java.util.ArrayList;
import java.util.List;

public class Medico extends Funcionario{
    private String areaDeAtuacao;
    private String crm;

    // pode utilizar set's da classe para validações
    public Medico(String nome, int idade, String sexo, String cpf, String telefone, String login, String senha, boolean ativo, double salario, int cargaHorariaSemanal, String turno, boolean atendente, String areaDeAtuacao, String crm) {
        super(nome, idade, sexo, cpf, telefone, login, senha, ativo, salario, cargaHorariaSemanal, turno, atendente);
        this.areaDeAtuacao = areaDeAtuacao;
        this.crm = crm;
    }

    public void verMedico(){
        // implementar
    }

    // modificar a posteriori o retorno de void para Medico
    public void cadastrarMedico(){
        // implementar
    }

    public void atualizarMedico(){
        // implementar
    }

    public void deletarMedico(){
        // implementar
    }

    public List<Medico> listarMedicos(){
        // implementar
        List <Medico> medicos = new ArrayList<>();

        return medicos;
    }

    public List<Medico> listarFuncionariosPorArea(){
        // implementar
        List <Medico> medicos = new ArrayList<>();

        return medicos;
    }

    // Adequar para trazer o construtor da classe mãe
    @Override
    public String toString() {
        return "Medico{" +
                "areaDeAtuacao='" + areaDeAtuacao + '\'' +
                ", crm='" + crm + '\'' +
                '}';
    }
}
