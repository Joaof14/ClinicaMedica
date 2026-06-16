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
    private Status status;
    private String prescricao;
    private String cpfPaciente;
    private String crmMedico;


<<<<<<< HEAD
    public Consulta(int id, LocalDate data, LocalTime horario, StatusConsulta status, String prescricao,
=======
    


    public Consulta(int id, LocalDate data, LocalTime horario, Status status, String prescricao,
>>>>>>> 98a43144e6aa0aa6911918fef1e41e8f519a01c2
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
                        Status.valueOf(rs.getString("status").replace(" ", "_")),
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






public static void atualizarConsulta(Consulta consulta, int id, LocalDate data, LocalTime horario, 
                                   StatusConsulta status, String prescricao) {
    if (consulta == null) {
        throw new IllegalArgumentException("Consulta não pode ser nula");
    }
    
    // Validar se a consulta existe no banco
    String sqlCheck = "SELECT status FROM consultas WHERE id_tb_consulta = ?";
    String sqlUpdate = """
            UPDATE consultas 
            SET data_consulta = ?, horario_consulta = ?, status = ?::status_consulta, prescricao = ?
            WHERE id_tb_consulta = ?
            """;
    
    try (Connection conn = ConexaoDB.obterConexao()) {
        // Verificar se a consulta existe e obter status atual
        StatusConsulta statusAtual = null;
        try (PreparedStatement stmtCheck = conn.prepareStatement(sqlCheck)) {
            stmtCheck.setInt(1, id);
            try (ResultSet rs = stmtCheck.executeQuery()) {
                if (!rs.next()) {
                    System.out.println("Consulta com ID " + id + " não encontrada.");
                    return;
                }
                statusAtual = StatusConsulta.valueOf(rs.getString("status").replace(" ", "_"));
            }
        }
        
        // Validar se a transição de status é permitida (apenas se o status for diferente)
        if (status != null && status != statusAtual) {
            if (!validarTransicaoStatus(statusAtual, status)) {
                System.out.println("Transição de status inválida: " + statusAtual + " -> " + status);
                return;
            }
        }
        
        // Atualizar a consulta no banco
        try (PreparedStatement stmtUpdate = conn.prepareStatement(sqlUpdate)) {
            stmtUpdate.setDate(1, Date.valueOf(data));
            stmtUpdate.setTime(2, Time.valueOf(horario));
            stmtUpdate.setString(3, status != null ? status.name() : statusAtual.name());
            stmtUpdate.setString(4, prescricao != null ? prescricao : consulta.getPrescricao());
            stmtUpdate.setInt(5, id);
            
            int linhasAfetadas = stmtUpdate.executeUpdate();
            if (linhasAfetadas > 0) {
                // Atualizar o objeto consulta
                consulta.setId(id);
                consulta.setData(data);
                consulta.setHorario(horario);
                if (status != null) consulta.setStatus(status);
                if (prescricao != null) consulta.setPrescricao(prescricao);
                
                System.out.println("Consulta atualizada com sucesso! ID: " + id);
            }
        }
        
    } catch (Exception e) {
        System.err.println("Erro ao atualizar consulta: " + e.getMessage());
    }
}

public static void deletarConsulta(Consulta consulta) {
    if (consulta == null) {
        System.out.println("Erro: A consulta fornecida é nula");
        return;
    }
    
    // Verificar se a consulta pode ser deletada (apenas AGENDADA ou CANCELADA)
    if (consulta.getStatus() == StatusConsulta.EM_ANDAMENTO) {
        System.out.println("Não é possível deletar uma consulta em andamento. Cancela-la primeiro.");
        return;
    }
    
    if (consulta.getStatus() == StatusConsulta.CONCLUIDA) {
        System.out.println("Não é possível deletar uma consulta já concluída.");
        return;
    }
    
    String sql = "DELETE FROM consultas WHERE id_tb_consulta = ?";
    
    try (Connection conn = ConexaoDB.obterConexao();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        
        stmt.setInt(1, consulta.getId());
        
        int linhasAfetadas = stmt.executeUpdate();
        if (linhasAfetadas > 0) {
            System.out.println("Consulta ID " + consulta.getId() + " deletada com sucesso!");
        } else {
            System.out.println("Consulta não encontrada para deletar.");
        }
        
    } catch (Exception e) {
        System.err.println("Erro ao deletar consulta: " + e.getMessage());
    }
}

public static List<Consulta> listarConsultasPorPaciente(String cpf) {
    List<Consulta> consultas = new ArrayList<>();
    
    if (cpf == null || cpf.isBlank()) {
        throw new IllegalArgumentException("CPF do paciente não pode ser nulo ou vazio");
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
            WHERE u.cpf = ?
            ORDER BY c.data_consulta DESC, c.horario_consulta DESC
            """;
    
    try (Connection conn = ConexaoDB.obterConexao();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        
        stmt.setString(1, cpf);
        
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
        System.err.println("Erro ao listar consultas por paciente: " + e.getMessage());
    }
    
    return consultas;
}

public static List<Consulta> listarConsultasPorMedico(String crm) {
    List<Consulta> consultas = new ArrayList<>();
    
    if (crm == null || crm.isBlank()) {
        throw new IllegalArgumentException("CRM do médico não pode ser nulo ou vazio");
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
            WHERE m.crm = ?
            ORDER BY c.data_consulta, c.horario_consulta
            """;
    
    try (Connection conn = ConexaoDB.obterConexao();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        
        stmt.setString(1, crm);
        
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
        System.err.println("Erro ao listar consultas por médico: " + e.getMessage());
    }
    
    return consultas;
}

// Método auxiliar - adicione o campo horario que estava faltando
public LocalTime getHorario() {
    return horario;
}

public void setHorario(LocalTime horario) {
    this.horario = horario;
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
                        Status.valueOf(rs.getString("status").replace(" ", "_")),
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

public static List<Consulta> listarConsultasPorStatus(Status status) {
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
                        Status.valueOf(rs.getString("status").replace(" ", "_")),
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


<<<<<<< HEAD
private static boolean validarTransicaoStatus(StatusConsulta atual, StatusConsulta novo) {
    // Regras de transição de status
    switch (atual) {
        case AGENDADA:
            return novo == StatusConsulta.EM_ANDAMENTO || novo == StatusConsulta.CANCELADA;
        case EM_ANDAMENTO:
            return novo == StatusConsulta.CONCLUIDA || novo == StatusConsulta.CANCELADA;
        case CONCLUIDA:
            return false; // Consulta concluída não pode mais ser alterada
        case CANCELADA:
            return false; // Consulta cancelada não pode mais ser alterada
        default:
            return false;
    }
=======

public static boolean iniciarConsulta(int idConsulta) {
    return atualizarStatus(idConsulta, Status.EM_ANDAMENTO, null);
>>>>>>> 98a43144e6aa0aa6911918fef1e41e8f519a01c2
}


public void iniciarConsulta() {
    // Verificar se a consulta está no status AGENDADA
    if (this.status != StatusConsulta.AGENDADA) {
        System.out.println("Não é possível iniciar uma consulta que não está AGENDADA. Status atual: " + this.status);
        return;
    }
    
    // Verificar se a data/hora da consulta já passou
    LocalDateTime dataHoraConsulta = LocalDateTime.of(this.data, this.horario);
    if (dataHoraConsulta.isBefore(LocalDateTime.now())) {
        System.out.println("A data/hora da consulta já passou. Não é possível iniciar.");
        return;
    }
    
    // Atualizar status no banco
    boolean atualizado = atualizarStatusConsulta(this.id, StatusConsulta.EM_ANDAMENTO, null);
    if (atualizado) {
        this.status = StatusConsulta.EM_ANDAMENTO;
        System.out.println("Consulta " + this.id + " iniciada com sucesso!");
    }
}

public void concluirConsulta(String prescricao) {
    if (prescricao == null || prescricao.isBlank()) {
        throw new IllegalArgumentException("Prescrição não pode ser vazia ao concluir consulta");
    }
<<<<<<< HEAD
    
    // Verificar se a consulta está em andamento
    if (this.status != StatusConsulta.EM_ANDAMENTO) {
        System.out.println("Não é possível concluir uma consulta que não está EM_ANDAMENTO. Status atual: " + this.status);
        return;
    }
    
    // Atualizar status e prescrição no banco
    boolean atualizado = atualizarStatusConsulta(this.id, StatusConsulta.CONCLUIDA, prescricao);
    if (atualizado) {
        this.status = StatusConsulta.CONCLUIDA;
        this.prescricao = prescricao;
        System.out.println("Consulta " + this.id + " concluída com sucesso!");
    }
}

public void cancelarConsulta() {
    // Verificar se a consulta pode ser cancelada (AGENDADA ou EM_ANDAMENTO)
    if (this.status == StatusConsulta.CONCLUIDA) {
        System.out.println("Não é possível cancelar uma consulta já concluída.");
        return;
    }
    
    if (this.status == StatusConsulta.CANCELADA) {
        System.out.println("Esta consulta já está cancelada.");
        return;
    }
    
    // Atualizar status no banco
    boolean atualizado = atualizarStatusConsulta(this.id, StatusConsulta.CANCELADA, null);
    if (atualizado) {
        this.status = StatusConsulta.CANCELADA;
        System.out.println("Consulta " + this.id + " cancelada com sucesso!");
    }
=======
    return atualizarStatus(idConsulta, Status.CONCLUIDA, prescricao);
}

public static boolean cancelarConsulta(int idConsulta) {
    return atualizarStatus(idConsulta, Status.CANCELADA, null);
>>>>>>> 98a43144e6aa0aa6911918fef1e41e8f519a01c2
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



    public Status getStatus() {
        return status;
    }



    public void setStatus(Status status) {
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
