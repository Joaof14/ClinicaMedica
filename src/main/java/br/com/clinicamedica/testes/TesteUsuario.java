package br.com.clinicamedica.testes;

import br.com.clinicamedica.ConexaoDB;
import br.com.clinicamedica.Usuario;

import java.util.List;

import static br.com.clinicamedica.Usuario.listarUsuarios;

public class TesteUsuario {
    public static void main(String[] args) {
        // Como testar a instanciação de usuario ?

        // Como testar cadastrarUsuario ? -> não é uma implementação usável conforme o projeto

        // verUsuario+toString a partide funcionario

        // atualizarUsuario a partir de funcionario

        // deletarUsuario a parti de funcionario

        // autenticar

        // listarUsuarios
        System.out.println("Teste de listagem de usuário");
        List<Usuario> l1 = listarUsuarios();

        for( Usuario u: l1){
            System.out.println(u);
        }

        // teste das implementações set's
    }
}
