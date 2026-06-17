package br.com.clinicamedica;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Paciente extends Usuario {
    private float peso;
    private float altura;
    private String sintomas;

    private static List<Paciente> pacientes = new ArrayList<>();

    public Paciente(String nome, int idade, String sexo, String cpf, String telefone, String login, String senha,
            boolean ativo, float peso, float altura, String sintomas) {
        super(nome, idade, sexo, cpf, telefone, login, senha, ativo);
        this.peso = peso;
        this.altura = altura;
        this.sintomas = sintomas;
    }

    public void verPaciente() {
        System.out.println("Imprimindo Paciente:\n");
        System.out.println("==========================");
        System.out.println(this.toString());
        System.out.println("=========================");
    }

    public static Paciente cadastrarPaciente(String nome, int idade, String sexo, String cpf, String telefone,
            String login, String senha,
            boolean ativo, float peso, float altura, String sintomas) {
        Connection conn = null;

        try {
            conn = ConexaoDB.obterConexao();
            conn.setAutoCommit(false);

            String sqlUsuario = "INSERT INTO usuarios (nome, idade, sexo, cpf, telefone, login, senha, ativo) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            int idUsuario;
            try (PreparedStatement stmtUsuario = conn.prepareStatement(sqlUsuario,
                    java.sql.Statement.RETURN_GENERATED_KEYS)) {
                stmtUsuario.setString(1, nome);
                stmtUsuario.setInt(2, idade);
                stmtUsuario.setString(3, sexo);
                stmtUsuario.setString(4, cpf);
                stmtUsuario.setString(5, telefone);
                stmtUsuario.setString(6, login);
                stmtUsuario.setString(7, senha);
                stmtUsuario.setBoolean(8, ativo);
                stmtUsuario.executeUpdate();

                try (ResultSet rs = stmtUsuario.getGeneratedKeys()) {
                    if (!rs.next()) {
                        throw new SQLException("Falha ao obter ID do usuário gerado.");
                    }
                    idUsuario = rs.getInt(1);
                }
            }
            String sqlPaciente = "INSERT INTO pacientes (id_tb_usuario, peso, altura, sintomas) " +
                    "VALUES (?, ?, ?, ?)";

            try (PreparedStatement stmtPaciente = conn.prepareStatement(sqlPaciente)) {
                stmtPaciente.setInt(1, idUsuario);
                stmtPaciente.setFloat(2, peso);
                stmtPaciente.setFloat(3, altura);
                stmtPaciente.setString(4, sintomas);
                stmtPaciente.executeUpdate();
            }
            conn.commit();

            Paciente paciente = new Paciente(nome, idade, sexo, cpf, telefone, login, senha, ativo, peso, altura,
                    sintomas);
            System.out.println("Paciente cadastrado com sucesso! CPF: " + cpf);
            return paciente;

        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    System.err.println("Erro no rollback: " + ex.getMessage());
                }
            }
            System.err.println("Erro ao cadastrar paciente: " + e.getMessage());
            return null;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    System.err.println("Erro ao fechar conexão: " + e.getMessage());
                }
            }
        }
    }

    public void atualizarPaciente(Paciente paciente, String nome, int idade, String sexo, String cpf, String telefone,
            String login, String senha, boolean ativo, float altura, float peso, String sintomas) {
        String sqlUsuario = "UPDATE usuarios SET nome = ?, idade = ?, sexo = ?, cpf = ?, " +
                "telefone = ?, login = ?, senha = ?, ativo = ? WHERE cpf = ?";

        String sqlAtualizarPaciente = "UPDATE pacientes SET peso = ?, altura = ?, sintomas = ? " +
                "WHERE id_tb_usuario = (SELECT id_tb_usuario FROM usuarios WHERE cpf = ?)";

        Connection conn = null;
        try {

            conn = ConexaoDB.obterConexao();
            conn.setAutoCommit(false);

            try (PreparedStatement stmtUsuario = conn.prepareStatement(sqlUsuario)) {
                stmtUsuario.setString(1, nome);
                stmtUsuario.setInt(2, idade);
                stmtUsuario.setString(3, sexo);
                stmtUsuario.setString(4, cpf);
                stmtUsuario.setString(5, telefone);
                stmtUsuario.setString(6, login);
                stmtUsuario.setString(7, senha);
                stmtUsuario.setBoolean(8, ativo);
                stmtUsuario.setString(9, this.getCpf());
                stmtUsuario.executeUpdate();
            }

            try (PreparedStatement stmtPaciente = conn.prepareStatement(sqlAtualizarPaciente)) {
                stmtPaciente.setFloat(1, peso);
                stmtPaciente.setFloat(2, altura);
                stmtPaciente.setString(3, sintomas);
                stmtPaciente.setString(4, cpf);
                stmtPaciente.executeUpdate();
            }
            conn.commit();

            this.setNome(nome);
            this.setIdade(idade);
            this.setSexo(sexo);
            this.setCpf(cpf);
            this.setTelefone(telefone);
            this.setLogin(login);
            this.setSenha(senha);
            this.setAtivo(ativo);

            this.peso = peso;
            this.altura = altura;
            this.sintomas = sintomas;

            System.out.println("Paciente atualizado com sucesso!");

        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    System.err.println("Erro no rollback: " + ex.getMessage());
                }
            }
            System.err.println("Erro ao atualizar paciente: " + e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    System.err.println("Erro ao fechar conexão: " + e.getMessage());
                }
            }
        }
    }

    public void deletarPaciente(Paciente paciente) {
        Connection conn = null;

        try {
            conn = ConexaoDB.obterConexao();
            conn.setAutoCommit(false);

            String sqlPaciente = "DELETE FROM pacientes WHERE id_tb_usuario IN " +
                    "(SELECT id_tb_usuario FROM usuarios WHERE cpf = ?)";

            try (PreparedStatement stmtPaciente = conn.prepareStatement(sqlPaciente)) {
                stmtPaciente.setString(1, this.getCpf());
                stmtPaciente.executeUpdate();
            }
            String sqlUsuario = "DELETE FROM usuarios WHERE cpf = ?";
            try (PreparedStatement stmtUsuario = conn.prepareStatement(sqlUsuario)) {
                stmtUsuario.setString(1, this.getCpf());
                stmtUsuario.executeUpdate();
            }
            conn.commit();

            System.out.println("Paciente deletado com sucesso!");

        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    System.err.println("Erro no rollback: " + ex.getMessage());
                }
            }
            System.err.println("Erro ao deletar paciente: " + e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    System.err.println("Erro ao fechar conexão: " + e.getMessage());
                }
            }
        }
    }

    public static List<Paciente> listarPacientes() {
        List<Paciente> pacientes = new ArrayList<>();

        String sql = "SELECT u.nome, u.idade, u.sexo, u.cpf, u.telefone, " +
                "u.login, u.senha, u.ativo, p.peso, p.altura, p.sintomas " +
                "FROM usuarios u " +
                "JOIN pacientes p ON p.id_tb_usuario = u.id_tb_usuario " +
                "ORDER BY u.nome";

        try (Connection conn = ConexaoDB.obterConexao();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Paciente paciente = new Paciente(
                        rs.getString("nome"),
                        rs.getInt("idade"),
                        rs.getString("sexo"),
                        rs.getString("cpf"),
                        rs.getString("telefone"),
                        rs.getString("login"),
                        rs.getString("senha"),
                        rs.getBoolean("ativo"),
                        rs.getFloat("peso"),
                        rs.getFloat("altura"),
                        rs.getString("sintomas"));
                pacientes.add(paciente);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao listar pacientes: " + e.getMessage());
        }

        return pacientes;
    }

    public static List<Paciente> listarPacientesPorSintomas(String sintomas) {
        List<Paciente> pacientes = new ArrayList<>();

        String sql = "SELECT u.nome, u.idade, u.sexo, u.cpf, u.telefone, " +
                "u.login, u.senha, u.ativo, p.peso, p.altura, p.sintomas " +
                "FROM usuarios u " +
                "JOIN pacientes p ON p.id_tb_usuario = u.id_tb_usuario " +
                "WHERE p.sintomas ILIKE ?";

        try (Connection conn = ConexaoDB.obterConexao();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + sintomas + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Paciente paciente = new Paciente(
                        rs.getString("nome"),
                        rs.getInt("idade"),
                        rs.getString("sexo"),
                        rs.getString("cpf"),
                        rs.getString("telefone"),
                        rs.getString("login"),
                        rs.getString("senha"),
                        rs.getBoolean("ativo"),
                        rs.getFloat("peso"),
                        rs.getFloat("altura"),
                        rs.getString("sintomas"));
                pacientes.add(paciente);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao listar pacientes por sintomas: " + e.getMessage());
        }

        return pacientes;
    }

    public float getPeso() {
        return peso;
    }

    public float getAltura() {
        return altura;
    }

    public String getSintomas() {
        return sintomas;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }

    public void setAltura(float altura) {
        this.altura = altura;
    }

    public void setSintomas(String sintomas) {
        this.sintomas = sintomas;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("PACIENTE\n");
        sb.append(super.toString());
        sb.append("\npeso=").append(peso);
        sb.append("\naltura=").append(altura);
        sb.append("\nsintomas=").append(sintomas);
        return sb.toString();
    }
}