package br.com.clinicamedica;

import java.util.ArrayList;
import java.util.List;

public abstract class Usuario {

    private String nome;
    private int idade;
    private String sexo;
    private String cpf;
    private String telefone;
    private String login;
    private String senha;
    private boolean ativo;

    /* cadastrarUsuario se tornou o construtor Usuario vide aula 29_05_26 */
    public Usuario(String nome, int idade, String sexo, String cpf, String telefone, String login, String senha, boolean ativo) {
        setNome(nome);
        setIdade(idade);
        setSexo(sexo);
        setCpf(cpf);
        setTelefone(telefone);
        setLogin(login);
        setSenha(senha);
        setAtivo(ativo);
    }

    public void verUsuario() {
        System.out.println("Imprimindo usuário:\n");
        System.out.println("==========================");
        System.out.println(this.toString());
        System.out.println("=========================");
    }

    /* método estático para atualizar usuário, uso implementação dos métodos set para validacao*/
    public static void atualizarUsuario(Usuario usuario, String nome, int idade, String sexo, String cpf, String telefone, String login, String senha, boolean ativo) {
        usuario.setNome(nome);
        usuario.setIdade(idade);
        usuario.setSexo(sexo);
        usuario.setCpf(cpf);
        usuario.setTelefone(telefone);
        usuario.setLogin(login);
        usuario.setSenha(senha);
        usuario.setAtivo(ativo);

        System.out.println("Atualização concluída!");
    }

    /* Como regra de negócio não faz sentido no contexto de um sistema de clínica médica, 
    pois não existe a possibilidade de deletar um usuário, apenas desativá-lo, perde um registro médico
    pode se tornar um problema crítico.
    public static void deletarUsuario(Usuario usuario){
        // busco usuario no banco de dados, se existir, deleto
    }
     */

    /* método estático para autenticar usuário */
    public static boolean autenticar(String login, String senha) {
        // fazer
        // busco usuário no banco de dados, se existir, comparo senha, se bater, retorno true, senão retorno false
        return false;
    }

    /* método estático para listar todos os usuários */
    public static List<Usuario> listarUsuarios() {
        // fazer
        // acesso ao banco de dados, busco todos os usuários, retorno como lista
        return new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
       try {
            if (nome == null || nome.isBlank())
                throw new IllegalArgumentException("Nome nao pode ser nulo ou vazio!");
            if (!nome.matches("[a-zA-ZÁ-ú]+"))
                throw new IllegalArgumentException("Nome deve conter apenas letras!");
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
    public String toString(){
        String s = "";
        s += String.format("Nome: %s120 \n", nome);
        s += String.format("idade: %3d \n", idade);
        s += String.format("sexo: %s \n",sexo);
        s += String.format("cpf: %11s \n", cpf);
        s += String.format("telefone: %11s \n", telefone);
        s += String.format("login: %30s \n", login);
        s += String.format("Usuário ativo: %s \n", (ativo?"sim":"não"));

        return s;
    }
}