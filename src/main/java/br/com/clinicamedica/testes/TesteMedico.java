package br.com.clinicamedica.testes;

import static br.com.clinicamedica.Medico.listarMedicos;
import static br.com.clinicamedica.Medico.listarMedicosPorArea;

import java.util.List;

import br.com.clinicamedica.Medico;

public class TesteMedico {
    public static void main(String[] args) {
        // Instancia Medico

        // cadastrarMedico

        // verMedico+toString

        // atualizarMedico

        // deletarMedico

        // listarMedico
        
        System.out.println("Testando listar médico");
        List<Medico> l1 = listarMedicos();
        //System.out.println(l1);
        for (Medico m: l1){
            System.out.println(m);
        }
        

        
        System.out.println("Testando listar médico");
        List<Medico> l2 = listarMedicosPorArea("Cardiologia");
        for (Medico m: l2){
            System.out.println(m);
        }
        

        // teste set's

    }
}
