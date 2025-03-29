package com.mycompany.projetointegrador3a.controllers;

import com.mycompany.projetointegrador3a.models.Aluno;
import com.mycompany.projetointegrador3a.models.ListaDeAlunos;

import java.util.Optional;

public class AlunoService {
    private final ListaDeAlunos alunos = new ListaDeAlunos();

    public void cadastrarAluno(String nome, String cpf, String endereco, String dataNascimento) {
        alunos.adicionarNoFim(new Aluno(nome, cpf, endereco, dataNascimento));
        System.out.println("Aluno cadastrado com sucesso!");
    }

    public void listarAlunos() {
        alunos.listarAlunos();
    }

    public Optional<Aluno> buscarAlunoPorNome(String nome) {
        return alunos.buscarPorNome(nome);
    }

    public Optional<Aluno> removerAlunoPorCpf(String cpf) {
        return alunos.removerPorCpf(cpf);
    }
}
