package empresa;

import java.sql.Connection;
import java.util.Scanner;

import util.Conexao;

public class Principal {
    private static Scanner scanner = new Scanner(System.in);
    private static PessoaDAO pessoaDAO = new PessoaDAO();
    private static FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
    private static ProjetoDAO projetoDAO = new ProjetoDAO();

    public static void main(String[] args) {
        Connection con = Conexao.conectar();
        if (con == null) {
            System.err.println("Não foi possível conectar ao banco de dados.");
            return;
        }

        System.out.println("Sistema de Gestão Empresarial");
        System.out.println("----------------------------");

        int opcao;
        do {
            System.out.println("\nMenu Principal:");
            System.out.println("1. Gerenciar Pessoas");
            System.out.println("2. Gerenciar Funcionários");
            System.out.println("3. Gerenciar Projetos");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            
            opcao = scanner.nextInt();
            scanner.nextLine(); // Limpar buffer
            
            switch (opcao) {
                case 1:
                    gerenciarPessoas();
                    break;
                case 2:
                    gerenciarFuncionarios();
                    break;
                case 3:
                    gerenciarProjetos();
                    break;
                case 0:
                    System.out.println("Saindo do sistema...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 0);

        Conexao.fechar(con);
    }

    private static void gerenciarPessoas() {
        int opcao;
        do {
            System.out.println("\nMenu Pessoas:");
            System.out.println("1. Cadastrar Pessoa");
            System.out.println("2. Listar Pessoas");
            System.out.println("3. Buscar Pessoa por ID");
            System.out.println("4. Atualizar Pessoa");
            System.out.println("5. Deletar Pessoa");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");
            
            opcao = scanner.nextInt();
            scanner.nextLine(); // Limpar buffer
            
            switch (opcao) {
                case 1:
                    cadastrarPessoa();
                    break;
                case 2:
                    listarPessoas();
                    break;
                case 3:
                    buscarPessoaPorId();
                    break;
                case 4:
                    atualizarPessoa();
                    break;
                case 5:
                    deletarPessoa();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    private static void gerenciarFuncionarios() {
        int opcao;
        do {
            System.out.println("\nMenu Funcionários:");
            System.out.println("1. Cadastrar Funcionário");
            System.out.println("2. Listar Funcionários");
            System.out.println("3. Buscar Funcionário por ID");
            System.out.println("4. Atualizar Funcionário");
            System.out.println("5. Deletar Funcionário");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");
            
            opcao = scanner.nextInt();
            scanner.nextLine(); // Limpar buffer
            
            switch (opcao) {
                case 1:
                    cadastrarFuncionario();
                    break;
                case 2:
                    listarFuncionarios();
                    break;
                case 3:
                    buscarFuncionarioPorId();
                    break;
                case 4:
                    atualizarFuncionario();
                    break;
                case 5:
                    deletarFuncionario();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    private static void gerenciarProjetos() {
        int opcao;
        do {
            System.out.println("\nMenu Projetos:");
            System.out.println("1. Cadastrar Projeto");
            System.out.println("2. Listar Projetos");
            System.out.println("3. Buscar Projeto por ID");
            System.out.println("4. Atualizar Projeto");
            System.out.println("5. Deletar Projeto");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");
            
            opcao = scanner.nextInt();
            scanner.nextLine(); // Limpar buffer
            
            switch (opcao) {
                case 1:
                    cadastrarProjeto();
                    break;
                case 2:
                    listarProjetos();
                    break;
                case 3:
                    buscarProjetoPorId();
                    break;
                case 4:
                    atualizarProjeto();
                    break;
                case 5:
                    deletarProjeto();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    // Métodos auxiliares para cada operação CRUD
    private static void cadastrarPessoa() {
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        
        // Alterado: Utiliza Funcionario como implementação concreta de Pessoa
        Pessoa pessoa = new Funcionario(0, nome, email, null, null);
        pessoaDAO.inserir(pessoa);
    }

    private static void listarPessoas() {
        System.out.println("\nLista de Pessoas:");
        for (Pessoa p : pessoaDAO.listarTodos()) {
            System.out.println("ID: " + p.getId() + ", Nome: " + p.getNome() + ", Email: " + p.getEmail());
        }
    }

    private static void buscarPessoaPorId() {
        System.out.print("ID da Pessoa: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        
        Pessoa p = pessoaDAO.buscarPorId(id);
        if (p != null) {
            System.out.println("ID: " + p.getId() + ", Nome: " + p.getNome() + ", Email: " + p.getEmail());
        } else {
            System.out.println("Pessoa não encontrada.");
        }
    }

    private static void atualizarPessoa() {
        System.out.print("ID da Pessoa a atualizar: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        
        System.out.print("Novo Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Novo Email: ");
        String email = scanner.nextLine();
        
        // Alterado: Usa Funcionario como instância
        Pessoa pessoa = new Funcionario(id, nome, email, null, null);
        pessoaDAO.atualizar(pessoa);
    }

    private static void deletarPessoa() {
        System.out.print("ID da Pessoa a deletar: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        
        pessoaDAO.deletar(id);
    }

    private static void cadastrarFuncionario() {
        System.out.print("ID da Pessoa (já cadastrada): ");
        int idPessoa = scanner.nextInt();
        scanner.nextLine();
        
        System.out.print("Matrícula (formato FXXX): ");
        String matricula = scanner.nextLine();
        System.out.print("Departamento: ");
        String departamento = scanner.nextLine();
        
        Funcionario funcionario = new Funcionario(idPessoa, "", "", matricula, departamento);
        funcionarioDAO.inserir(funcionario);
    }

    private static void listarFuncionarios() {
        System.out.println("\nLista de Funcionários:");
        for (Funcionario f : funcionarioDAO.listarTodos()) {
            System.out.println("ID: " + f.getId() + ", Nome: " + f.getNome() + 
                             ", Email: " + f.getEmail() + ", Matrícula: " + f.getMatricula() + 
                             ", Departamento: " + f.getDepartamento());
        }
    }

    private static void buscarFuncionarioPorId() {
        System.out.print("ID do Funcionário: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        
        Funcionario f = funcionarioDAO.buscarPorId(id);
        if (f != null) {
            System.out.println("ID: " + f.getId() + ", Nome: " + f.getNome() + 
                             ", Email: " + f.getEmail() + ", Matrícula: " + f.getMatricula() + 
                             ", Departamento: " + f.getDepartamento());
        } else {
            System.out.println("Funcionário não encontrado.");
        }
    }

    private static void atualizarFuncionario() {
        System.out.print("ID do Funcionário a atualizar: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        
        System.out.print("Nova Matrícula (formato FXXX): ");
        String matricula = scanner.nextLine();
        System.out.print("Novo Departamento: ");
        String departamento = scanner.nextLine();
        
        Funcionario funcionario = new Funcionario(id, "", "", matricula, departamento);
        funcionarioDAO.atualizar(funcionario);
    }

    private static void deletarFuncionario() {
        System.out.print("ID do Funcionário a deletar: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        
        funcionarioDAO.deletar(id);
    }

    private static void cadastrarProjeto() {
        System.out.print("Nome do Projeto: ");
        String nome = scanner.nextLine();
        System.out.print("Descrição: ");
        String descricao = scanner.nextLine();
        System.out.print("ID do Funcionário Responsável: ");
        int idFuncionario = scanner.nextInt();
        scanner.nextLine();
        
        Projeto projeto = new Projeto(0, nome, descricao, idFuncionario);
        projetoDAO.inserir(projeto);
    }

    private static void listarProjetos() {
        System.out.println("\nLista de Projetos:");
        for (Projeto p : projetoDAO.listarTodos()) {
            System.out.println("ID: " + p.getId() + ", Nome: " + p.getNome() + 
                             ", Descrição: " + p.getDescricao() + 
                             ", ID Funcionário Responsável: " + p.getIdFuncionario());
        }
    }

    private static void buscarProjetoPorId() {
        System.out.print("ID do Projeto: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        
        Projeto p = projetoDAO.buscarPorId(id);
        if (p != null) {
            System.out.println("ID: " + p.getId() + ", Nome: " + p.getNome() + 
                             ", Descrição: " + p.getDescricao() + 
                             ", ID Funcionário Responsável: " + p.getIdFuncionario());
        } else {
            System.out.println("Projeto não encontrado.");
        }
    }

    private static void atualizarProjeto() {
        System.out.print("ID do Projeto a atualizar: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        
        System.out.print("Novo Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Nova Descrição: ");
        String descricao = scanner.nextLine();
        System.out.print("Novo ID do Funcionário Responsável: ");
        int idFuncionario = scanner.nextInt();
        scanner.nextLine();
        
        Projeto projeto = new Projeto(id, nome, descricao, idFuncionario);
        projetoDAO.atualizar(projeto);
    }

    private static void deletarProjeto() {
        System.out.print("ID do Projeto a deletar: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        
        projetoDAO.deletar(id);
    }
}
