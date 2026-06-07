package br.com.clinicamedica;

import java.util.ArrayList;
import java.util.List;

public class Paciente extends Usuario{
    private float peso;
    private float altura;
    private String sintomas;
    
    private static List<Paciente> pacientes = new ArrayList<>();


    public Paciente(float altura, float peso, String sintomas, String nome, int idade, String sexo, String cpf, String telefone, String login, String senha, boolean ativo) {
        super(nome, idade, sexo, cpf, telefone, login, senha, ativo);
        this.altura = altura;
        this.peso = peso;
        this.sintomas = sintomas;
    }

    
    public void verPaciente(){
        System.out.println("Nome: " + getNome());
        System.out.println("Idade: " + getIdade());
        System.out.println("Sexo: " + getSexo());
        System.out.println("CPF: " + getCpf());
        System.out.println("Peso: " + peso);
        System.out.println("Altura: " + altura);
        System.out.println("Sintomas: " + sintomas);

    }

    public static Paciente cadastrarPaciente(float altura, float peso, String sintomas, String nome, int idade, String sexo, String cpf, String telefone, String login, String senha, boolean ativo) {
        Paciente paciente = new Paciente(peso, altura, "", nome, idade, sexo, cpf, telefone, login, senha, true);
        pacientes.add(paciente);
        return paciente;
    }

    public void atualizarPaciente(Paciente paciente, float altura, float peso, String sintomas, String nome, int idade, String sexo, String cpf, String telefone, String login, String senha, boolean ativo){
        paciente.setNome(nome);
        paciente.setIdade(idade);
        paciente.setSexo(sexo);
        paciente.setCpf(cpf);
        paciente.setTelefone(telefone);
        paciente.setLogin(login);
        paciente.setSenha(senha);
        paciente.setAtivo(ativo);
        paciente.setPeso(peso);
        paciente.setAltura(altura);
        paciente.setSintomas(sintomas);
    }

    public void deletarPaciente(Paciente paciente){
        pacientes.remove(paciente);
    }

    public static List<Paciente> listarPacientes() {
        return pacientes;
    }

    public static List<Paciente> listarPacientesPorSintomas(String sintomas) {
        List<Paciente> resultado = new ArrayList<>();
        for (Paciente paciente : pacientes) {
            if (paciente.getSintomas() != null && paciente.getSintomas().equalsIgnoreCase(sintomas)) {
                resultado.add(paciente);
            }
        }
        return resultado;
    }

    public float getPeso() {
        return peso;
    }

    public float getAltura() {
        return altura;
    }

    public String getSintomas() {
        return sintomas;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }

    public void setAltura(float altura) {
        this.altura = altura;
    }

    public void setSintomas(String sintomas) {
        this.sintomas = sintomas;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Paciente{");
        sb.append("peso=").append(peso);
        sb.append(", altura=").append(altura);
        sb.append(", sintomas=").append(sintomas);
        sb.append('}');
        return sb.toString();
    }
}