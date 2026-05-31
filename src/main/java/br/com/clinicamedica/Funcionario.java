package br.com.clinicamedica;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

    public void verFuncionario(){
        System.out.println("Imprimindo Funcionário:\n");
        System.out.println("==========================");
        System.out.println(this.toString());
        System.out.println("=========================");
    }

    public void atualizarFuncionario(){
        // implementar
    }

    public void deletarFuncionario(){
        // implementar
    }

    public List<Funcionario> listarFuncionario(){
        // implementar
        List <Funcionario> funcionarios = new ArrayList<>();

        // JOIN para trazer dados de usuarios + funcionarios
        String sql = "SELECT u.nome, u.idade, u.sexo, u.cpf, u.telefone, u.login, u.senha, u.ativo, " +
                "f.salario, f.carga_horaria_semanal, f.turno, f.atendente " +
                "FROM usuarios u " +
                "JOIN funcionarios f ON f.id_tb_usuario = u.id_tb_usuario";

        try (Connection conn = ConexaoDB.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql); // modifica a query
             ResultSet rs = stmt.executeQuery()) { // executa

            while (rs.next()) {
                // Campos comuns (reutilizados de Usuario)
                Funcionario funcionario = new Funcionario(
                        rs.getString("nome"),
                        rs.getInt("idade"),
                        rs.getString("sexo"),
                        rs.getString("cpf"),
                        rs.getString("telefone"),
                        rs.getString("login"),
                        rs.getString("senha"),
                        rs.getBoolean("ativo"),
                        rs.getDouble("salario"),
                        rs.getInt("carga_horaria_semanal"),
                        rs.getString("turno"),
                        rs.getBoolean("atendente")
                );
                // Adiciona na lista para depois retornar a lista
                funcionarios.add(funcionario);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar funcionários: "+e.getMessage());
        }

        return funcionarios;
    }

    public List<Funcionario> listarFuncionariosPorPapel(boolean atendente){
        // implementar
        List <Funcionario> funcionarios = new ArrayList<>();

        /* Query para agrupar por papel, como não existe um campo no banco, o trabalho é feito na query
        * Dessa forma permite adicionar mais funções posteriormente
        * */
        String sql = """
        SELECT u.nome, u.idade, u.sexo, u.cpf, u.telefone, u.login, u.senha, u.ativo,
               f.salario, f.carga_horaria_semanal, f.turno, f.atendente
        FROM usuarios u
        JOIN funcionarios f ON f.id_tb_usuario = u.id_tb_usuario
        WHERE f.atendente = ?
        ORDER BY u.nome
        """;

        try (Connection conn = ConexaoDB.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setBoolean(1, atendente);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                // Campos comuns (reutilizados de Usuario)
                Funcionario funcionario = new Funcionario(
                        rs.getString("nome"),
                        rs.getInt("idade"),
                        rs.getString("sexo"),
                        rs.getString("cpf"),
                        rs.getString("telefone"),
                        rs.getString("login"),
                        rs.getString("senha"),
                        rs.getBoolean("ativo"),
                        rs.getDouble("salario"),
                        rs.getInt("carga_horaria_semanal"),
                        rs.getString("turno"),
                        rs.getBoolean("atendente")
                );

                // Adiciona na lista para depois retornar a lista
                funcionarios.add(funcionario);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar funcionários: "+e.getMessage());
        }

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

        return String.format(
                super.toString() +
                "Salário: R$ %.2f%n" +
                        "Carga horaria semanal: %d%n" +
                        "Turno: %s%n" +
                        "Atendente: %s%n" +
                salario, cargaHorariaSemanal, turno, (atendente ? "sim" : "não")
        );
    }
}
