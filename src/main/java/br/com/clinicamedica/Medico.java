package br.com.clinicamedica;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Medico extends Funcionario {
    private String areaDeAtuacao;
    private String crm;

    private static final Set<String> AREAS_DE_ATUACAO_VALIDAS = Set.of(
            "uti", "ambulatorio", "pronto-socorro", "urgencia", "medicina-preventiva",
            "neurologia", "pneumologia", "ortopedia", "cardiologia", "clínica geral");

    public Medico(String nome, int idade, String sexo, String cpf, String telefone, String login, String senha,
            boolean ativo, double salario, int cargaHorariaSemanal, String turno, boolean atendente,
            String areaDeAtuacao, String crm) {
        super(nome, idade, sexo, cpf, telefone, login, senha, ativo, salario, cargaHorariaSemanal, turno, atendente);
        validarAreaDeAtuacao(areaDeAtuacao);
        validarCRM(crm);
        this.areaDeAtuacao = areaDeAtuacao;
        this.crm = crm;
    }

    private static void validarCRM(String crm) {
        if (crm == null || crm.trim().isEmpty()) {
            throw new IllegalArgumentException("CRM nao pode ser vazio.");
        }
        if (!crm.matches("(CRM-)?[A-Z]{2}-\\d{4,6}")) {
            throw new IllegalArgumentException(
                    String.format("CRM INVALIDO: %s. Formato deve ser 'UF-1234' ou 'CRM-UF-1234'.", crm));
        }
    }

    private static void validarAreaDeAtuacao(String areaDeAtuacao) {
        if (areaDeAtuacao == null || areaDeAtuacao.trim().isEmpty()) {
            throw new IllegalArgumentException("Area de atuacao nao pode ser vazia.");
        }
        if (!AREAS_DE_ATUACAO_VALIDAS.contains(areaDeAtuacao.toLowerCase())) {
            throw new IllegalArgumentException(
                    String.format("Area de atuacao invalida: %s.", areaDeAtuacao));
        }
    }

    public static void validarCamposMedicos(String areaDeAtuacao, String crm) {
        validarAreaDeAtuacao(areaDeAtuacao);
        validarCRM(crm);
    }

    public void verMedico() {
        System.out.println("Imprimindo Médico:\n");
        System.out.println("==========================");
        System.out.println(this.toString());
        System.out.println("=========================");
    }

    public static Medico cadastrarMedico(String nome, int idade, String sexo, String cpf, String telefone,
            String login, String senha, boolean ativo, double salario, int cargaHorariaSemanal,
            String turno, boolean atendente, String areaDeAtuacao, String crm) {

        areaDeAtuacao = areaDeAtuacao.trim();
        crm = crm.trim().toUpperCase();

        validarCamposMedicos(areaDeAtuacao, crm);

        String sqlInsertUsuario = "INSERT INTO usuarios (nome, idade, sexo, cpf, telefone, login, senha, ativo) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        String sqlInsertFuncionario = "INSERT INTO funcionarios (id_tb_usuario, salario, carga_horaria_semanal, turno, atendente) VALUES (?, ?, ?, ?, ?)";
        String sqlInsertMedico = "INSERT INTO medicos (id_tb_funcionario, area_de_atuacao, crm) VALUES (?, ?, ?)";

        Connection conn = null;

        try {
            conn = ConexaoDB.obterConexao();
            conn.setAutoCommit(false);

            long idUsuario;
            try (PreparedStatement stmtUsuario = conn.prepareStatement(sqlInsertUsuario,
                    Statement.RETURN_GENERATED_KEYS)) {
                stmtUsuario.setString(1, nome);
                stmtUsuario.setInt(2, idade);
                stmtUsuario.setString(3, sexo);
                stmtUsuario.setString(4, cpf);
                stmtUsuario.setString(5, telefone);
                stmtUsuario.setString(6, login);
                stmtUsuario.setString(7, senha);
                stmtUsuario.setBoolean(8, ativo);
                stmtUsuario.executeUpdate();

                try (ResultSet generatedKeys = stmtUsuario.getGeneratedKeys()) {
                    if (!generatedKeys.next()) {
                        throw new SQLException("Falha ao obter ID do usuario.");
                    }
                    idUsuario = generatedKeys.getLong(1);
                }
            }

            long idFuncionario;
            try (PreparedStatement stmtFuncionario = conn.prepareStatement(sqlInsertFuncionario,
                    Statement.RETURN_GENERATED_KEYS)) {
                stmtFuncionario.setLong(1, idUsuario);
                stmtFuncionario.setDouble(2, salario);
                stmtFuncionario.setInt(3, cargaHorariaSemanal);
                stmtFuncionario.setString(4, turno);
                stmtFuncionario.setBoolean(5, atendente);
                stmtFuncionario.executeUpdate();

                try (ResultSet generatedKeys = stmtFuncionario.getGeneratedKeys()) {
                    if (!generatedKeys.next()) {
                        throw new SQLException("Falha ao obter ID do funcionario.");
                    }
                    idFuncionario = generatedKeys.getLong(1);
                }
            }

            try (PreparedStatement stmtMedico = conn.prepareStatement(sqlInsertMedico)) {
                stmtMedico.setLong(1, idFuncionario);
                stmtMedico.setString(2, areaDeAtuacao);
                stmtMedico.setString(3, crm);
                stmtMedico.executeUpdate();
            }

            conn.commit();

            return new Medico(nome, idade, sexo, cpf, telefone, login, senha, ativo, salario,
                    cargaHorariaSemanal, turno, atendente, areaDeAtuacao, crm);

        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    System.out.println("Erro ao realizar rollback: " + ex.getMessage());
                }
            }
            System.out.println("Erro ao cadastrar medico: " + e.getMessage());
            return null;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    System.out.println("Erro ao fechar conexao: " + e.getMessage());
                }
            }
        }
    }

    public static boolean atualizarMedico(String crmAtual, String novaAreaDeAtuacao, String novoCRM) {
        novaAreaDeAtuacao = novaAreaDeAtuacao.trim();
        novoCRM = novoCRM.trim().toUpperCase();
        crmAtual = crmAtual.trim().toUpperCase();

        validarCamposMedicos(novaAreaDeAtuacao, novoCRM);

        String sql = "UPDATE medicos SET area_de_atuacao = ?, crm = ? WHERE crm = ?";

        try (Connection conn = ConexaoDB.obterConexao();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, novaAreaDeAtuacao);
            stmt.setString(2, novoCRM);
            stmt.setString(3, crmAtual);

            int n = stmt.executeUpdate();

            if (n == 0) {
                System.out.println("Nenhum medico encontrado com o CRM: " + crmAtual);
                return false;
            }

            return true;

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar medico: " + e.getMessage());
            return false;
        }
    }

    public static boolean deletarMedico(String crm) {
        if (crm == null || crm.trim().isEmpty()) {
            System.out.println("CRM nao pode ser vazio.");
            return false;
        }

        crm = crm.trim().toUpperCase();

        String sqlBusca = """
                SELECT u.id_tb_usuario, f.id_tb_funcionario
                FROM medicos m
                JOIN funcionarios f ON m.id_tb_funcionario = f.id_tb_funcionario
                JOIN usuarios u ON f.id_tb_usuario = u.id_tb_usuario
                WHERE m.crm = ?
                """;

        Connection conn = null;

        try {
            conn = ConexaoDB.obterConexao();
            conn.setAutoCommit(false);

            long idUsuario;
            long idFuncionario;

            try (PreparedStatement stmtBusca = conn.prepareStatement(sqlBusca)) {
                stmtBusca.setString(1, crm);

                try (ResultSet rs = stmtBusca.executeQuery()) {
                    if (!rs.next()) {
                        conn.rollback();
                        return false;
                    }

                    idUsuario = rs.getLong("id_tb_usuario");
                    idFuncionario = rs.getLong("id_tb_funcionario");
                }
            }

            try (PreparedStatement stmtDeleteMedico = conn.prepareStatement(
                    "DELETE FROM medicos WHERE id_tb_funcionario = ?")) {
                stmtDeleteMedico.setLong(1, idFuncionario);
                stmtDeleteMedico.executeUpdate();
            }

            try (PreparedStatement stmtDeleteFuncionario = conn.prepareStatement(
                    "DELETE FROM funcionarios WHERE id_tb_funcionario = ?")) {
                stmtDeleteFuncionario.setLong(1, idFuncionario);
                stmtDeleteFuncionario.executeUpdate();
            }

            try (PreparedStatement stmtDeleteUsuario = conn.prepareStatement(
                    "DELETE FROM usuarios WHERE id_tb_usuario = ?")) {
                stmtDeleteUsuario.setLong(1, idUsuario);
                stmtDeleteUsuario.executeUpdate();
            }

            conn.commit();
            return true;

        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    System.out.println("Erro ao realizar rollback: " + ex.getMessage());
                }
            }
            System.out.println("Erro ao deletar medico: " + e.getMessage());
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    System.out.println("Erro ao fechar conexao: " + e.getMessage());
                }
            }
        }
    }

    public static List<Medico> listarMedicos() {
        List<Medico> medicos = new ArrayList<>();

        String sql = "SELECT u.nome, u.idade, u.sexo, u.cpf, u.telefone, u.login, u.senha, u.ativo, " +
                "f.salario, f.carga_horaria_semanal, f.turno, f.atendente, " +
                "m.area_de_atuacao, m.crm " +
                "FROM usuarios u " +
                "JOIN funcionarios f ON f.id_tb_usuario = u.id_tb_usuario " +
                "JOIN medicos m ON m.id_tb_funcionario = f.id_tb_funcionario";

        try (Connection conn = ConexaoDB.obterConexao();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

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
            System.out.println("Erro ao listar médicos: " + e.getMessage());
        }

        return medicos;
    }

    public static List<Medico> listarMedicosPorArea(String areaDeAtuacao) {
        List<Medico> medicos = new ArrayList<>();

        areaDeAtuacao = areaDeAtuacao.trim();
        validarAreaDeAtuacao(areaDeAtuacao);

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

                    medicos.add(medico);
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar médicos por área: " + e.getMessage());
        }

        return medicos;
    }

    public String getAreaDeAtuacao() {
        return areaDeAtuacao;
    }

    public void setAreaDeAtuacao(String areaDeAtuacao) {
        validarAreaDeAtuacao(areaDeAtuacao);
        this.areaDeAtuacao = areaDeAtuacao;
    }

    public String getCrm() {
        return crm;
    }

    public void setCrm(String crm) {
        validarCRM(crm);
        this.crm = crm;
    }

    @Override
    public String toString() {
        return String.format(
                "MÉDICO\n" +
                super.toString() +
                        "Área de atuação: %s%n" +
                        "CRM: %s%n",
                areaDeAtuacao, crm);
    }
}