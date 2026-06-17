package br.com.clinicamedica;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.clinicamedica.Paciente;
import br.com.clinicamedica.Funcionario;

public abstract class Usuario {

    private String nome;
    private int idade;
    private String sexo;
    private String cpf;
    private String telefone;
    private String login;
    private String senha;
    private boolean ativo;

    /* cadastrarUsuario se tornou o construtor Usuario conforme aula 29_05_26 */
    public Usuario(String nome, int idade, String sexo, String cpf, String telefone, String login, String senha,
            boolean ativo) {
        setNome(nome);
        setIdade(idade);
        setSexo(sexo);
        setCpf(cpf);
        setTelefone(telefone);
        setLogin(login);
        setSenha(senha);
        setAtivo(ativo);
    }

    /* DIFERENTE DO UML: apenas cadastra usuario no banco */
    public void cadastrarUsuario(Usuario usuario) {
        String query = "INSERT INTO usuarios (nome, idade, sexo, cpf, telefone, login, senha, ativo) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexaoDB.obterConexao(); PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, usuario.getNome());
            stmt.setInt(2, usuario.getIdade());
            stmt.setString(3, usuario.getSexo());
            stmt.setString(4, usuario.getCpf());
            stmt.setString(5, usuario.getTelefone());
            stmt.setString(6, usuario.getLogin());
            stmt.setString(7, usuario.getSenha());
            stmt.setBoolean(8, usuario.isAtivo());

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Inserção concluída com sucesso no banco de dados!");
            }

        } catch (Exception error) {
            System.err.println("Erro ao atualizar no banco: " + error.getMessage());
        }
    }

    public void verUsuario() {
        System.out.println("Imprimindo usuário:\n");
        System.out.println("==========================");
        System.out.println(this.toString());
        System.out.println("=========================");
    }

    public static void atualizarUsuario(Usuario usuario, String nome, int idade, String sexo, String cpf,
            String telefone, String login, String senha, boolean ativo) {
        String query = "UPDATE usuarios SET nome = ?, idade = ?, sexo = ?, cpf = ?, telefone = ?, login = ?, senha = ?, ativo = ? WHERE cpf = ?";

        String cpfAntigo = usuario.getCpf();

        usuario.setNome(nome);
        usuario.setIdade(idade);
        usuario.setSexo(sexo);
        usuario.setCpf(cpf);
        usuario.setTelefone(telefone);
        usuario.setLogin(login);
        usuario.setSenha(senha);
        usuario.setAtivo(ativo);

        try (Connection conn = ConexaoDB.obterConexao(); PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, usuario.getNome());
            stmt.setInt(2, usuario.getIdade());
            stmt.setString(3, usuario.getSexo());
            stmt.setString(4, usuario.getCpf());
            stmt.setString(5, usuario.getTelefone());
            stmt.setString(6, usuario.getLogin());
            stmt.setString(7, usuario.getSenha());
            stmt.setBoolean(8, usuario.isAtivo());

            stmt.setString(9, cpfAntigo);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Atualização concluída com sucesso no banco de dados!");
            } else {
                System.out.println("Aviso: Nenhum usuário encontrado com o CPF informado para atualizar.");
            }

        } catch (Exception error) {
            System.err.println("Erro ao atualizar no banco: " + error.getMessage());
        }
    }

    public static void deletarUsuario(Usuario usuario) {
        if (usuario == null) {
            System.out.println("Erro: O objeto usuário fornecido é nulo");
            return;
        }

        String query = "DELETE FROM usuarios WHERE cpf = ?";

        try (Connection conn = ConexaoDB.obterConexao(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, usuario.getCpf());

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Usuário de CPF: " + usuario.getCpf()
                        + " e todas as suas dependências foram deletados com sucesso!");
            } else {
                System.out.println("Aviso: Nenhum usuário encontrado no banco com este CPF!");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao tentar deletar o usuário no banco: " + e.getMessage());
        }

    }

    /* Implementação para autenticar usuário */
    public static boolean autenticar(String login, String senha) {
        String sql = "SELECT * FROM usuarios WHERE login = ? AND senha = ? AND ativo = true";

        try (Connection conn = ConexaoDB.obterConexao();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, login);
            stmt.setString(2, senha);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            System.err.println("Erro ao autenticar usuário");
            return false;
        }
    }

    /* Implementação para listar todos os usuários */
    public static List<Usuario> listarUsuarios() {
        List<Usuario> todosUsuarios = new ArrayList<>();
        List<Paciente> pacientes = Paciente.listarPacientes();
        List<Funcionario> funcionarios = Funcionario.listarFuncionario();

        todosUsuarios.addAll(pacientes);
        todosUsuarios.addAll(funcionarios);

        return todosUsuarios;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        try {
            if (nome == null || nome.isBlank())
                throw new IllegalArgumentException("Nome nao pode ser nulo ou vazio!");
            if (!nome.matches("[a-zA-ZÁ-ú\\s]+"))
                throw new IllegalArgumentException("Nome deve conter apenas letras e espaços!");
            this.nome = nome.trim();
        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao setar nome: " + e.getMessage());
        }
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        try {
            if (idade < 0)
                throw new IllegalArgumentException("Idade nao pode ser negativa!");
            this.idade = idade;
        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao setar idade: " + e.getMessage());
        }
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        try {
            if (sexo == null || sexo.isBlank())
                throw new IllegalArgumentException("Sexo nao pode ser nulo ou vazio!");
            this.sexo = sexo;
        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao setar sexo: " + e.getMessage());
        }
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        try {
            if (cpf == null || cpf.isBlank())
                throw new IllegalArgumentException("CPF nao pode ser nulo ou vazio!");
            if (!cpf.matches("\\d{11}"))
                throw new IllegalArgumentException("CPF deve ter exatamente 11 digitos!");
            this.cpf = cpf;
        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao setar CPF: " + e.getMessage());
        }
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        try {
            if (telefone == null || telefone.isBlank())
                throw new IllegalArgumentException("Telefone nao pode ser nulo ou vazio!");
            if (!telefone.matches("\\d{10,11}"))
                throw new IllegalArgumentException("Telefone deve conter entre 10 e 11 digitos!");
            this.telefone = telefone;
        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao setar telefone: " + e.getMessage());
        }
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        try {
            if (login == null || login.isBlank())
                throw new IllegalArgumentException("Login nao pode ser nulo ou vazio!");
            this.login = login;
        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao setar login: " + e.getMessage());
        }
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        try {
            if (senha == null || senha.isBlank())
                throw new IllegalArgumentException("Senha nao pode ser nula ou vazia!");
            this.senha = senha;
        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao setar senha: " + e.getMessage());
        }
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    @Override
    public String toString() {
        return String.format(
                "Nome: %s%n" +
                        "Idade: %d%n" +
                        "Sexo: %s%n" +
                        "CPF: %s%n" +
                        "Telefone: %s%n" +
                        "Login: %s%n" +
                        "Usuário ativo: %s%n",
                nome, idade, sexo, cpf, telefone, login, (ativo ? "sim" : "não"));
    }
}