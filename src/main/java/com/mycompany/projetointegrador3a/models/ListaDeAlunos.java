package com.mycompany.projetointegrador3a.models;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import com.mycompany.projetointegrador3a.exceptions.ExcecaoDeAlunoJaExistente;

public class ListaDeAlunos {
    // Lista privada para encapsulamento
    private final List<Aluno> alunos = new ArrayList<>();

    // Adiciona um aluno no início da lista
    public void adicionarNoInicio(Aluno aluno) {
        if (aluno == null) {
            throw new IllegalArgumentException("O aluno não pode ser nulo.");
        }
        alunos.add(0, aluno);
    }

    // Adiciona um aluno no final da lista
    public void adicionarNoFim(Aluno aluno) {
        if (aluno == null) {
            throw new IllegalArgumentException("O aluno não pode ser nulo.");
        }

        if (alunos.contains(aluno)) {
            throw new ExcecaoDeAlunoJaExistente("Aluno a ser cadastrado está utilizando o mesmo CPF, já cadastrado em nossa base.");
        }
        alunos.add(aluno);
    }

    // Ordena a lista de alunos por nome
    public void ordenarPorNome() {
        alunos.sort(Comparator.comparing(Aluno::getNome));
    }

    // Remove e retorna o último aluno da lista
    public Aluno removerDoFim() {
        if (alunos.isEmpty()) {
            return null;
        }
        return alunos.remove(alunos.size() - 1);
    }

    // Retorna o tamanho da lista
    public int tamanho() {
        return alunos.size();
    }

    // Retorna o aluno em uma posição específica
    public Aluno get(int index) {
        if (index < 0 || index >= alunos.size()) {
            throw new IndexOutOfBoundsException("Índice inválido: " + index);
        }
        return alunos.get(index);
    }

    // Busca um aluno pelo nome (ignora maiúsculas/minúsculas)
    public Optional<Aluno> buscarPorNome(String nome) {
        if (nome == null || nome.isEmpty()) {
            throw new IllegalArgumentException("O nome não pode ser vazio.");
        }
        return alunos.stream()
                .filter(a -> a.getNome().equalsIgnoreCase(nome))
                .findFirst();
    }

    // Remove um aluno pelo CPF
    public Optional<Aluno> removerPorCpf(String cpf) {
        if (cpf == null || cpf.isEmpty()) {
            throw new IllegalArgumentException("O CPF não pode ser vazio.");
        }
        Optional<Aluno> alunoEncontrado = alunos.stream()
                .filter(a -> a.getCpf().equals(cpf))
                .findFirst();
        alunoEncontrado.ifPresent(alunos::remove);
        return alunoEncontrado;
    }

    // Lista todos os alunos ordenados por nome
    public void listarAlunos() {
        ordenarPorNome();
        alunos.forEach(System.out::println);
    }
}
