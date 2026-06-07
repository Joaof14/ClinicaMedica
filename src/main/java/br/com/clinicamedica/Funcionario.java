package br.com.clinicamedica;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Funcionario extends Usuario {
    private double salario;
    private int cargaHorariaSemanal;
    private String turno;
    private boolean atendente;

    private static final double SALARIO_MINIMO = 1621.00;
    private static final double SALARIO_MAXIMO = 5000.00;
    private static final int CARGA_HORARIA_MINIMA = 20;
    private static final int CARGA_HORARIA_MAXIMA = 44;
    private static final Set<String> TURNOS_VALIDOS = Set.of("manhã", "tarde", "noite");

    // pode utilizar set's da classe para validações
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
            throw new IllegalArgumentException("O salario nao pode ser inferior ao salario minimo: R$ %.2f.", SALARIO_MINIMO);
        if (salario > SALARIO_MAXIMO)
            throw new IllegalArgumentException("O salario nao pode ser superior ao salario maximo: R$ %.2f.", SALARIO_MAXIMO);
    }

    private static void validarCargaHoraria(int cargaHoraria) {
        if (cargaHoraria < CARGA_HORARIA_MINIMA)
            throw new IllegalArgumentException("A carga horaria semanal nao pode ser inferior a %d horas.", CARGA_HORARIA_MINIMA);
        if (cargaHoraria > CARGA_HORARIA_MAXIMA)
            throw new IllegalArgumentException("A carga horaria semanal nao pode ser superior a %d horas.", CARGA_HORARIA_MAXIMA);
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

    // modificar a posteriori o retorno de void para Funcionario
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
                s.setString(1, nome);
                s.setInt(2, idade);
                s.setString(3,sexo);
                s.setString(4, cpf);
                s.setString(5, telefone);
                s.setString(6, login);
                s.setString(7, senha);
                s.setBoolean(8, ativo);
                s.executeUpdate();

                try (ResultSet k = s.getGeneratedKeys()) {
                    if (k.next()) 
                        throw new SQLException("Falha ao obter ID do usuario")
                    idUsuario = k.getLong(1);
                }
            }
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                s.setLong(1, idUsuario);
                s.setDouble(2, salario);
                s.setInt(3, cargaHorariaSemanal);
                s.setString(4, turno);
                s.setBoolean(5, atendente);
                s.executeUpdate();
            }
            conn.commit();
            return new Funcionario(nome, idade, sexo, cpf, telefone, login, senha, ativo, salario, cargaHorariaSemanal, turno, atendente);
        } catch (SQLException e) {
            if (conn != null) 
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    System.out.println("Erro ao realizar rollback: " + ex.getMessage());
                    return null;
                } finally {
                    if (conn != null)
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

    public static void atualizarFuncionario() {
        // implementar
    }

    public static void deletarFuncionario() {
        // implementar
    }

    public static List<Funcionario> listarFuncionario() {
        // implementar
        List<Funcionario> funcionarios = new ArrayList<>();

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
                        rs.getBoolean("atendente"));
                // Adiciona na lista para depois retornar a lista
                funcionarios.add(funcionario);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar funcionários: " + e.getMessage());
        }

        return funcionarios;
    }

    public static List<Funcionario> listarFuncionariosPorPapel(boolean atendente) {
        // implementar
        List<Funcionario> funcionarios = new ArrayList<>();

        /*
         * Query para agrupar por papel, como não existe um campo no banco, o trabalho é
         * feito na query
         * Dessa forma permite adicionar mais funções posteriormente
         */
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
                            rs.getBoolean("atendente"));

                    // Adiciona na lista para depois retornar a lista
                    funcionarios.add(funcionario);
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
                        "Atendente: %s%n",
                salario,
                cargaHorariaSemanal, turno, (atendente ? "sim" : "não"));
    }
}
