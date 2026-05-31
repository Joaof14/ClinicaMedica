package br.com.clinicamedica;

import java.util.ArrayList;
import java.util.List;

public class Paciente extends Usuario{
    private float peso;
    private float altura;
    private String simtomas;
    
    private static List<Paciente> pacientes = new ArrayList<>();

    public Paciente(float altura, float peso, String simtomas, String nome, int idade, String sexo, String cpf, String telefone, String login, String senha, boolean ativo) {
        super(nome, idade, sexo, cpf, telefone, login, senha, ativo);
        this.altura = altura;
        this.peso = peso;
        this.simtomas = simtomas;
    }

    
    public void verPaciente(){

    }

    public void atualizarPaciente(){

    }

    public void deletarPaciente(){

    }


    public float getPeso() {
        return peso;
    }

    public float getAltura() {
        return altura;
    }

    public String getSimtomas() {
        return simtomas;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }

    public void setAltura(float altura) {
        this.altura = altura;
    }

    public void setSimtomas(String simtomas) {
        this.simtomas = simtomas;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Paciente{");
        sb.append("peso=").append(peso);
        sb.append(", altura=").append(altura);
        sb.append(", simtomas=").append(simtomas);
        sb.append('}');
        return sb.toString();
    }

   
}