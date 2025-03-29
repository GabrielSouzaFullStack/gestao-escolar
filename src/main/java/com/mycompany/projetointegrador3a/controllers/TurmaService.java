package com.mycompany.projetointegrador3a.controllers;

import com.mycompany.projetointegrador3a.models.Aluno;
import com.mycompany.projetointegrador3a.models.Turma;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TurmaService {
    private final List<Turma> turmas = new ArrayList<>();

    public void cadastrarTurma(String codigoTurma, String etapaEnsino, int ano, int limiteVagas) {
        // Lista de etapas de ensino válidas
        List<String> etapasPermitidas = List.of("infantil", "fundamental anos iniciais", "fundamental anos finais", "medio");

        // Verifica se a etpa de ensino é válida
        if (!etapasPermitidas.contains(etapaEnsino.toLowerCase())) {
            throw new IllegalArgumentException("Etapa de ensino inválida: " + etapaEnsino);
        }

        // Cadastra a turma se a etapa de ensino for válida
        turmas.add(new Turma(codigoTurma, etapaEnsino, ano, limiteVagas));
        System.out.println("Turma cadastrada com sucesso!");
    }

    public void listarTurmas() {
        if (turmas.isEmpty()) {
            System.out.println("Nenhuma turma cadastrada.");
        } else {
            turmas.forEach(System.out::println);
        }
    }

    public Optional<Turma> buscarTurmaPorCodigo(String codigoTurma) {
        return turmas.stream()
                .filter(t -> t.getCodigoTurma().equalsIgnoreCase(codigoTurma))
                .findFirst();
    }

    public boolean matricularAlunoEmTurma(Aluno aluno, String codigoTurma) {
        Optional<Turma> turma = buscarTurmaPorCodigo(codigoTurma);
        if (turma.isPresent()) {
            return turma.get().matricularAluno(aluno);
        }
        return false;
    }

    public boolean removerTurma(String codigoTurma) {
        Optional<Turma> turma = buscarTurmaPorCodigo(codigoTurma);
        if (turma.isPresent()) {
            turmas.remove(turma.get());
            return true;
        }
        return false;
    }
}
