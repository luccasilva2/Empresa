package empresa;

public class Funcionario extends Pessoa {
    private String matricula;
    private String departamento;

    public Funcionario() {}

    public Funcionario(int id, String nome, String email, String matricula, String departamento) {
        super(id, nome, email);
        this.matricula = matricula;
        this.departamento = departamento;
    }

    // Getters e Setters
}
