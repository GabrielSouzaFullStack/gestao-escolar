package com.mycompany.projetointegrador3a.models;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

public class Aluno {
    // Atributos privados e finais para imutabilidade
    private final String nome;
    private final String cpf;
    private final String endereco;
    private final LocalDate dataNascimento;

    // Construtor com validações
    public Aluno(String nome, String cpf, String endereco, String dataNascimento) {
        if (nome == null || nome.isEmpty()) {
            throw new IllegalArgumentException("O nome não pode ser vazio.");
        }
        if (cpf == null || cpf.isEmpty() || !cpf.matches("\\d{11}")) {
            throw new IllegalArgumentException("CPF inválido. Deve conter 11 dígitos.");
        }
        if (endereco == null || endereco.isEmpty()) {
            throw new IllegalArgumentException("O endereço não pode ser vazio.");
        }
        if (dataNascimento == null || dataNascimento.isEmpty()) {
            throw new IllegalArgumentException("A data de nascimento não pode ser vazia.");
        }

        // Validação e formatação da data
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            this.dataNascimento = LocalDate.parse(dataNascimento, formatter);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Data de nascimento inválida. Use o formato dd/MM/yyyy.");
        }

        this.nome = nome;
        this.cpf = cpf;
        this.endereco = endereco;
    }

    // Getters para acessar os atributos
    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public String getEndereco() {
        return endereco;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    // Método para calcular a idade
    public int getIdade() {
        return Period.between(this.dataNascimento, LocalDate.now()).getYears();
    }

    // Sobrescrita do método toString
    @Override
    public String toString() {
        return "Aluno: " + nome + " | Endereço: " + endereco + " | CPF: " + cpf + " | Idade: " + getIdade() + " anos";
    }

    // Sobrescrita dos métodos equals e hashCode para comparação de objetos
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Aluno aluno = (Aluno) o;
        return cpf.equals(aluno.cpf); // Comparação baseada no CPF
    }

    @Override
    public int hashCode() {
        return Objects.hash(cpf);
    }
}
