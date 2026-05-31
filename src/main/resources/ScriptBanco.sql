-- Criação do Banco de Dados
CREATE DATABASE clinica_medica;


-- 1. Criação do ENUM exigido pelo diagrama de classes
CREATE TYPE status_consulta AS ENUM ('AGENDADA', 'EM ANDAMENTO', 'CONCLUIDA', 'CANCELADA');


-- 2. Tabela Mãe 
CREATE TABLE usuarios (
    id_tb_usuario SERIAL PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    idade INTEGER NOT NULL CHECK (idade >= 0),
    sexo VARCHAR(20) NOT NULL,
    cpf VARCHAR(11) UNIQUE NOT NULL,
    telefone VARCHAR(11), 
    login VARCHAR(30) UNIQUE NOT NULL,
    senha VARCHAR(50) NOT NULL,
    ativo BOOLEAN DEFAULT TRUE
);

-- 3. Tabela Paciente

CREATE TABLE pacientes (
    id_tb_paciente SERIAL PRIMARY KEY,
    id_tb_usuario INTEGER UNIQUE NOT NULL,
    peso REAL,
    altura REAL,
    sintomas TEXT,
    CONSTRAINT fk_paciente_usuario 
        FOREIGN KEY (id_tb_usuario) REFERENCES usuarios(id_tb_usuario) 
        ON UPDATE CASCADE ON DELETE CASCADE
);

-- 4. Tabela Funcionario
CREATE TABLE funcionarios (
    id_tb_funcionario SERIAL PRIMARY KEY,
    id_tb_usuario INTEGER UNIQUE NOT NULL,
    salario DECIMAL(10, 2) NOT NULL,
    carga_horaria_semanal INTEGER NOT NULL,
    turno VARCHAR(20) NOT NULL,
    atendente BOOLEAN NOT NULL,
    CONSTRAINT fk_funcionario_usuario 
        FOREIGN KEY (id_tb_usuario) REFERENCES usuarios(id_tb_usuario) 
        ON UPDATE CASCADE ON DELETE CASCADE
);

-- 5. Tabela br.com.clinicamedica.Funcionario
CREATE TABLE medicos (
    id_tb_medico SERIAL PRIMARY KEY,
    id_tb_funcionario INTEGER UNIQUE NOT NULL,
    area_de_atuacao VARCHAR(100) NOT NULL,
    crm VARCHAR(20) UNIQUE NOT NULL,
    CONSTRAINT fk_medico_funcionario 
        FOREIGN KEY (id_tb_funcionario) REFERENCES funcionarios(id_tb_funcionario) 
        ON UPDATE CASCADE ON DELETE CASCADE
);


-- 6. Tabela Consulta
CREATE TABLE consultas (
    id_tb_consulta SERIAL PRIMARY KEY,
    id_consulta VARCHAR(50) UNIQUE NOT NULL, -- ID String do diagrama UML
    id_tb_paciente INTEGER NOT NULL,
    id_tb_medico INTEGER NOT NULL,
    data_consulta DATE NOT NULL,
    horario_consulta TIME NOT NULL,
    status status_consulta NOT NULL DEFAULT 'AGENDADA',
    prescricao TEXT,
    
    CONSTRAINT fk_consulta_paciente 
        FOREIGN KEY (id_tb_paciente) REFERENCES pacientes(id_tb_paciente)
        ON UPDATE CASCADE ON DELETE CASCADE,
        
    CONSTRAINT fk_consulta_medico 
        FOREIGN KEY (id_tb_medico) REFERENCES medicos(id_tb_medico)
        ON UPDATE CASCADE ON DELETE CASCADE
);