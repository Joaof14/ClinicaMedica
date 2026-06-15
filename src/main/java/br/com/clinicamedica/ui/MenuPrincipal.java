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

        String perfil = menuLogin();

        if (perfil != null) {
            menuSelecaoPerfil(perfil);
        }

        Utils.fecharScanner();
        System.out.println("\nSistema encerrado. Até logo!");
    }

    private static String menuLogin() {
        int tentativas = 3;

        while (tentativas > 0) {
            System.out.println("|-------------------- LOGIN -----------------------|");

            String login = Utils.lerTexto("Login: ");
            String senha = Utils.lerTexto("Senha: ");

            if (Usuario.autenticar(login, senha)) {
                loginAutenticado = login;
                System.out.println("\nLogin realizado com sucesso!");

                String perfil = identificarPerfil(loginAutenticado);

                if (perfil != null) {
                    return perfil;
                } else {
                    System.out.println("Usuário autenticado, mas o perfil não pôde ser identificado.");
                    return null;
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
        return null;
    }

    private static String identificarPerfil(String login) {
        usuarioLogado = buscarPacientePorLogin(login);
        if (usuarioLogado != null) {
            return "PACIENTE";
        }

        usuarioLogado = buscarMedicoPorLogin(login);
        if (usuarioLogado != null) {
            return "MEDICO";
        }

        usuarioLogado = buscarFuncionarioPorLogin(login);
        if (usuarioLogado != null) {
            return "FUNCIONARIO";
        }

        return null;
    }

    private static Paciente buscarPacientePorLogin(String login) {
        List<Paciente> pacientes = Paciente.listarPacientes();

        for (Paciente paciente : pacientes) {
            if (paciente.getLogin().equals(login)) {
                return paciente;
            }
        }

        return null;
    }

    private static Medico buscarMedicoPorLogin(String login) {
        List<Medico> medicos = Medico.listarMedicos();

        for (Medico medico : medicos) {
            if (medico.getLogin().equals(login)) {
                return medico;
            }
        }

        return null;
    }

    private static Funcionario buscarFuncionarioPorLogin(String login) {
        List<Funcionario> funcionarios = Funcionario.listarFuncionario();

        for (Funcionario funcionario : funcionarios) {
            if (funcionario.getLogin().equals(login)) {
                return funcionario;
            }
        }

        return null;
    }

    private static void menuSelecaoPerfil(String perfil) {
    switch (perfil) {
        case "PACIENTE" -> {
            if (usuarioLogado instanceof Paciente paciente) {
                MenuPaciente.exibir(paciente);
            }
        }
        case "FUNCIONARIO" -> {
            if (usuarioLogado instanceof Funcionario funcionario){
                MenuFuncionario.exibir(funcionario);
            }
           
        }
        case "MEDICO" -> {
            if (usuarioLogado instanceof Medico medico) {
                MenuMedico.exibir(medico);
            }
        }
        default -> System.out.println("Perfil não reconhecido.");
    }
}
}