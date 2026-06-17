package br.com.clinicamedica.ui;

import java.util.List;

import br.com.clinicamedica.Funcionario;
import br.com.clinicamedica.Medico;
import br.com.clinicamedica.Paciente;
import br.com.clinicamedica.Usuario;

public class MenuPrincipal {

    private static String loginAutenticado;
    private static Usuario usuarioLogado;

    public static void main(String[] args) {
        System.out.println("|--------------------------------------------------|");
        System.out.println("|     SISTEMA GERENCIADOR CLÍNICA DE SAÚDE         |");
        System.out.println("|         UFERSA – Campus Pau dos Ferros           |");
        System.out.println("|--------------------------------------------------|");
        System.out.println();

        boolean loginRealizado = menuLogin();

        if (loginRealizado) {
            menuSelecaoPerfil();
        }

        Utils.fecharScanner();
        System.out.println("\nSistema encerrado. Até logo!");
    }

    private static boolean menuLogin() {
        int tentativas = 3;

        while (tentativas > 0) {
            System.out.println("|-------------------- LOGIN -----------------------|");

            String login = Utils.lerTexto("Login: ");
            String senha = Utils.lerTexto("Senha: ");

            if (Usuario.autenticar(login, senha)) {
                loginAutenticado = login;
                usuarioLogado = buscarUsuarioPorLogin(loginAutenticado);

                if (usuarioLogado != null) {
                    System.out.println("\nLogin realizado com sucesso!");
                    return true;
                } else {
                    System.out.println("\nUsuário autenticado, mas não foi localizado no sistema.");
                    return false;
                }
            }

            tentativas--;
            System.out.println("\nLogin ou senha inválidos.");

            if (tentativas > 0) {
                System.out.println("Tentativas restantes: " + tentativas);
                Utils.pausar();
            }
        }

        System.out.println("\nNúmero máximo de tentativas atingido.");
        return false;
    }

    private static Usuario buscarUsuarioPorLogin(String login) {
        List<Usuario> usuarios = Usuario.listarUsuarios();

        for (Usuario usuario : usuarios) {
            if (usuario.getLogin().equals(login)) {
                return usuario;
            }
        }

        return null;
    }

    private static void menuSelecaoPerfil() {
        if (usuarioLogado instanceof Paciente paciente) {
            MenuPaciente.exibir(paciente);
        } else if (usuarioLogado instanceof Medico medico) {
            MenuMedico.exibir(medico);
        } else if (usuarioLogado instanceof Funcionario funcionario) {
            MenuFuncionario.exibir(funcionario);
        } else {
            System.out.println("Perfil não reconhecido.");
        }
    }
}