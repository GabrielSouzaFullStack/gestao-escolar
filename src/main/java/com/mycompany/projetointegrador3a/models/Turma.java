package com.mycompany.projetointegrador3a.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Turma {
    // Atributos privados para encapsulamento
    private final String codigoTurma;
    private final String etapaEnsino;
    private final int ano;
    private final int limiteVagas;
    private final List<Aluno> alunos;

    // Construtor com validações
    public Turma(String codigoTurma, String etapaEnsino, int ano, int limiteVagas) {
        if (codigoTurma == null || codigoTurma.isEmpty()) {
            throw new IllegalArgumentException("O código da turma não pode ser vazio.");
        }
        if (etapaEnsino == null || etapaEnsino.isEmpty()) {
            throw new IllegalArgumentException("A etapa de ensino não pode ser vazia.");
        }
        if (limiteVagas <= 0) {
            throw new IllegalArgumentException("O limite de vagas deve ser maior que zero.");
        }

        this.codigoTurma = codigoTurma;
        this.etapaEnsino = etapaEnsino;
        this.ano = ano;
        this.limiteVagas = limiteVagas;
        this.alunos = new ArrayList<>();
    }

    // Método para matricular um aluno
    public boolean matricularAluno(Aluno aluno) {
        if (aluno == null) {
            throw new IllegalArgumentException("O aluno não pode ser nulo.");
        }
        if (alunos.size() < limiteVagas) {
            alunos.add(aluno);
            return true;
        }
        System.out.println("A turma está lotada :(");
        return false;
    }

    // Lista os alunos matriculados na turma
    public void listarAlunosMatriculados() {
        alunos.forEach(System.out::println);
    }

    // Retorna o número de alunos fora da idade prevista para a etapa de ensino
    public int alunosForaIdadePrevista() {
        return (int) alunos.stream()
                .filter(aluno -> !idadeDentroDaEtapa(aluno.getIdade()))
                .count();
    }

    // Retorna uma lista de alunos fora da idade prevista para a etapa de ensino
    public List<Aluno> listarAlunosForaIdadePrevista() {
        return alunos.stream()
                .filter(aluno -> !idadeDentroDaEtapa(aluno.getIdade()))
                .toList();
    }

    // Método auxiliar para verificar se a idade está dentro da etapa de ensino
    private boolean idadeDentroDaEtapa(int idade) {
        switch (etapaEnsino.toLowerCase()) {
            case "infantil":
                return idade < 6;
            case "fundamental anos iniciais":
                return idade >= 6 && idade <= 11;
            case "fundamental anos finais":
                return idade >= 11 && idade <= 15;
            case "medio":
                return idade >= 15 && idade <= 18;
            default:
                throw new IllegalArgumentException("Etapa de ensino inválida: " + etapaEnsino);
        }
    }

    // Sobrescrita do método toString
    @Override
    public String toString() {
        return "Turma: " + codigoTurma +
                " | Etapa Ensino: " + etapaEnsino +
                " | Ano: " + ano +
                " | Vagas: " + alunos.size() + "/" + limiteVagas;
    }

    // Sobrescrita dos métodos equals e hashCode para comparação de objetos
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Turma turma = (Turma) o;
        return codigoTurma.equals(turma.codigoTurma);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigoTurma);
    }

    // Getters para acessar os atributos, se necessário
    public String getCodigoTurma() {
        return codigoTurma;
    }

    public String getEtapaEnsino() {
        return etapaEnsino;
    }

    public int getAno() {
        return ano;
    }

    public int getLimiteVagas() {
        return limiteVagas;
    }

    public List<Aluno> getAlunos() {
        return new ArrayList<>(alunos); // Retorna uma cópia para proteger a lista original
    }
}
