package br.com.clinicamedica.testes;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import br.com.clinicamedica.Consulta;

public class TesteConsulta {
    public static void main(String[] args) {

        //testar gerar consulta
        System.out.println("Gerando consulta");
        Consulta.gerarConsulta( LocalDate.of(2026, 6, 10), LocalTime.of(9, 0), "00000000000",  "CRM12345");

        // listarConsultas
        System.out.println("Teste de listagem de consultas:");
        List<Consulta> lista = Consulta.listarConsultas();
        for (Consulta c : lista) {
            System.out.println(c);
        }

        // verConsulta



        // autenticar antes de gerar consulta — teste de fluxo completo
    }
}
