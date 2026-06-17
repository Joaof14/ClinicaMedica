package br.com.clinicamedica.testes;

import static br.com.clinicamedica.Medico.listarMedicos;
import static br.com.clinicamedica.Medico.listarMedicosPorArea;

import java.util.List;

import br.com.clinicamedica.Medico;

public class TesteMedico {
    public static void main(String[] args) {

        Medico novoMedico = Medico.cadastrarMedico(
                "Carlos Silva", 45, "Masculino", "11122233344", "84999990002",
                "carlos.silva", "senha123", true, 15000.0, 40, "Diurno", false,
                "UTI", "RN-1234");

        if (novoMedico != null) {
            novoMedico.verMedico();

            Medico.atualizarMedico("RN-1234", "Ambulatorio", "RN-1234");

            Medico.deletarMedico("RN-1234");
        }

        System.out.println("Testando listar médico");
        List<Medico> l1 = listarMedicos();
        for (Medico m : l1) {
            System.out.println(m);
        }

        System.out.println("Testando listar médico");
        List<Medico> l2 = listarMedicosPorArea("UTI");
        for (Medico m : l2) {
            System.out.println(m);
        }
    }
}
