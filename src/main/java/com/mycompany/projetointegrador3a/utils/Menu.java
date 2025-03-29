package com.mycompany.projetointegrador3a.utils;

import com.mycompany.projetointegrador3a.controllers.AlunoService;
import com.mycompany.projetointegrador3a.controllers.TurmaService;
import com.mycompany.projetointegrador3a.models.Aluno;

import java.util.List;
import java.util.Scanner;

public class Menu {
    private final AlunoService alunoService = new AlunoService();
    private final TurmaService turmaService = new TurmaService();
    private final Scanner scanner = new Scanner(System.in);

    public void exibirMenu() {
        int opcao;
        do {
            System.out.println("""
                    1. Cadastrar Aluno
                    2. Cadastrar Turma
                    3. Listar Alunos
                    4. Listar Turmas
                    5. Listar Alunos Matriculados em uma Turma
                    6. Matricular Aluno em Turma
                    7. Exibir Alunos Fora da Faixa Etária Prevista
                    0. Sair
                    """);
            opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir quebra de linha
            processarOpcao(opcao);
        } while (opcao != 0);
    }

    private void processarOpcao(int opcao) {
        switch (opcao) {
            case 1 -> cadastrarAluno();
            case 2 -> cadastrarTurma();
            case 3 -> alunoService.listarAlunos();
            case 4 -> turmaService.listarTurmas();
            case 5 -> listarAlunosMatriculadosTurma();
            case 6 -> matricularAlunoEmTurma();
            case 7 -> exibirAlunosForaDaFaixaEtaria();
            case 0 -> System.out.println("Saindo...");
            default -> System.out.println("Opção inválida.");
        }
    }

    private void cadastrarAluno() {
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("CPF: ");
        String cpf = scanner.nextLine();
        System.out.print("Endereço: ");
        String endereco = scanner.nextLine();

        String dataNascimento;
        while (true) {
            System.out.print("Data de Nascimento (dd/MM/yyyy): ");
            dataNascimento = scanner.nextLine();
            if (dataNascimento.matches("\\d{2}/\\d{2}/\\d{4}")) {
                break; // Formato válido
            } else {
                System.out.println("Formato inválido. Use o formato dd/MM/yyyy.");
            }
        }
        alunoService.cadastrarAluno(nome, cpf, endereco, dataNascimento);
    }

    private void cadastrarTurma() {
        System.out.print("Código da Turma: ");
        String codigoTurma = scanner.nextLine();

        // Lista de etapas permitidas
        List<String> etapasPermitidas = List.of("infantil", "fundamental anos iniciais", "fundamental anos finais", "medio");

        String etapaEnsino;
        while (true) {
            System.out.println("Etapas disponíveis: (infantil, fundamental anos iniciais, fundamental anos finais, medio)");
            System.out.print("Digite a etapa de ensino: ");
            etapaEnsino = scanner.nextLine().toLowerCase();

            if (etapasPermitidas.contains(etapaEnsino)) {
                break; // Etapa válida, sai do loop
            } else {
                System.out.println("Etapa de ensino inválida. Tente novamente.");
            }
        }

        System.out.print("Ano: ");
        int ano = scanner.nextInt();
        System.out.print("Limite de Vagas: ");
        int limiteVagas = scanner.nextInt();
        scanner.nextLine(); // Consumir quebra de linha
        turmaService.cadastrarTurma(codigoTurma, etapaEnsino, ano, limiteVagas);
    }

    private void matricularAlunoEmTurma() {
        System.out.print("Nome do Aluno: ");
        String nomeAluno = scanner.nextLine();
        System.out.print("Código da Turma: ");
        String codigoTurma = scanner.nextLine();

        alunoService.buscarAlunoPorNome(nomeAluno).ifPresentOrElse(
                aluno -> {
                    if (turmaService.matricularAlunoEmTurma(aluno, codigoTurma)) {
                        System.out.println("Aluno matriculado com sucesso!");
                    } else {
                        System.out.println("Turma lotada ou não encontrada.");
                    }
                },
                () -> System.out.println("Aluno não encontrado.")
        );
    }

    private void listarAlunosMatriculadosTurma() {
        System.out.print("Código da Turma: ");
        String codigoTurma = scanner.nextLine();
    
        turmaService.buscarTurmaPorCodigo(codigoTurma).ifPresentOrElse(
                turma -> {
                    System.out.println("Alunos Matriculados na Turma " + codigoTurma + ":");
                    turma.listarAlunosMatriculados();
                },
                () -> System.out.println("Turma não encontrada.")
        );
    }

    public void exibirAlunosForaDaFaixaEtaria() {
        System.out.print("Código da Turma: ");
        String codigoTurma = scanner.nextLine();
        
        turmaService.buscarTurmaPorCodigo(codigoTurma).ifPresentOrElse(
                turma -> {
                    List<Aluno> alunosForaDaIdade = turma.listarAlunosForaIdadePrevista();
                    int qtdForaDaIdade = alunosForaDaIdade.size();

                    if (qtdForaDaIdade == 0) {
                        System.out.println("Nenhum aluno está fora da faixa etária prevista.");
                    } else {
                        System.out.println("Quantidade de alunos fora da faixa etária: " + qtdForaDaIdade);
                        System.out.println("Lista de alunos fora da faixa etária:");
                        alunosForaDaIdade.forEach(System.out::println);
                    }
                },
                () -> System.out.println("Turma não encontrada.")
        );
    }
}
