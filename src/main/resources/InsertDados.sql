-- 1. INSERINDO USUÁRIOS
INSERT INTO usuarios (nome, idade, sexo, cpf, telefone, login, senha, ativo) VALUES
('Ana Costa', 25, 'Feminino', '11111111111', '84999991111', 'ana.paciente', 'senha123', true),
('Bruno Silva', 34, 'Masculino', '22222222222', '84999992222', 'bruno.paciente', 'senha123', true),
('Carlos Mendes', 42, 'Masculino', '33333333333', '84999993333', 'carlos.paciente', 'senha123', true),
('Daniela Alves', 19, 'Feminino', '44444444444', '84999994444', 'daniela.paciente', 'senha123', true),
('Eduardo Rocha', 60, 'Masculino', '55555555555', '84999995555', 'eduardo.paciente', 'senha123', true),
('Fernanda Lima', 28, 'Feminino', '66666666666', '84988886666', 'fernanda.adm', 'senha123', true),
('Gustavo Nunes', 31, 'Masculino', '77777777777', '84988887777', 'gustavo.adm', 'senha123', true),
('Helena Gomes', 40, 'Feminino', '88888888888', '84988888888', 'helena.adm', 'senha123', true),
('Igor Martins', 22, 'Masculino', '99999999999', '84988889999', 'igor.adm', 'senha123', true),
('Juliana Castro', 35, 'Feminino', '10101010101', '84988881010', 'juliana.adm', 'senha123', true),
('Leonardo Farias', 45, 'Masculino', '12121212121', '84977771212', 'leonardo.med', 'senha123', true),
('Mariana Dias', 38, 'Feminino', '13131313131', '84977771313', 'mariana.med', 'senha123', true),
('Nicolas Sousa', 50, 'Masculino', '14141414141', '84977771414', 'nicolas.med', 'senha123', true),
('Olivia Ramos', 41, 'Feminino', '15151515151', '84977771515', 'olivia.med', 'senha123', true),
('Paulo Pires', 55, 'Masculino', '16161616161', '84977771616', 'paulo.med', 'senha123', true);

-- 2. INSERINDO PACIENTES
INSERT INTO pacientes (id_tb_usuario, peso, altura, sintomas) VALUES
(1, 65.5, 1.65, 'Dor de cabeça constante e enxaqueca.'),
(2, 80.0, 1.75, 'Tosse seca crônica e febre leve.'),
(3, 92.3, 1.80, 'Dores nas articulações, principalmente nos joelhos.'),
(4, 55.0, 1.60, 'Falta de ar e cansaço extremo.'),
(5, 75.8, 1.70, 'Exames de rotina cardíaca.');

-- 3. INSERINDO FUNCIONÁRIOS
INSERT INTO funcionarios (id_tb_usuario, salario, carga_horaria_semanal, turno, atendente) VALUES
(6, 2500.00, 40, 'matutino', true),
(7, 2500.00, 40, 'vespertino', true),
(8, 3000.00, 44, 'matutino', true),
(9, 2100.00, 30, 'vespertino', true),
(10, 2500.00, 40, 'matutino', true),
(11, 15000.00, 20, 'matutino', false),
(12, 18000.00, 30, 'vespertino', false),
(13, 15000.00, 20, 'vespertino', false),
(14, 22000.00, 40, 'matutino', false),
(15, 12000.00, 20, 'vespertino', false);

-- 4. INSERINDO MÉDICOS
INSERT INTO medicos (id_tb_funcionario, area_de_atuacao, crm) VALUES
(6, 'UTI', 'CRM-RN-1111'),
(7, 'ambulatorio', 'CRM-RN-2222'),
(8, 'urgencia', 'CRM-RN-3333'),
(9, 'pronto-socorro', 'CRM-RN-4444'),
(10, 'medicina-preventiva', 'CRM-RN-5555');

select * from pacientes;

-- 5. INSERINDO CONSULTAS 
INSERT INTO consultas (id_consulta, id_tb_paciente, id_tb_medico, data_consulta, horario_consulta, status, prescricao) VALUES
('CONS-001', 1, 6, '2026-06-05', '09:00:00', 'AGENDADA', NULL),
('CONS-002', 2, 7, '2026-06-05', '14:30:00', 'AGENDADA', NULL),
('CONS-003', 3, 8, '2026-05-30', '10:00:00', 'CONCLUIDA', 'Ibuprofeno 600mg de 8 em 8 horas e repouso.'),
('CONS-004', 4, 9, '2026-05-31', '08:15:00', 'EM ANDAMENTO', NULL),
('CONS-005', 5, 10, '2026-06-10', '16:00:00', 'CANCELADA', 'Paciente informou incompatibilidade de horário.');