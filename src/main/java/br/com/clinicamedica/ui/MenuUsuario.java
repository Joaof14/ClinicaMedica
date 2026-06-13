package br.com.clinicamedica.ui;

public class MenuUsuario {

    public static String lerNome() {
        return Utils.lerTexto("Nome: ");
    }

    public static int lerIdade() {
        return Utils.lerInteiro("Idade: ");
    }

    public static String lerSexo() {
        return Utils.lerTexto("Sexo: ");
    }

    public static String lerCpf() {
        return Utils.lerTexto("CPF: ");
    }

    public static String lerTelefone() {
        return Utils.lerTexto("Telefone: ");
    }

    public static String lerLogin() {
        return Utils.lerTexto("Login: ");
    }

    public static String lerSenha() {
        return Utils.lerTexto("Senha: ");
    }

    public static boolean lerAtivo() {
        return Utils.lerBoolean("Ativo? (true/false): ");
    }

    public static void exibirDadosBasicos(Usuario usuario) {
        System.out.println("Nome: " + usuario.getNome());
        System.out.println("Idade: " + usuario.getIdade());
        System.out.println("Sexo: " + usuario.getSexo());
        System.out.println("CPF: " + usuario.getCpf());
        System.out.println("Telefone: " + usuario.getTelefone());
        System.out.println("Login: " + usuario.getLogin());
        System.out.println("Ativo: " + usuario.isAtivo());
    }
}
    
}
