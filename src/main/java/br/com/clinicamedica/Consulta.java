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
    private Status status;
    private String prescricao;
    private String cpfPaciente;
    private String crmMedico;

    public Consulta(int id, LocalDate data, LocalTime horario, Status status, String prescricao,  String cpfPaciente, String crmMedico) {
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

    public static Consulta gerarConsulta(LocalDate data, LocalTime horario, String cpfPaciente, String crmMedico) {
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
                return null;
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
                return null;
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
                    String sqlBuscar = "SELECT id_tb_consulta FROM consultas WHERE id_consulta = ?";
                    try (PreparedStatement stmtBuscar = conn.prepareStatement(sqlBuscar)) {
                        stmtBuscar.setString(1, idConsulta);
                        try (ResultSet rs = stmtBuscar.executeQuery()) {
                            if (rs.next()) {
                                int id = rs.getInt("id_tb_consulta");
                                Consulta consulta = new Consulta(
                                    id,
                                    data,
                                    horario,
                                    Status.AGENDADA,
                                    null,
                                    cpfPaciente,
                                    crmMedico
                                );
                                return consulta;
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            System.err.println("Erro ao gerar consultas: " + e.getMessage());
        }
        return null;
    }

    public void atualizarConsulta(int id, LocalDate data, LocalTime horario, Status status, String prescricao) {
        String sqlCheck = "SELECT status FROM consultas WHERE id_tb_consulta = ?";
        String sqlUpdate = """
                UPDATE consultas 
                SET data_consulta = ?, horario_consulta = ?, status = ?::status_consulta, prescricao = ?
                WHERE id_tb_consulta = ?
                """;
        
        try (Connection conn = ConexaoDB.obterConexao()) {
            Status statusAtual = null;
            try (PreparedStatement stmtCheck = conn.prepareStatement(sqlCheck)) {
                stmtCheck.setInt(1, id);
                try (ResultSet rs = stmtCheck.executeQuery()) {
                    if (!rs.next()) {
                        System.out.println("Consulta com ID " + id + " não encontrada.");
                        return;
                    }
                    statusAtual = Status.valueOf(rs.getString("status").replace(" ", "_"));
                }
            }

            if (status != null && status != statusAtual) {
                if (!validarTransicaoStatus(statusAtual, status)) {
                    System.out.println("Transição de status inválida: " + statusAtual + " -> " + status);
                    return;
                }
            }

            try (PreparedStatement stmtUpdate = conn.prepareStatement(sqlUpdate)) {
                stmtUpdate.setDate(1, Date.valueOf(data));
                stmtUpdate.setTime(2, Time.valueOf(horario));
                stmtUpdate.setString(3, status != null ? status.name() : statusAtual.name());
                stmtUpdate.setString(4, prescricao != null ? prescricao : this.prescricao);
                stmtUpdate.setInt(5, id);
                
                int linhasAfetadas = stmtUpdate.executeUpdate();
                if (linhasAfetadas > 0) {
                    this.id = id;
                    this.data = data;
                    this.horario = horario;
                    if (status != null) this.status = status;
                    if (prescricao != null) this.prescricao = prescricao;
                    
                    System.out.println("Consulta atualizada com sucesso! ID: " + id);
                }
            }
            
        } catch (Exception e) {
            System.err.println("Erro ao atualizar consulta: " + e.getMessage());
        }
    }

    public void iniciarConsulta() {
        if (this.status != Status.AGENDADA) {
            System.out.println("Não é possível iniciar uma consulta que não está AGENDADA. Status atual: " + this.status);
            return;
        }

        LocalDateTime dataHoraConsulta = LocalDateTime.of(this.data, this.horario);
        if (dataHoraConsulta.isAfter(LocalDateTime.now())) {
            System.out.println("Ainda não é hora de iniciar esta consulta. Horário agendado: " + dataHoraConsulta);
            return;
        }

        boolean atualizado = atualizarStatus(this.id, Status.EM_ANDAMENTO, null);
        if (atualizado) {
            this.status = Status.EM_ANDAMENTO;
            System.out.println("Consulta " + this.id + " iniciada com sucesso!");
        }
    }

    public void concluirConsulta(String prescricao) {
        if (prescricao == null || prescricao.isBlank()) {
            throw new IllegalArgumentException("Prescrição não pode ser vazia ao concluir consulta");
        }

        if (this.status != Status.EM_ANDAMENTO) {
            System.out.println("Não é possível concluir uma consulta que não está EM_ANDAMENTO. Status atual: " + this.status);
            return;
        }

        boolean atualizado = atualizarStatus(this.id, Status.CONCLUIDA, prescricao);
        if (atualizado) {
            this.status = Status.CONCLUIDA;
            this.prescricao = prescricao;
            System.out.println("Consulta " + this.id + " concluída com sucesso!");
        }
    }

    public void cancelarConsulta() {
        if (this.status == Status.CONCLUIDA) {
            System.out.println("Não é possível cancelar uma consulta já concluída.");
            return;
        }
        
        if (this.status == Status.CANCELADA) {
            System.out.println("Esta consulta já está cancelada.");
            return;
        }

        boolean atualizado = atualizarStatus(this.id, Status.CANCELADA, null);
        if (atualizado) {
            this.status = Status.CANCELADA;
            System.out.println("Consulta " + this.id + " cancelada com sucesso!");
        }
    }

    public static void deletarConsulta(Consulta consulta) {
        if (consulta == null) {
            System.out.println("Erro: A consulta fornecida é nula");
            return;
        }

        if (consulta.getStatus() == Status.EM_ANDAMENTO) {
            System.out.println("Não é possível deletar uma consulta em andamento. Cancela-la primeiro.");
            return;
        }
        
        if (consulta.getStatus() == Status.CONCLUIDA) {
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

    public static List<Consulta> listarConsultas() {
        List<Consulta> consultas = new ArrayList<>();

        String sql = """
                SELECT c.id_tb_consulta, c.data_consulta, c.horario_consulta,
                       c.status, c.prescricao,
                       u.cpf AS cpf_paciente,
                       m.crm AS crm_medico
                FROM consultas c
                JOIN pacientes p ON c.id_tb_paciente = p.id_tb_paciente
                JOIN usuarios u ON p.id_tb_usuario = u.id_tb_usuario
                JOIN medicos m ON c.id_tb_medico = m.id_tb_medico
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
                            Status.valueOf(rs.getString("status").replace(" ", "_")),
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
                            Status.valueOf(rs.getString("status").replace(" ", "_")),
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

    private static boolean validarTransicaoStatus(Status atual, Status novo) {
        switch (atual) {
            case AGENDADA:
                return novo == Status.EM_ANDAMENTO || novo == Status.CANCELADA;
            case EM_ANDAMENTO:
                return novo == Status.CONCLUIDA || novo == Status.CANCELADA;
            case CONCLUIDA:
                return false;
            case CANCELADA:
                return false;
            default:
                return false;
        }
    }

    private static boolean atualizarStatus(int idConsulta, Status novoStatus, String prescricao) {
        String sqlSelect = "SELECT status FROM consultas WHERE id_tb_consulta = ?";
        String sqlUpdate = "UPDATE consultas SET status = ?::status_consulta" + 
                          (prescricao != null ? ", prescricao = ?" : "") + 
                          " WHERE id_tb_consulta = ?";
        
        try (Connection conn = ConexaoDB.obterConexao()) {
            Status statusAtual;
            try (PreparedStatement stmtSelect = conn.prepareStatement(sqlSelect)) {
                stmtSelect.setInt(1, idConsulta);
                try (ResultSet rs = stmtSelect.executeQuery()) {
                    if (!rs.next()) {
                        System.out.println("Consulta não encontrada com ID: " + idConsulta);
                        return false;
                    }
                    statusAtual = Status.valueOf(rs.getString("status").replace(" ", "_"));
                }
            }
            
            if (!validarTransicaoStatus(statusAtual, novoStatus)) {
                System.out.println("Transição de status inválida: " + statusAtual + " -> " + novoStatus);
                return false;
            }
            
            try (PreparedStatement stmtUpdate = conn.prepareStatement(sqlUpdate)) {
                int paramIndex = 1;
                stmtUpdate.setString(paramIndex++, novoStatus.name());
                
                if (prescricao != null) {
                    stmtUpdate.setString(paramIndex++, prescricao);
                    stmtUpdate.setInt(paramIndex, idConsulta);
                } else {
                    stmtUpdate.setInt(paramIndex, idConsulta);
                }
                
                int linhasAfetadas = stmtUpdate.executeUpdate();
                return linhasAfetadas > 0;
            }
            
        } catch (Exception e) {
            System.err.println("Erro ao atualizar status da consulta: " + e.getMessage());
            return false;
        }
    }





    public int getId() {
        return id;
    }

    public LocalDate getData() {
        return data;
    }

    public LocalTime getHorario() {
        return horario;
    }

    public Status getStatus() {
        return status;
    }

    public String getPrescricao() {
        return prescricao;
    }

    public String getCpfPaciente() {
        return cpfPaciente;
    }

    public String getCrmMedico() {
        return crmMedico;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public void setHorario(LocalTime horario) {
        this.horario = horario;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setPrescricao(String prescricao) {
        this.prescricao = prescricao;
    }

    public void setCpfPaciente(String cpfPaciente) {
        this.cpfPaciente = cpfPaciente;
    }

    public void setCrmMedico(String crmMedico) {
        this.crmMedico = crmMedico;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Consulta{");
        sb.append("id=").append(id);
        sb.append(", data=").append(data);
        sb.append(", horario=").append(horario);
        sb.append(", status=").append(status);
        sb.append(", prescricao=").append(prescricao);
        sb.append(", cpfPaciente=").append(cpfPaciente);
        sb.append(", crmMedico=").append(crmMedico);
        sb.append('}');
        return sb.toString();
    }

    
}