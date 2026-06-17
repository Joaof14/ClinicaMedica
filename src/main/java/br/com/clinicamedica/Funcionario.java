package br.com.clinicamedica;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Funcionario extends Usuario {
    private double salario;
    private int cargaHorariaSemanal;
    private String turno;
    private boolean atendente;

    private static final double SALARIO_MINIMO = 1621.00;
    private static final double SALARIO_MAXIMO = 5000.00;
    private static final int CARGA_HORARIA_MINIMA = 20;
    private static final int CARGA_HORARIA_MAXIMA = 44;
    private static final Set<String> TURNOS_VALIDOS = Set.of("manhã", "tarde", "noite", "integral");

    public Funcionario(String nome, int idade, String sexo, String cpf, String telefone, String login, String senha,
            boolean ativo, double salario, int cargaHorariaSemanal, String turno, boolean atendente) {
        super(nome, idade, sexo, cpf, telefone, login, senha, ativo);
        this.salario = salario;
        this.cargaHorariaSemanal = cargaHorariaSemanal;
        this.turno = turno;
        this.atendente = atendente;
    }

    private static void validarSalario(double salario) {
        if (salario < SALARIO_MINIMO)
            throw new IllegalArgumentException(
                    String.format("O salario nao pode ser inferior ao salario minimo: R$ %.2f.", SALARIO_MINIMO));
        if (salario > SALARIO_MAXIMO)
            throw new IllegalArgumentException(
                    String.format("O salario nao pode ser superior ao salario maximo: R$ %.2f.", SALARIO_MAXIMO));
    }

    private static void validarCargaHoraria(int cargaHoraria) {
        if (cargaHoraria < CARGA_HORARIA_MINIMA)
            throw new IllegalArgumentException(
                    String.format("A carga horaria semanal nao pode ser inferior a %d horas.", CARGA_HORARIA_MINIMA));
        if (cargaHoraria > CARGA_HORARIA_MAXIMA)
            throw new IllegalArgumentException(
                    String.format("A carga horaria semanal nao pode ser superior a %d horas.", CARGA_HORARIA_MAXIMA));
    }

    private static void validarTurno(String turno) {
        if (turno == null || !TURNOS_VALIDOS.contains(turno.toLowerCase()))
            throw new IllegalArgumentException("Turno invalido. Os turnos validos sao: " + TURNOS_VALIDOS);
    }

    private static void validarCampos(double salario, int cargaHoraria, String turno) {
        validarSalario(salario);
        validarCargaHoraria(cargaHoraria);
        validarTurno(turno);
    }

    public static Funcionario cadastrarFuncionario(String nome, int idade, String sexo, String cpf, String telefone,
            String login, String senha, boolean ativo, double salario, int cargaHorariaSemanal, String turno,
            boolean atendente) {
        Connection conn = null;
        String sql = "INSERT INTO funcionarios (id_tb_usuario, salario, carga_horaria_semanal, turno, atendente) VALUES (?, ?, ?, ?, ?)";
        String sqlUsuario = "INSERT INTO usuarios (nome, idade, sexo, cpf, telefone, login, senha, ativo) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            conn = ConexaoDB.obterConexao();
            conn.setAutoCommit(false);
            long idUsuario;

            try (PreparedStatement stmtUsuario = conn.prepareStatement(sqlUsuario, Statement.RETURN_GENERATED_KEYS)) {
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
                    if (!generatedKeys.next())
                        throw new SQLException("Falha ao obter ID do usuario");
                    idUsuario = generatedKeys.getLong(1);
                }
            }

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setLong(1, idUsuario);
                stmt.setDouble(2, salario);
                stmt.setInt(3, cargaHorariaSemanal);
                stmt.setString(4, turno);
                stmt.setBoolean(5, atendente);
                stmt.executeUpdate();
            }

            conn.commit();
            return new Funcionario(nome, idade, sexo, cpf, telefone, login, senha, ativo, salario, cargaHorariaSemanal,
                    turno, atendente);
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    System.out.println("Erro ao realizar rollback: " + ex.getMessage());
                }
            }
            System.out.println("Erro ao cadastrar funcionario: " + e.getMessage());
            return null;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException ex) {
                    System.out.println("Erro ao fechar conexao: " + ex.getMessage());
                }
            }
        }
    }

    public void verFuncionario() {
        System.out.println("Imprimindo Funcionário:\n");
        System.out.println("==========================");
        System.out.println(this.toString());
        System.out.println("=========================");
    }

    public static boolean atualizarFuncionario(String cpf, double novoSalario, int novaCargaHoraria, String novoTurno,
            boolean novoAtendente) {
        validarCampos(novoSalario, novaCargaHoraria, novoTurno);
        String sql = "UPDATE funcionarios SET salario = ?, carga_horaria_semanal = ?, turno = ?, atendente = ? " +
                "WHERE id_tb_usuario = (SELECT id_tb_usuario FROM usuarios WHERE cpf = ?)";

        try (Connection conn = ConexaoDB.obterConexao();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, novoSalario);
            stmt.setInt(2, novaCargaHoraria);
            stmt.setString(3, novoTurno);
            stmt.setBoolean(4, novoAtendente);
            stmt.setString(5, cpf);
            int n = stmt.executeUpdate();
            if (n == 0) {
                System.out.println("Nenhum funcionario encontrado com o CPF: " + cpf);
                return false;
            }
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar funcionario: " + e.getMessage());
            return false;
        }
    }

    public static boolean deletarFuncionario(String cpf) {
        if (cpf == null || cpf.isBlank()) {
            System.out.println("CPF invalido. O CPF nao pode ser nulo ou vazio.");
            return false;
        }

        Connection conn = null;
        try {
            conn = ConexaoDB.obterConexao();
            conn.setAutoCommit(false);
            long idUsuario;

            try (PreparedStatement stmt = conn.prepareStatement("SELECT id_tb_usuario FROM usuarios WHERE cpf = ?")) {
                stmt.setString(1, cpf);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (!rs.next()) {
                        conn.rollback();
                        return false;
                    }
                    idUsuario = rs.getLong("id_tb_usuario");
                }
            }
            try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM funcionarios WHERE id_tb_usuario = ?")) {
                stmt.setLong(1, idUsuario);
                stmt.executeUpdate();
            }
            try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM usuarios WHERE id_tb_usuario = ?")) {
                stmt.setLong(1, idUsuario);
                stmt.executeUpdate();
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
            System.out.println("Erro ao deletar funcionario: " + e.getMessage());
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException ex) {
                    System.out.println("Erro ao fechar conexao: " + ex.getMessage());
                }
            }
        }
    }

    public static List<Funcionario> listarFuncionario() {
        List<Funcionario> funcionarios = new ArrayList<>();

        String sql = "SELECT u.nome, u.idade, u.sexo, u.cpf, u.telefone, u.login, u.senha, u.ativo, " +
                "f.salario, f.carga_horaria_semanal, f.turno, f.atendente " +
                "FROM usuarios u " +
                "JOIN funcionarios f ON f.id_tb_usuario = u.id_tb_usuario";

        try (Connection conn = ConexaoDB.obterConexao();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
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
                        rs.getBoolean("atendente"));
                funcionarios.add(funcionario);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar funcionários: " + e.getMessage());
        }

        return funcionarios;
    }

    private static Funcionario mapearFuncionario(ResultSet rs) throws SQLException {
        return new Funcionario(
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
                rs.getBoolean("atendente"));
    }

    public static List<Funcionario> listarFuncionariosPorPapel(boolean atendente) {
        List<Funcionario> funcionarios = new ArrayList<>();

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

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    funcionarios.add(mapearFuncionario(rs));
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar funcionários: " + e.getMessage());
        }

        return funcionarios;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public int getCargaHorariaSemanal() {
        return cargaHorariaSemanal;
    }

    public void setCargaHorariaSemanal(int cargaHorariaSemanal) {
        this.cargaHorariaSemanal = cargaHorariaSemanal;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public boolean isAtendente() {
        return atendente;
    }

    public void setAtendente(boolean atendente) {
        this.atendente = atendente;
    }

    @Override
    public String toString() {

        return String.format(
                super.toString() +
                        "Salário: R$ %.2f%n" +
                        "Carga horaria semanal: %d%n" +
                        "Turno: %s%n" +
                        "Atendente: %s%n",
                salario,
                cargaHorariaSemanal, turno, (atendente ? "sim" : "não"));
    }
}
