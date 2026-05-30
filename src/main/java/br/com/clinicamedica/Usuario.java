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
        // fazer
        // todo: validar nome, não pode ser vazio, nem nulo, nem conter números ou caracteres especiais
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        // fazer 
        this.idade = idade;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        // fazer
        this.sexo = sexo;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        // fazer
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        // fazer
        this.telefone = telefone;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        // fazer
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        // fazer
        this.senha = senha;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        // fazer
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