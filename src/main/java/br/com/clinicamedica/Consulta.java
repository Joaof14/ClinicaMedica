package br.com.clinicamedica;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Consulta {
    private int id;
    private LocalDate data;
    private LocalTime horario;
    private StatusConsulta status;
    private String prescricao;
    private String cpfPaciente;
    private String crmMedico;


    public Consulta(int id, LocalDate data, StatusConsulta status, String prescricao, String cpfPaciente,
            String crmMedico) {
        this.id = id;
        this.data = data;
        this.status = status;
        this.prescricao = prescricao;
        this.cpfPaciente = cpfPaciente;
        this.crmMedico = crmMedico;
    }


    public void verConsulta(){
        System.out.println("Imprimindo Consulta:\n");
        System.out.println("==========================");
        System.out.println(this.toString());
        System.out.println("=========================");
    }

    public static void gerarConsulta(LocalDate data, LocalTime horario,String cpfPaciente, String crmMedico){
        //To do.
    }


    public static List<Consulta> listarConsultas(){
        //To do.
        List<Consulta> consultas = new ArrayList<>();
        return consultas;
    }

    public int getId() {
        return id;
    }



    public void setId(int id) {
        this.id = id;
    }



    public LocalDate getData() {
        return data;
    }



    public void setData(LocalDate data) {
        this.data = data;
    }



    public StatusConsulta getStatus() {
        return status;
    }



    public void setStatus(StatusConsulta status) {
        this.status = status;
    }



    public String getPrescricao() {
        return prescricao;
    }



    public void setPrescricao(String prescricao) {
        this.prescricao = prescricao;
    }



    public String getCpfPaciente() {
        return cpfPaciente;
    }



    public void setCpfPaciente(String cpfPaciente) {
        this.cpfPaciente = cpfPaciente;
    }



    public String getCrmMedico() {
        return crmMedico;
    }



    public void setCrmMedico(String crmMedico) {
        this.crmMedico = crmMedico;
    }

    @Override
    public String toString() {
        return String.format(
                "ID: %d%n" +
                "Data: %s%n" +
                "Horário: %s%n" +
                "Status: %s%n" +
                "Prescrição: %s%n" +
                "CPF Paciente: %s%n" +
                "CRM Médico: %s%n",
                id, data, horario, status,
                (prescricao != null ? prescricao : "—"),
                cpfPaciente, crmMedico);
    }


    

    
}
