package empresa;

public abstract class Pessoa {
    private int    id;
    private String nome;
    private String email;

    public Pessoa() {}

    public Pessoa(int id, String nome, String email) {
        this.id = id;
        this.nome = nome;
        this.email = email;
    }

    // Getters e Setters
}
