package br.com.clinicamedica;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

    public void verConsulta() {
        System.out.println("Imprimindo Consulta:\n");
        System.out.println("==========================");
        System.out.println(this.toString());
        System.out.println("=========================");
    }

    public static void gerarConsulta(LocalDate data, LocalTime horario, String cpfPaciente, String crmMedico) {
        if (data == null) throw new IllegalArgumentException("Data não pode ser nula");
        if (horario == null) throw new IllegalArgumentException("Horário não pode ser nulo");
        if (cpfPaciente == null || cpfPaciente.isBlank()) throw new IllegalArgumentException("CPF do paciente não pode ser nulo nem branco");
        if (crmMedico == null || crmMedico.isBlank()) throw new IllegalArgumentException("CRM do médico não pode ser nulo nem branco");

        String sqlPaciente = """
            SELECT p.id_tb_paciente
            FROM pacientes p
            JOIN usuarios u ON p.id_tb_usuario = u.id_tb_usuario
            WHERE u.cpf = ?
            """;

        String sqlMedico = """
            SELECT m.id_tb_medico
            FROM medicos m
            WHERE m.crm = ?
            """;

        String sqlInsert = """
            INSERT INTO consultas (id_consulta, id_tb_paciente, id_tb_medico, data_consulta, horario_consulta, status)
            VALUES (?, ?, ?, ?, ?, ?::status_consulta)
            """;

        try (Connection conn = ConexaoDB.obterConexao()) {
            Integer idPaciente = null;
            Integer idMedico = null;

            try (PreparedStatement stmtPaciente = conn.prepareStatement(sqlPaciente)) {
                stmtPaciente.setString(1, cpfPaciente);
                try (ResultSet rs = stmtPaciente.executeQuery()) {
                    if (rs.next()) {
                        idPaciente = rs.getInt("id_tb_paciente");
                    }
                }
            }

            if (idPaciente == null) {
                System.out.println("Paciente não encontrado para o CPF: " + cpfPaciente);
                return;
            }

            try (PreparedStatement stmtMedico = conn.prepareStatement(sqlMedico)) {
                stmtMedico.setString(1, crmMedico);
                try (ResultSet rs = stmtMedico.executeQuery()) {
                    if (rs.next()) {
                        idMedico = rs.getInt("id_tb_medico");
                    }
                }
            }

            if (idMedico == null) {
                System.out.println("Médico não encontrado para o CRM: " + crmMedico);
                return;
            }

            String idConsulta = "CONS-" + System.currentTimeMillis();

            try (PreparedStatement stmtInsert = conn.prepareStatement(sqlInsert)) {
                stmtInsert.setString(1, idConsulta);
                stmtInsert.setInt(2, idPaciente);
                stmtInsert.setInt(3, idMedico);
                stmtInsert.setDate(4, Date.valueOf(data));
                stmtInsert.setTime(5, Time.valueOf(horario));
                stmtInsert.setString(6, "AGENDADA");

                int linhasAfetadas = stmtInsert.executeUpdate();
                if (linhasAfetadas > 0) {
                    System.out.println("Consulta gerada com sucesso! ID: " + idConsulta);
                }
            }

        } catch (Exception e) {
            System.err.println("Erro ao gerar consultas: " + e.getMessage());
        }
    }

    public static List<Consulta> listarConsultas() {
        List<Consulta> consultas = new ArrayList<>();

        // Faz JOIN para recuperar CPF e CRM, que são os dados usados no objeto Java
        String sql = """
                SELECT c.id_tb_consulta, c.data_consulta, c.horario_consulta,
                       c.status, c.prescricao,
                       u.cpf AS cpf_paciente,
                       m.crm AS crm_medico
                FROM consultas c
                JOIN pacientes p  ON c.id_tb_paciente = p.id_tb_paciente
                JOIN usuarios u   ON p.id_tb_usuario  = u.id_tb_usuario
                JOIN medicos m    ON c.id_tb_medico    = m.id_tb_medico
                """;

        try (Connection conn = ConexaoDB.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Consulta consulta = new Consulta(
                        rs.getInt("id_tb_consulta"),
                        rs.getDate("data_consulta").toLocalDate(),
                        rs.getTime("horario_consulta").toLocalTime(),
                        StatusConsulta.valueOf(rs.getString("status").replace(" ", "_")),
                        rs.getString("prescricao"),
                        rs.getString("cpf_paciente"),
                        rs.getString("crm_medico")
                );
                consultas.add(consulta);
            }

        } catch (Exception e) {
            System.err.println("Erro ao listar consultas: " + e.getMessage());
        }

        return consultas;
    }









public static List<Consulta> listarConsultasPorPeriodo(LocalDate dataInicio, LocalDate dataFim) {
    List<Consulta> consultas = new ArrayList<>();
    
    if (dataInicio == null || dataFim == null) {
        throw new IllegalArgumentException("Datas não podem ser nulas");
    }
    
    if (dataInicio.isAfter(dataFim)) {
        throw new IllegalArgumentException("Data inicial não pode ser posterior à data final");
    }
    
    String sql = """
            SELECT c.id_tb_consulta, c.data_consulta, c.horario_consulta,
                   c.status, c.prescricao,
                   u.cpf AS cpf_paciente,
                   m.crm AS crm_medico
            FROM consultas c
            JOIN pacientes p ON c.id_tb_paciente = p.id_tb_paciente
            JOIN usuarios u ON p.id_tb_usuario = u.id_tb_usuario
            JOIN medicos m ON c.id_tb_medico = m.id_tb_medico
            WHERE c.data_consulta BETWEEN ? AND ?
            ORDER BY c.data_consulta, c.horario_consulta
            """;
    
    try (Connection conn = ConexaoDB.obterConexao();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        
        stmt.setDate(1, Date.valueOf(dataInicio));
        stmt.setDate(2, Date.valueOf(dataFim));
        
        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Consulta consulta = new Consulta(
                        rs.getInt("id_tb_consulta"),
                        rs.getDate("data_consulta").toLocalDate(),
                        rs.getTime("horario_consulta").toLocalTime(),
                        StatusConsulta.valueOf(rs.getString("status").replace(" ", "_")),
                        rs.getString("prescricao"),
                        rs.getString("cpf_paciente"),
                        rs.getString("crm_medico")
                );
                consultas.add(consulta);
            }
        }
        
    } catch (Exception e) {
        System.err.println("Erro ao listar consultas por período: " + e.getMessage());
    }
    
    return consultas;
}

public static List<Consulta> listarConsultasPorStatus(StatusConsulta status) {
    List<Consulta> consultas = new ArrayList<>();
    
    if (status == null) {
        throw new IllegalArgumentException("Status não pode ser nulo");
    }
    
    String sql = """
            SELECT c.id_tb_consulta, c.data_consulta, c.horario_consulta,
                   c.status, c.prescricao,
                   u.cpf AS cpf_paciente,
                   m.crm AS crm_medico
            FROM consultas c
            JOIN pacientes p ON c.id_tb_paciente = p.id_tb_paciente
            JOIN usuarios u ON p.id_tb_usuario = u.id_tb_usuario
            JOIN medicos m ON c.id_tb_medico = m.id_tb_medico
            WHERE c.status = ?::status_consulta
            ORDER BY c.data_consulta, c.horario_consulta
            """;
    
    try (Connection conn = ConexaoDB.obterConexao();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        
        stmt.setString(1, status.name());
        
        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Consulta consulta = new Consulta(
                        rs.getInt("id_tb_consulta"),
                        rs.getDate("data_consulta").toLocalDate(),
                        rs.getTime("horario_consulta").toLocalTime(),
                        StatusConsulta.valueOf(rs.getString("status").replace(" ", "_")),
                        rs.getString("prescricao"),
                        rs.getString("cpf_paciente"),
                        rs.getString("crm_medico")
                );
                consultas.add(consulta);
            }
        }
        
    } catch (Exception e) {
        System.err.println("Erro ao listar consultas por status: " + e.getMessage());
    }
    
    return consultas;
}

public static boolean iniciarConsulta(int idConsulta) {
    return atualizarStatusConsulta(idConsulta, StatusConsulta.EM_ANDAMENTO, null);
}

public static boolean concluirConsulta(int idConsulta, String prescricao) {
    if (prescricao == null || prescricao.isBlank()) {
        throw new IllegalArgumentException("Prescrição não pode ser vazia ao concluir consulta");
    }
    return atualizarStatusConsulta(idConsulta, StatusConsulta.CONCLUIDA, prescricao);
}

public static boolean cancelarConsulta(int idConsulta) {
    return atualizarStatusConsulta(idConsulta, StatusConsulta.CANCELADA, null);
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
