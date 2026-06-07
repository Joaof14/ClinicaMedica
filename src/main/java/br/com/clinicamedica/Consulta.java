package br.com.clinicamedica;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;
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


    


    public Consulta(int id, LocalDate data, LocalTime horario, StatusConsulta status, String prescricao,
            String cpfPaciente, String crmMedico) {
        this.id = id;
        this.data = data;
        this.horario = horario;
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
        //verifica argumentos validos.
        if (data == null){ throw new IllegalArgumentException("Data não pode ser nula");}
        if (horario == null){ throw new IllegalArgumentException("Horário não pode ser nulo");}
        if (cpfPaciente == null || cpfPaciente.isBlank()){ throw new IllegalArgumentException("cpf do paciente não pode ser nulo nem branco");}
        if (crmMedico == null || crmMedico.isBlank()){ throw new IllegalArgumentException("crm do medico nao pode ser nulo nem branco");}


        String sql = """
                INSERT INTO consultas (data, horario, status, prescricao, cpf_paciente, crm_medico)
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        try (Connection conn = ConexaoDB.obterConexao(); PreparedStatement stmt = conn.prepareStatement(sql);){

            stmt.setDate(1, Date.valueOf(data));
            stmt.setTime(2, Time.valueOf(horario));
            stmt.setString(3, StatusConsulta.AGENDADA.name());
            stmt.setString(4, null);
            stmt.setString(5, crmMedico);
            stmt.setString(6, cpfPaciente);

            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas > 0)
                System.out.println("Consulta gerada com sucesso!");
        
        } catch (Exception e){
            System.err.println("Erro ao gerar consultas: " + e.getMessage());
        }
    }


    public static List<Consulta> listarConsultas(){
        //To do.
        List<Consulta> consultas = new ArrayList<>();

        String sql = "SELECT * FROM consultas";

        try (Connection conn = ConexaoDB.obterConexao(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()){
            
            while (rs.next()){
                Consulta consulta = new Consulta(rs.getInt("id"),rs.getDate("data").toLocalDate(),rs.getTime("horario").toLocalTime(), StatusConsulta.valueOf(rs.getString("status")), rs.getString("prescricao"), rs.getString("cpf_paciente"),rs.getString("crm_medico") );
                consultas.add(consulta);
            }

        } catch (Exception e){
            System.err.println("Erro ao listar consultas: " + e.getMessage());
        }

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
