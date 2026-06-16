package br.com.clinicamedica;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Medico extends Funcionario {
    private String areaDeAtuacao;
    private String crm;

    // pode utilizar set's da classe para validações
    public Medico(String nome, int idade, String sexo, String cpf, String telefone, String login, String senha,
            boolean ativo, double salario, int cargaHorariaSemanal, String turno, boolean atendente,
            String areaDeAtuacao, String crm) {
        super(nome, idade, sexo, cpf, telefone, login, senha, ativo, salario, cargaHorariaSemanal, turno, atendente);
        this.areaDeAtuacao = areaDeAtuacao;
        this.crm = crm;
    }

    public void verMedico() {
        System.out.println("Imprimindo Médico:\n");
        System.out.println("==========================");
        System.out.println(this.toString());
        System.out.println("=========================");
    }

    // modificar a posteriori o retorno de void para Medico
    public static void cadastrarMedico() {
        // implementar
    }

    public static void atualizarMedico() {
        // implementar
    }

    public static void deletarMedico() {
        // implementar
    }

    public static List<Medico> listarMedicos() {
        // implementar
        List<Medico> medicos = new ArrayList<>();

        // JOIN para trazer dados de usuarios + funcionarios
        String sql = "SELECT u.nome, u.idade, u.sexo, u.cpf, u.telefone, u.login, u.senha, u.ativo, " +
                "f.salario, f.carga_horaria_semanal, f.turno, f.atendente, " +
                "m.area_de_atuacao, m.crm " +
                "FROM usuarios u " +
                "JOIN funcionarios f ON f.id_tb_usuario = u.id_tb_usuario " +
                "JOIN medicos m ON m.id_tb_funcionario = f.id_tb_funcionario";

        try (Connection conn = ConexaoDB.obterConexao();
                PreparedStatement stmt = conn.prepareStatement(sql);) {

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Medico medico = new Medico(
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
                            rs.getBoolean("atendente"),
                            rs.getString("area_de_atuacao"),
                            rs.getString("crm"));

                    // Adiciona na lista para depois retornar a lista
                    medicos.add(medico);
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar funcionários: " + e.getMessage());
        }

        return medicos;
    }

    public static List<Medico> listarMedicosPorArea(String areaDeAtuacao) {
        // implementar
        List<Medico> medicos = new ArrayList<>();

        String sql = "SELECT u.nome, u.idade, u.sexo, u.cpf, u.telefone, u.login, u.senha, u.ativo, " +
                "f.salario, f.carga_horaria_semanal, f.turno, f.atendente, " +
                "m.area_de_atuacao, m.crm " +
                "FROM usuarios u " +
                "JOIN funcionarios f ON f.id_tb_usuario = u.id_tb_usuario " +
                "JOIN medicos m ON m.id_tb_funcionario = f.id_tb_funcionario " +
                "WHERE m.area_de_atuacao = ?";

        try (Connection conn = ConexaoDB.obterConexao();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, areaDeAtuacao);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Medico medico = new Medico(
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
                        rs.getBoolean("atendente"),
                        rs.getString("area_de_atuacao"),
                        rs.getString("crm"));

                medicos.add(medico);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar Médicos: " + e.getMessage());
        }

        return medicos;
    }

    public String getAreaDeAtuacao() {
        return areaDeAtuacao;
    }

    public void setAreaDeAtuacao(String areaDeAtuacao) {
        this.areaDeAtuacao = areaDeAtuacao;
    }

    public String getCrm() {
        return crm;
    }

    public void setCrm(String crm) {
        this.crm = crm;
    }

    // Adequar para trazer o construtor da classe mãe
    @Override
    public String toString() {
        return String.format(
                super.toString() +
                        "Área de atuação: %s%n" +
                        "CRM: %s%n",
                areaDeAtuacao, crm);
    }
}
