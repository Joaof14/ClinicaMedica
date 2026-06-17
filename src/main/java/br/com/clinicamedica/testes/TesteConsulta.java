package br.com.clinicamedica.testes;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import br.com.clinicamedica.Consulta;
import br.com.clinicamedica.Usuario;

public class TesteConsulta {
    public static void main(String[] args) {

        if (Usuario.autenticar("admin", "admin")) {
            System.out.println("Gerando consulta");
            Consulta.gerarConsulta(LocalDate.of(2026, 6, 10), LocalTime.of(9, 0), "11111111111", "RN-1234");
        }

        System.out.println("Teste de listagem de consultas:");
        List<Consulta> lista = Consulta.listarConsultas();
        for (Consulta c : lista) {
            System.out.println(c);
            c.verConsulta();
        }

    }
}
