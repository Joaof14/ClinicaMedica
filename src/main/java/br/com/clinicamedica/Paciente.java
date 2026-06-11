package br.com.clinicamedica;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Paciente extends Usuario{
    private float peso;
    private float altura;
    private String sintomas;
    
    private static List<Paciente> pacientes = new ArrayList<>();


    public Paciente(float altura, float peso, String sintomas, String nome, int idade, String sexo, String cpf, String telefone, String login, String senha, boolean ativo) {
        super(nome, idade, sexo, cpf, telefone, login, senha, ativo);
        this.altura = altura;
        this.peso = peso;
        this.sintomas = sintomas;
    }

    
    public void verPaciente(){
        System.out.println("Imprimindo Paciente:\n");
        System.out.println("==========================");
        System.out.println(this.toString());
        System.out.println("=========================");
    }

    public static Paciente cadastrarPaciente(float altura, float peso, String sintomas, String nome, int idade, String sexo, String cpf, String telefone, String login, String senha, boolean ativo) {
                Connection conn = null;
        try {
            conn = ConexaoDB.obterConexao();
            conn.setAutoCommit(false);

            // 1. Inserir na tabela usuarios
            String sqlUsuario = "INSERT INTO usuarios (nome, idade, sexo, cpf, telefone, login, senha, ativo) " +
                               "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            
            PreparedStatement stmtUsuario = conn.prepareStatement(sqlUsuario);
            stmtUsuario.setString(1, nome);
            stmtUsuario.setInt(2, idade);
            stmtUsuario.setString(3, sexo);
            stmtUsuario.setString(4, cpf);
            stmtUsuario.setString(5, telefone);
            stmtUsuario.setString(6, login);
            stmtUsuario.setString(7, senha);
            stmtUsuario.setBoolean(8, ativo);
            stmtUsuario.executeUpdate();
            stmtUsuario.close();

            // 2. Buscar o ID do usuário recém-criado
            String sqlBuscarId = "SELECT id_tb_usuario FROM usuarios WHERE cpf = ?";
            PreparedStatement stmtBuscar = conn.prepareStatement(sqlBuscarId);
            stmtBuscar.setString(1, cpf);
            ResultSet rs = stmtBuscar.executeQuery();
            rs.next();
            int idUsuario = rs.getInt(1);
            rs.close();
            stmtBuscar.close();

            // 3. Inserir na tabela pacientes
            String sqlPaciente = "INSERT INTO pacientes (id_tb_usuario, peso, altura, sintomas) " +
                                "VALUES (?, ?, ?, ?)";
            
            PreparedStatement stmtPaciente = conn.prepareStatement(sqlPaciente);
            stmtPaciente.setInt(1, idUsuario);
            stmtPaciente.setFloat(2, peso);
            stmtPaciente.setFloat(3, altura);
            stmtPaciente.setString(4, sintomas);
            stmtPaciente.executeUpdate();
            stmtPaciente.close();

            conn.commit();

            Paciente paciente = new Paciente(altura, peso, sintomas, nome, idade, sexo, cpf, telefone, login, senha, ativo );
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

    public void atualizarPaciente(Paciente paciente, float altura, float peso, String sintomas, String nome, int idade, String sexo, String cpf, String telefone, String login, String senha, boolean ativo){
                String sqlUsuario = "UPDATE usuarios SET nome = ?, idade = ?, sexo = ?, cpf = ?, " +
                           "telefone = ?, login = ?, senha = ?, ativo = ? WHERE cpf = ?";
        
        String sqlAtualizarPaciente = "UPDATE pacientes SET peso = ?, altura = ?, sintomas = ? " +
                                     "FROM usuarios u " +
                                     "WHERE pacientes.id_tb_usuario = u.id_tb_usuario " +
                                     "AND u.cpf = ?";

        Connection conn = null;
        try {
            conn = ConexaoDB.obterConexao();
            conn.setAutoCommit(false);

            // Atualizar dados na tabela usuarios
            PreparedStatement stmtUsuario = conn.prepareStatement(sqlUsuario);
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
            stmtUsuario.close();

            // Atualizar dados do paciente usando CPF
            PreparedStatement stmtPaciente = conn.prepareStatement(sqlAtualizarPaciente);
            stmtPaciente.setFloat(1, peso);
            stmtPaciente.setFloat(2, altura);
            stmtPaciente.setString(3, sintomas);
            stmtPaciente.setString(4, this.getCpf());
            stmtPaciente.executeUpdate();
            stmtPaciente.close();

            conn.commit();

            // Atualizar atributos do objeto
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

    public void deletarPaciente(Paciente paciente){
        Connection conn = null;
        try {
            conn = ConexaoDB.obterConexao();
            conn.setAutoCommit(false);

            // Deletar da tabela pacientes (via JOIN com CPF)
            String sqlPaciente = "DELETE FROM pacientes WHERE id_tb_usuario IN " +
                                "(SELECT id_tb_usuario FROM usuarios WHERE cpf = ?)";
            PreparedStatement stmtPaciente = conn.prepareStatement(sqlPaciente);
            stmtPaciente.setString(1, this.getCpf());
            stmtPaciente.executeUpdate();
            stmtPaciente.close();

            // Deletar da tabela usuarios (ON DELETE CASCADE cuida do resto)
            String sqlUsuario = "DELETE FROM usuarios WHERE cpf = ?";
            PreparedStatement stmtUsuario = conn.prepareStatement(sqlUsuario);
            stmtUsuario.setString(1, this.getCpf());
            stmtUsuario.executeUpdate();
            stmtUsuario.close();

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
                    rs.getFloat("altura"),
                    rs.getFloat("peso"),
                    rs.getString("sintomas"),
                    rs.getString("nome"),
                    rs.getInt("idade"),
                    rs.getString("sexo"),
                    rs.getString("cpf"),
                    rs.getString("telefone"),
                    rs.getString("login"),
                    rs.getString("senha"),
                    rs.getBoolean("ativo")
                    
                );
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
                    rs.getFloat("altura"),
                    rs.getFloat("peso"),
                    rs.getString("sintomas"),
                    rs.getString("nome"),
                    rs.getInt("idade"),
                    rs.getString("sexo"),
                    rs.getString("cpf"),
                    rs.getString("telefone"),
                    rs.getString("login"),
                    rs.getString("senha"),
                    rs.getBoolean("ativo")
                );
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
        sb.append("Paciente{");
        sb.append("peso=").append(peso);
        sb.append(", altura=").append(altura);
        sb.append(", sintomas=").append(sintomas);
        sb.append('}');
        return sb.toString();
    }
}