package br.com.clinicamedica;

import java.util.ArrayList;
import java.util.List;

public class Funcionario extends Usuario{
    private double salario;
    private int cargaHorariaSemanal;
    private String turno;
    private boolean atendente;

    // pode utilizar set's da classe para validações
    public Funcionario(String nome, int idade, String sexo, String cpf, String telefone, String login, String senha, boolean ativo, double salario, int cargaHorariaSemanal, String turno, boolean atendente) {
        super(nome, idade, sexo, cpf, telefone, login, senha, ativo);
        this.salario = salario;
        this.cargaHorariaSemanal = cargaHorariaSemanal;
        this.turno = turno;
        this.atendente = atendente;
    }

    // modificar a posteriori o retorno de void para Funcionario
    public void cadastrarFuncionario(String nome, int idade, String sexo, String cpf, String telefone, String login, String senha, boolean ativo, double salario, int cargaHorariaSemanal, String turno, boolean atendente){
        // implementar
        //return new Funcionario( nome,  idade,  sexo,  cpf, telefone, login,  senha,  ativo,  salario,  cargaHorariaSemanal,  turno,  atendente);
    }

    public void atualizarFuncionario(){
        // implementar
    }

    public void deletarMedico(){
        // implementar
    }

    public List<Funcionario> listarMedico(){
        // implementar
        List <Funcionario> funcionarios = new ArrayList<>();

        return funcionarios;
    }

    public List<Funcionario> listarFuncionariosPorPapel(){
        // implementar
        List <Funcionario> funcionarios = new ArrayList<>();

        return funcionarios;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        // atualizar
        this.salario = salario;
    }

    public int getCargaHorariaSemanal() {
        return cargaHorariaSemanal;
    }

    public void setCargaHorariaSemanal(int cargaHorariaSemanal) {
        // atualizar
        this.cargaHorariaSemanal = cargaHorariaSemanal;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        // atualizar
        this.turno = turno;
    }

    public boolean isAtendente() {
        return atendente;
    }

    public void setAtendente(boolean atendente) {
        // atualizar
        this.atendente = atendente;
    }

    // Adequar para trazer o construtor da classe mãe
    @Override
    public String toString() {
        return "Medico{" +
                "salario=" + salario +
                ", cargaHorariaSemanal=" + cargaHorariaSemanal +
                ", turno='" + turno + '\'' +
                ", atendente=" + atendente +
                '}';
    }
}
