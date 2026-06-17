package br.com.clinicamedica.testes;

import br.com.clinicamedica.Funcionario;
import br.com.clinicamedica.Usuario;

import java.util.List;

import static br.com.clinicamedica.Usuario.listarUsuarios;

public class TesteUsuario {
    public static void main(String[] args) {

        Funcionario f = new Funcionario("Ana Lima", 28, "Feminino", "33344455566", "84999990004", "ana.lima",
                "senha000", true, 2500.0, 40, "Vespertino", true);

        f.cadastrarUsuario(f);

        f.verUsuario();

        Usuario.atualizarUsuario(f, "Ana Lima Costa", 29, "Feminino", "33344455566", "84999990004", "ana.costa",
                "senha111", true);

        Usuario.deletarUsuario(f);

        boolean auth = Usuario.autenticar("ana.costa", "senha111");
        System.out.println("Autenticado: " + auth);

        List<Usuario> lista = listarUsuarios();
        for (Usuario u : lista) {
            System.out.println(u);
        }
    }
}
